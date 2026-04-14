
package com.alibaba.cloud.ai.agentdatamanager.service.llm;

import com.alibaba.cloud.ai.agentdatamanager.properties.DataAgentProperties;
import com.alibaba.cloud.ai.agentdatamanager.service.aimodelconfig.AiModelRegistry;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.impls.BlockLlmService;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.impls.StreamLlmService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LlmServiceFactory implements FactoryBean<LlmService> {

	private final DataAgentProperties properties;

	private final AiModelRegistry aiModelRegistry;

	@Override
	public LlmService getObject() {
		if (LlmServiceEnum.BLOCK.equals(properties.getLlmServiceType())) {
			return new BlockLlmService(aiModelRegistry);
		}
		else {
			return new StreamLlmService(aiModelRegistry);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return LlmService.class;
	}

}
