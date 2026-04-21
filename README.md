# XinMetaPlugin

[![License](https://img.shields.io/github/license/huangdihd/XinMetaPlugin)](https://github.com/huangdihd/XinMetaPlugin/blob/main/LICENSE)
[![Java Version](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![xinbot](https://img.shields.io/badge/Plugin%20for-xinbot-blue)](https://github.com/huangdihd/xinbot)

XinMetaPlugin 是一个基于 [xinbot](https://github.com/huangdihd/xinbot) 框架开发的元插件（Meta Plugin），专为 **2b2t.xin** 服务器设计。它通过自动化排队、验证码识别及环境适配，旨在为用户提供极致的自动化进服体验。

## ✨ 特性

- **自动进服适配**:
    - `AutoLogin`: 自动处理登录流程。
    - `AutoJoin`: 自动识别并点击进服容器/菜单。
    - `AutoReconnect`: 掉线自动重连，无需人工干预。
- **智能验证系统**:
    - `Captcha Solver`: 自动处理图形验证码。
    - `Question Answer`: 自动回答服务器进服提问（支持通过 `questions.json` 自定义）。
- **实时状态感知**:
    - `Queue Overlay`: 在屏幕副标题实时显示排队位置与进度。
    - `Position Tracker`: 精确追踪排队变化。
- **消息监控**:
    - 集成公聊与私聊监听，方便进行二次开发或日志审计。
- **多语言支持**: 
    - 内置德、英、法、日、俄、中（简/繁）等多种语言文件。

## 🚀 快速开始

### 前置要求

- [xinbot](https://github.com/huangdihd/xinbot) 运行环境。
- Java 17 或更高版本。

### 安装

1. 下载或自行编译 `XinMetaPlugin.jar`。
2. 将插件放入 `xinbot` 的 `plugins` 目录下。
3. 启动 `xinbot`，插件将自动加载。

### 配置

插件会自动加载 `src/main/resources/questions.json` 中的问题库。你可以根据服务器的变动随时更新该文件：

```json
[
  {
    "question": "1 + 1 = ?",
    "answer": "2"
  }
]
```

## 🛠️ 编译

使用 Maven 进行构建：

```bash
mvn clean package
```

构建后的文件将位于 `target/xinMetaPlugin-1.0.0-SNAPSHOT.jar`。

## 📄 开源协议

本项目采用 [GPL-3.0](LICENSE) 协议开源。
