FROM tencentos/tencentos4
ENV VERSION=3.0.4

# 8001： tomcat9
# 8002: springboot2
# 8003: springboot3
# 8004: tomcat10
EXPOSE 8001-8004

RUN mkdir -p /data && cd /data \
    && wget 'https://github.com/Tencent/TencentKona-8/releases/download/8.0.23-GA/TencentKona8.0.23.b1_jdk_linux-x86_64_8u462.tar.gz' \
    && wget 'https://github.com/Tencent/TencentKona-17/releases/download/TencentKona-17.0.16/TencentKona-17.0.16.b1-jdk_linux-x86_64.tar.gz' \
    && wget 'https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.108/bin/apache-tomcat-9.0.108.tar.gz' \
    && wget 'https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.44/bin/apache-tomcat-10.1.44.tar.gz'

RUN cd /data \
    && tar -xzf TencentKona8.0.23.b1_jdk_linux-x86_64_8u462.tar.gz \
    && tar -xzf TencentKona-17.0.16.b1-jdk_linux-x86_64.tar.gz \
    && tar -xzf apache-tomcat-9.0.108.tar.gz \
    && tar -xzf apache-tomcat-10.1.44.tar.gz

ENV JDK8=TencentKona-8.0.23-462
ENV JDK17=TencentKona-17.0.16.b1
ENV JAVA_HOME=/data/$JDK17
ENV TOMCAT9=apache-tomcat-9.0.108
ENV TOMCAT10=apache-tomcat-10.1.44
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY build/ /data

RUN cd /data \
    && mkdir -p /data/logs \
    && mkdir -p /data/ROOT \
    && cd /data/ROOT \
    && jar -xvf ../vuln-test.war

# Javax 需要的基座
RUN cd /data/$TOMCAT9 \
    && chmod +x bin/*.sh \
    && sed -i 's/8005/8101/g' conf/server.xml \
    && sed -i 's/8080/8001/g' conf/server.xml \
    && sed -i '1a JAVA_HOME=\"/data/$JDK8/\"' bin/catalina.sh \
    && cd webapps \
    && mv ROOT/ tomcat \
    && cp -r /data/ROOT ./ \
    && mv /data/testwar1.war ./

# Jakarta 需要的基座
RUN cd /data/$TOMCAT10 \
    && chmod +x bin/*.sh \
    && sed -i 's/8005/8102/g' conf/server.xml \
    && sed -i 's/8080/8004/g' conf/server.xml \
    && sed -i '1a JAVA_HOME=\"/data/$JDK17/\"' bin/catalina.sh \
    && cd webapps \
    && mv /data/testwar2.war ./

# 启动tomcat9 / tomcat10 / springboot2 / springboot3

RUN echo '#!/bin/sh' > /start.sh \
    && echo 'nohup /data/$JDK8/bin/java -jar /data/vuln-springboot2-$VERSION.jar >/data/logs/vuln-springboot2.log &' >> /start.sh \
    && echo 'nohup /data/$JDK17/bin/java -jar /data/vuln-springboot3-$VERSION.jar >/data/logs/vuln-springboot3.log &' >> /start.sh \
    && echo 'nohup /data/$TOMCAT9/bin/catalina.sh run >/data/logs/vuln-tomcat9.log &' >> /start.sh \
    && echo 'sh /data/$TOMCAT10/bin/catalina.sh run' >> /start.sh \
    && echo '/etc/ntp.conf:my content' >/etc/ntp.conf \
    && echo 'db97fb26-7713-44b5-9295-6038fdae38b9' >> /etc/flag.txt \
    && chmod +x /start.sh
CMD ["/start.sh"]