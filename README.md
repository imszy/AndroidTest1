# DemoApp

这是一个基础的 Android Demo 项目，包含多个功能演示页面，包括 UI 组件展示、列表数据、动画手势、传感器功能以及 API 数据获取演示。应用需要登录才能使用所有功能。

## 项目特性
- 最小 SDK 21，目标 SDK 34
- 使用 Java 编写
- 使用 Gradle 构建
- 支持 GitHub Actions 云端自动构建，无需本地安装 Android 环境
- 集成 Material Design 组件
- 使用 Retrofit 和 OkHttp 进行网络请求
- 使用 Gson 解析 JSON 数据
- 使用 ViewBinding 简化视图操作
- 用户身份验证系统，需要登录才能使用功能

## 技术栈

### 前端/UI
- Material Design 组件库
- ConstraintLayout 和其他高级布局
- RecyclerView 和 Adapter 模式
- SwipeRefreshLayout 下拉刷新
- CardView 卡片视图
- ViewBinding 视图绑定

### 网络和数据处理
- Retrofit 网络请求框架
- OkHttp 客户端和拦截器
- Gson JSON解析
- 异步回调处理

### 设备功能
- Android 传感器 API (加速度、光线、距离传感器)
- 系统信息和设备状态 API
- 动画和手势识别

### 开发工具和环境
- Java 编程语言
- Gradle 构建系统
- GitHub Actions CI/CD
- Android SDK (API 21-34)

## 功能模块

### 主要功能
- **UI组件展示**：展示各种 Android UI 组件的使用
- **列表与数据**：展示 RecyclerView 列表和数据处理
- **动画与手势**：展示 Android 动画和手势交互效果
- **传感器与设备功能**：展示 Android 传感器和设备功能使用
- **API数据演示**：展示如何从网络获取和展示 API 数据
- **功能测试页面**：展示各种功能测试和交互效果

### 用户身份验证
- 应用需要登录才能使用所有功能
- 提供了演示账号（用户名：demo，密码：password）
- 支持用户注册和登录功能
- 个人信息管理

### 页面详细功能

#### UI组件展示 (Page1Activity)
- **输入组件**：展示各种文本输入框、密码输入框和下拉菜单
- **选择组件**：展示复选框、单选按钮和开关控件
- **按钮组件**：展示标准按钮、轮廓按钮和其他按钮样式
- **Material Design**：展示符合 Material Design 规范的各种 UI 控件

#### 列表与数据 (Page2Activity)
- **RecyclerView 列表**：展示可滚动的数据列表
- **列表项交互**：点击列表项显示 Toast 提示
- **上下文菜单**：长按列表项显示编辑、删除和分享选项
- **下拉刷新**：使用 SwipeRefreshLayout 实现下拉刷新功能
- **动态添加**：通过浮动按钮动态添加新的列表项
- **数据管理**：展示列表数据的增删改查操作

#### 动画与手势 (Page3Activity)
- **缩放动画**：演示 View 的缩放效果
- **旋转动画**：演示 View 的旋转效果
- **平移动画**：演示 View 的位移效果
- **渐变动画**：演示 View 的透明度变化效果
- **组合动画**：演示多种动画效果的组合使用
- **属性动画**：使用 ObjectAnimator 实现流畅的属性动画效果

#### 传感器与设备功能 (Page4Activity)
- **传感器列表**：显示设备上所有可用的传感器
- **加速度传感器**：实时监测并显示设备的加速度数据
- **光线传感器**：实时监测并显示环境光线强度
- **距离传感器**：实时监测并显示物体与设备的距离
- **电池信息**：显示设备当前电池状态
- **设备信息**：显示设备型号、Android 版本等系统信息

#### API 数据演示 (ApiDemoActivity)
- **网络请求**：使用 Retrofit 从 JSONPlaceholder 公共 API 获取数据
- **数据展示**：在 RecyclerView 中展示获取的 API 数据
- **下拉刷新**：使用 SwipeRefreshLayout 实现数据刷新
- **加载状态**：显示加载中、加载成功和加载失败的不同状态
- **错误处理**：优雅处理网络请求失败的情况
- **日志记录**：使用 LogCat 记录 API 请求过程

#### 功能测试页面 (TestPage2Activity)
- **按钮测试**：测试不同按钮的点击效果和交互
- **进度条**：展示进度条的显示和隐藏效果
- **状态切换**：测试UI元素的可见性切换
- **延时操作**：演示延时任务的执行效果
- **Toast提示**：展示Toast消息提示功能

## 构建流程图

```mermaid
graph TD
    A[本地代码] -->|Push| B[GitHub 仓库]
    B -->|触发| C[GitHub Actions]
    C -->|构建| D[生成 APK]
    D -->|上传| E[Artifacts]
    E -->|下载| F[安装使用]
    G[手动触发] -->|触发| C
```

## 如何自动构建 APK

本项目已集成 GitHub Actions 自动化构建流程：
- 每次 push 到 main 分支会自动触发构建
- 也可以在 GitHub Actions 页面手动点击"Run workflow"触发构建

### 下载 APK
1. 构建完成后，进入 GitHub 仓库的 **Actions** 页面
2. 选择最新的构建记录，找到 **Artifacts** 区域
3. 下载 `app-debug-apk`，即为编译好的 APK 文件

## 本地开发（可选）
如需本地开发，建议使用 Android Studio 打开本项目。

## 快速开始

### 登录信息
- 演示账号：demo
- 密码：password

首次启动应用时，需要登录才能访问功能页面。可以使用上述演示账号，或者注册一个新账号。

---

如需自定义功能或遇到问题，欢迎提 Issue！

## 许可证

MIT License

Copyright (c) 2023

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 