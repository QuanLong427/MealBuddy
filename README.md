# Diet Agent 部署指南

## 项目概述

基于 Spring Boot 3.3 + MyBatis + DashScope（通义千问）的 AI 饮食推荐助手。多 Agent 协作系统，支持意图识别、槽位追问、个性化推荐。

## 环境要求

| 依赖 | 版本 |
|------|------|
| JDK | 21 |
| Maven | 3.9+ |
| MySQL | 8.x |

## 配置说明

配置文件：`src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/diet_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
    username: root
    password: "000000"        # 注意：纯数字密码必须加引号，否则 YAML 会解析为八进制
    driver-class-name: com.mysql.cj.jdbc.Driver
server:
  port: 8080
agentscope:
  dashscope:
    api-key: sk-xxx           # 替换为你的 DashScope API Key
diet:
  llm:
    main-model: qwen-max      # 推荐用重型模型
    light-model: qwen-turbo   # 意图识别/追问用轻量模型
```

**注意事项：**
- 数据库地址建议使用 `127.0.0.1` 而非 `localhost`，避免 IPv6 解析问题
- MySQL 密码如果全是数字，必须用引号包裹（如 `"000000"`），否则 YAML 1.1 会将其解析为八进制数

## 部署步骤

### 1. 创建数据库

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS diet_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

### 2. 导入表结构和初始数据

```bash
mysql -u root -p diet_db < src/main/resources/db/diet_db.sql
```

导入内容包括：
- 6 张数据表（diet_messages, diet_sessions, diet_request_trace, diet_slot_option, meal_item, recommend_feedback）
- 93 条槽位选项数据（mealTime, mood, scene, healthGoal, cuisine, taste, convenience）
- 5 条餐食示例数据

### 3. 修改配置

编辑 `src/main/resources/application.yml`：
- 设置正确的 MySQL 密码
- 填入你的 DashScope API Key

### 4. 编译打包

```bash
mvn clean package -DskipTests
```

### 5. 启动应用

**方式一：直接运行 JAR**
```bash
java -jar target/diet-agent-1.0-SNAPSHOT.jar
```

**方式二：Maven 插件启动（开发用）**
```bash
mvn spring-boot:run
```

### 6. 访问应用

- Web UI：http://localhost:8080
- API 基础路径：http://localhost:8080/api/v1/diet

## API 接口一览

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/v1/diet/sessions | 创建会话 |
| POST | /api/v1/diet/chat | 发送聊天消息 |
| GET | /api/v1/diet/slot-options | 获取槽位选项 |
| GET | /api/v1/diet/meals/public | 公共餐食列表 |
| GET | /api/v1/diet/meals/personal | 个人餐食列表 |
| POST | /api/v1/diet/meals/personal | 创建个人餐食 |
| PUT | /api/v1/diet/meals/personal/{id} | 更新个人餐食 |
| DELETE | /api/v1/diet/meals/personal/{id} | 删除个人餐食 |
| POST | /api/v1/diet/feedback | 提交反馈 |
| POST | /api/v1/diet/evaluations | 运行离线评估 |
| GET | /api/v1/diet/debug/traces/{traceId} | 查看请求链路 |
| GET | /api/v1/diet/debug/sessions/{id}/traces | 会话链路列表 |

## 前端页面

| 路由 | 说明 |
|------|------|
| #/diet | 首页 |
| #/diet/chat | AI 对话推荐 |
| #/diet/meals/personal | 个人餐食管理 |
| #/diet/meals/public | 公共餐食浏览 |
| #/admin/traces | 调试链路查看 |
| #/admin/evaluations | 评估面板 |
