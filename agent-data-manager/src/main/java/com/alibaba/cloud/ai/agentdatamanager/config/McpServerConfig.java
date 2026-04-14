
package com.alibaba.cloud.ai.agentdatamanager.config;

import com.alibaba.cloud.ai.agentdatamanager.annotation.McpServerTool;
import com.alibaba.cloud.ai.agentdatamanager.service.mcp.McpServerService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO 2025/12/08 合并包后移动到DataAgentConfiguration  中
@Configuration
public class McpServerConfig {

	// McpServerTool自定义注解 是为了解决如下场景：
	// ChatClient初始化依赖 chatModel，而如dashscopeChatModel等通过starter装配的ChatModel初始化会
	// 立马扫描tool了，但是我们的tool功能需要依赖LLM（比如NL2SQL），所以间接依赖了chatClient，循环依赖。
	@Bean
	@McpServerTool
	public ToolCallbackProvider mcpServerTools(McpServerService mcpServerService) {
		return MethodToolCallbackProvider.builder().toolObjects(mcpServerService).build();
	}

}
