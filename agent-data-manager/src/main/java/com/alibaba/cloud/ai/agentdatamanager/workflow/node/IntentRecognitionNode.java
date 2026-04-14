
package com.alibaba.cloud.ai.agentdatamanager.workflow.node;

import com.alibaba.cloud.ai.agentdatamanager.dto.prompt.IntentRecognitionOutputDTO;
import com.alibaba.cloud.ai.agentdatamanager.enums.TextType;
import com.alibaba.cloud.ai.agentdatamanager.prompt.PromptHelper;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import com.alibaba.cloud.ai.agentdatamanager.util.ChatResponseUtil;
import com.alibaba.cloud.ai.agentdatamanager.util.FluxUtil;
import com.alibaba.cloud.ai.agentdatamanager.util.JsonParseUtil;
import com.alibaba.cloud.ai.agentdatamanager.util.StateUtil;
import com.alibaba.cloud.ai.graph.GraphResponse;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.streaming.StreamingOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.*;

/**
 * 意图识别节点，用于识别用户输入是闲聊还是数据分析请求
 */
@Slf4j
@Component
@AllArgsConstructor
public class IntentRecognitionNode implements NodeAction {

	private final LlmService llmService;

	private final JsonParseUtil jsonParseUtil;

	@Override
	public Map<String, Object> apply(OverAllState state) throws Exception {

		// 获取用户输入
		String userInput = StateUtil.getStringValue(state, INPUT_KEY);
		log.info("User input for intent recognition: {}", userInput);

		String multiTurn = StateUtil.getStringValue(state, MULTI_TURN_CONTEXT, "(无)");

		// 构建意图识别提示
		String prompt = PromptHelper.buildIntentRecognitionPrompt(multiTurn, userInput);
		log.debug("Built intent recognition prompt as follows \n {} \n", prompt);

		// 调用LLM进行意图识别
		Flux<ChatResponse> responseFlux = llmService.callUser(prompt);

		Flux<GraphResponse<StreamingOutput>> generator = FluxUtil.createStreamingGenerator(this.getClass(), state,
				responseFlux,
				Flux.just(ChatResponseUtil.createResponse("正在进行意图识别..."),
						ChatResponseUtil.createPureResponse(TextType.JSON.getStartSign())),
				Flux.just(ChatResponseUtil.createPureResponse(TextType.JSON.getEndSign()),
						ChatResponseUtil.createResponse("\n意图识别完成！")),
				result -> {
					// 使用JsonParseUtil解析JSON并转换为IntentRecognitionOutputDTO对象
					IntentRecognitionOutputDTO intentRecognitionOutput = jsonParseUtil.tryConvertToObject(result,
							IntentRecognitionOutputDTO.class);
					return Map.of(INTENT_RECOGNITION_NODE_OUTPUT, intentRecognitionOutput);
				});
		return Map.of(INTENT_RECOGNITION_NODE_OUTPUT, generator);
	}

}
