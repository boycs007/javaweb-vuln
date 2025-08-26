#!/bin/bash
rm -rf build

mvn clean package

mkdir -p build

cp vuln-springboot2/target/*.jar build/
cp vuln-springboot3/target/*.jar build/
cp vuln-test/target/*.war build/
cp static/testwar1.war build/
cp static/testwar2.war build/

docker buildx build --platform linux/amd64 -t brookechen/javaweb-vuln:latest -f Dockerfile .