
package com.alibaba.cloud.ai.agentdatamanager.service.llm;

import com.alibaba.cloud.ai.agentdatamanager.util.ChatResponseUtil;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface LlmService {

	Flux<ChatResponse> call(String system, String user);

	Flux<ChatResponse> callSystem(String system);

	Flux<ChatResponse> callUser(String user);

	@Deprecated
	default String blockToString(Flux<ChatResponse> responseFlux) {
		return toStringFlux(responseFlux).collect(StringBuilder::new, StringBuilder::append)
			.map(StringBuilder::toString)
			.block();
	}

	default Flux<String> toStringFlux(Flux<ChatResponse> responseFlux) {
		return responseFlux.map(ChatResponseUtil::getText);
	}

}
