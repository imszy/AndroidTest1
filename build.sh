#!/bin/bash

# 确保脚本可以在任何目录下运行
cd "$(dirname "$0")"

# 确保Gradle wrapper有执行权限
chmod +x gradlew
chmod -R +x gradle

# 清理项目
./gradlew clean

# 构建项目并输出详细日志
./gradlew assembleDebug --stacktrace --info 