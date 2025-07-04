# 更新日志 (CHANGELOG)

所有项目的显著变更都将记录在此文件中。

格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)。

## [未发布]

## [1.1.0] - 2023-08-15

### 新增
- 添加了API数据演示页面
  - 使用Retrofit和OkHttp实现网络请求
  - 从JSONPlaceholder公共API获取数据
  - 使用RecyclerView展示数据列表
  - 实现下拉刷新功能
  - 添加加载状态和错误处理
- 在主页面添加了API演示入口卡片
- 添加了网络权限到AndroidManifest.xml

### 变更
- 更新了README文档，添加了API演示相关信息
- 添加了网络请求相关依赖库到build.gradle

## [1.0.0] - 2023-07-01

### 新增
- 创建基础Android Demo项目
- 实现主页面布局和基本功能
- 添加UI组件展示页面
- 添加列表与数据页面
- 添加动画与手势页面
- 添加传感器与设备功能页面
- 添加用户登录/注册功能
- 添加个人中心页面
- 集成GitHub Actions自动构建流程 