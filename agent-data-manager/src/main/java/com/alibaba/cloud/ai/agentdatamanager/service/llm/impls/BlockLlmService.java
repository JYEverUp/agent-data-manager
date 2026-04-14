
package com.alibaba.cloud.ai.agentdatamanager.service.llm.impls;

import com.alibaba.cloud.ai.agentdatamanager.service.aimodelconfig.AiModelRegistry;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BlockLlmService implements LlmService {

	private final AiModelRegistry registry;

	@Override
	public Flux<ChatResponse> call(String system, String user) {
		return Mono
			.fromCallable(() -> registry.getChatClient().prompt().system(system).user(user).call().chatResponse())
			.flux();
	}

	@Override
	public Flux<ChatResponse> callSystem(String system) {
		return Mono.fromCallable(() -> registry.getChatClient().prompt().system(system).call().chatResponse()).flux();
	}

	@Override
	public Flux<ChatResponse> callUser(String user) {
		return Mono.fromCallable(() -> registry.getChatClient().prompt().user(user).call().chatResponse()).flux();
	}

}
