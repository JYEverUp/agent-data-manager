# agent-data-manager backend

这是在 `agentData/agent-data-manager` 下新建的后端项目骨架，依赖和技术栈参考了仓库里的 `data-agent-management`。

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

## 后续如果要继续“抄” data-agent-management

建议下一步按需搬这些内容：

1. `config`
2. `aop`
3. `connector`
4. `service`
5. `mapper`
6. `application.yml` 中的 AI、数据库、向量库配置
