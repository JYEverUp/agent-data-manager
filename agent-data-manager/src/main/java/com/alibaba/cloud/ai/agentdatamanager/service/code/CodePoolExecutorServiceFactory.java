
package com.alibaba.cloud.ai.agentdatamanager.service.code;

import com.alibaba.cloud.ai.agentdatamanager.properties.CodeExecutorProperties;
import com.alibaba.cloud.ai.agentdatamanager.service.code.CodePoolExecutorService;
import com.alibaba.cloud.ai.agentdatamanager.service.code.impls.AiSimulationCodeExecutorService;
import com.alibaba.cloud.ai.agentdatamanager.service.code.impls.DockerCodePoolExecutorService;
import com.alibaba.cloud.ai.agentdatamanager.service.code.impls.LocalCodePoolExecutorService;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 运行Python任务的容器池（工厂Bean）
 *
 * @since 2025/7/28
 */
@Component
@AllArgsConstructor
public class CodePoolExecutorServiceFactory implements FactoryBean<CodePoolExecutorService> {

	private final CodeExecutorProperties properties;

	private final LlmService llmService;

	@Override
	public CodePoolExecutorService getObject() {
		return switch (properties.getCodePoolExecutor()) {
			case DOCKER -> new DockerCodePoolExecutorService(properties);
			case LOCAL -> new LocalCodePoolExecutorService(properties);
			case AI_SIMULATION -> new AiSimulationCodeExecutorService(llmService);
			default ->
				throw new IllegalStateException("This option does not have a corresponding implementation class yet.");
		};
	}

	@Override
	public Class<?> getObjectType() {
		return CodePoolExecutorService.class;
	}

}
