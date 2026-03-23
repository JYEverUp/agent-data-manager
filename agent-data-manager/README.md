# agent-data-manager backend

这是在 `agentData/agent-data-manager` 下搭建的后端项目，当前已经补齐 `/model-config` 页面联调所需的接口、数据表和 MyBatis 持久层。

## 当前技术栈

- Java 17
- Spring Boot 3.4.x
- Spring WebFlux
- Spring AI MCP Server WebFlux
- MyBatis
- JDBC / Druid
- OpenAPI / Swagger
- H2 / MySQL / PostgreSQL 驱动

## 目录

```text
agent-data-manager
├─ pom.xml
└─ src
   ├─ main
   │  ├─ java/com/alibaba/cloud/ai/agentdatamanager
   │  └─ resources/application.yml
   └─ test
```

## 运行

在项目根目录执行：

```powershell
..\..\mvnw.cmd -f pom.xml spring-boot:run
```

或者：

```powershell
mvn spring-boot:run
```

默认端口是 `8081`，健康检查地址：

```text
GET /api/health
```

## 已完成

- `/api/model-config/list`
- `/api/model-config/add`
- `/api/model-config/update`
- `/api/model-config/{id}`
- `/api/model-config/activate/{id}`
- `/api/model-config/test`
- `/api/model-config/check-ready`
- `model_config` 建表脚本
- MyBatis `entity` / `mapper` / `converter`

## 数据初始化

应用启动时会自动执行：

```text
classpath:sql/schema.sql
```
