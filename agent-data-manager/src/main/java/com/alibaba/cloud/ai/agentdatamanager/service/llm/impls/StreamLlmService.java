
package com.alibaba.cloud.ai.agentdatamanager.service.llm.impls;

import com.alibaba.cloud.ai.agentdatamanager.service.aimodelconfig.AiModelRegistry;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class StreamLlmService implements LlmService {

	private final AiModelRegistry registry;

	@Override
	public Flux<ChatResponse> call(String system, String user) {
		return registry.getChatClient().prompt().system(system).user(user).stream().chatResponse();
	}

	@Override
	public Flux<ChatResponse> callSystem(String system) {
		return registry.getChatClient().prompt().system(system).stream().chatResponse();
	}

	@Override
	public Flux<ChatResponse> callUser(String user) {
		return registry.getChatClient().prompt().user(user).stream().chatResponse();
	}

}
