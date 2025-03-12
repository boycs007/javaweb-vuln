import json

import pandas as pd


def parse_postman_collection(json_file, excel_file):
    # 读取Postman JSON文件
    with open(json_file, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 递归遍历item树结构
    def process_items(items, path, results):
        for item in items:
            if 'item' in item:  # 是文件夹节点
                new_path = path + [item['name']]
                process_items(item['item'], new_path, results)
            elif 'request' in item:  # 是请求节点
                parse_request(item, path + [item['name']], results)

    # 解析单个请求
    def parse_request(item, full_path, results):
        req = item['request']
        method = req.get('method', 'GET').upper()

        # 构建完整URL
        url = req.get('url')
        if isinstance(url, dict):
            raw_url = url.get('raw', '')
            # 处理变量替换
            raw_url = raw_url.replace('{{base_url}}', 'localhost:{{ .Port }}')
        else:
            raw_url = url.replace('{{base_url}}', 'localhost:{{ .Port }}')

        # 构建curl命令
        curl_cmd = [f'curl -X {method} "{raw_url}"']

        # 处理headers
        headers = req.get('header', [])
        for h in headers:
            if h.get('disabled'): continue
            curl_cmd.append(f'-H "{h["key"]}: {h["value"]}"')

        # 处理body
        body = req.get('body', {})
        if body.get('mode') == 'urlencoded':
            params = [f'{p["key"]}={p["value"]}' for p in body.get('urlencoded', [])]
            curl_cmd.append(f'-d "{",".join(params)}"')
        elif body.get('mode') == 'formdata':
            for param in body.get('formdata', []):
                if param.get('type') == 'file':
                    curl_cmd.append(f'-F "{param["key"]}=@{param["src"]}"')
                else:
                    curl_cmd.append(f'-F "{param["key"]}={param["value"]}"')
        elif body.get('mode') == 'raw':
            content_type = next((h["value"] for h in headers if h["key"].lower() == "content-type"),
                                "application/json" if 'json' in body.get('options', {}).get('raw', {}).get('language',
                                                                                                           '')
                                else "text/plain")
            curl_cmd.append(f'-H "Content-Type: {content_type}"')
            curl_cmd.append(f'-d \'{body["raw"]}\'')

        # 合并命令
        cmd = ' '.join(curl_cmd)

        # 生成用例名称
        case_name = '-'.join(full_path)

        # 添加到结果集
        results.append({
            'Name': case_name,
            'Cmd': cmd
        })

    # 执行解析
    results = []
    process_items(data['item'], [], results)

    # 保存到Excel
    df = pd.DataFrame(results)
    df.to_excel(excel_file, index=False, engine='openpyxl')

    import yaml
    yaml_data = []
    for item in results:
        ports = [8001, 8002, 8003]
        if 'jsp' in item['Cmd'] or 'webshell' in item['Cmd'].lower():
            ports = [8003]
        for port in ports:
            for typ in [1, 2]:
                yaml_data.append({
                    'Name': item['Name'] + "-" + str(port) + "-" + str(typ),
                    'Type': typ,
                    'Port': port,
                    'Cmd': item['Cmd'].replace('localhost:{{ .Port }}', f'localhost:{port}')
                })
    with open(excel_file.replace('.xlsx', '.yaml'), 'w', encoding='utf-8') as f:
        yaml.dump(yaml_data, f, allow_unicode=True, sort_keys=False, default_flow_style=False)


# 使用示例
parse_postman_collection('../RASP靶场测试.json', '测试用例集合.xlsx', )
