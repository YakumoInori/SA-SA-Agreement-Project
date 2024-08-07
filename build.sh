#!/bin/sh

# 创建输出目录
mkdir -p out

# 编译 Java 文件
javac -d out -cp "lib/*" src/webcom/*.java

# 进入输出目录
cd out

# 创建 JAR 文件
jar cfe SAAgreementProject.jar webcom.WebServer -C . .

# 返回项目根目录
cd ..
