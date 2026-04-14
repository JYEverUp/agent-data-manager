
package com.alibaba.cloud.ai.agentdatamanager.workflow.node;

import com.alibaba.cloud.ai.agentdatamanager.dto.planner.Plan;
import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SchemaDTO;
import com.alibaba.cloud.ai.agentdatamanager.enums.TextType;
import com.alibaba.cloud.ai.agentdatamanager.prompt.PromptConstant;
import com.alibaba.cloud.ai.agentdatamanager.prompt.PromptHelper;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import com.alibaba.cloud.ai.agentdatamanager.util.ChatResponseUtil;
import com.alibaba.cloud.ai.agentdatamanager.util.FluxUtil;
import com.alibaba.cloud.ai.agentdatamanager.util.StateUtil;
import com.alibaba.cloud.ai.graph.GraphResponse;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import com.alibaba.cloud.ai.graph.streaming.StreamingOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.*;

/**
 */
@Slf4j
@Component
@AllArgsConstructor
public class PlannerNode implements NodeAction {

	private final LlmService llmService;

	@Override
	public Map<String, Object> apply(OverAllState state) throws Exception {
		// 是否为NL2SQL模式
		Boolean onlyNl2sql = state.value(IS_ONLY_NL2SQL, false);

		Flux<ChatResponse> flux = onlyNl2sql ? handleNl2SqlOnly() : handlePlanGenerate(state);

		Flux<ChatResponse> chatResponseFlux = Flux.concat(
				Flux.just(ChatResponseUtil.createPureResponse(TextType.JSON.getStartSign())), flux,
				Flux.just(ChatResponseUtil.createPureResponse(TextType.JSON.getEndSign())));
		Flux<GraphResponse<StreamingOutput>> generator = FluxUtil.createStreamingGeneratorWithMessages(this.getClass(),
				state, v -> Map.of(PLANNER_NODE_OUTPUT, v.substring(TextType.JSON.getStartSign().length(),
						v.length() - TextType.JSON.getEndSign().length())),
				chatResponseFlux);

		return Map.of(PLANNER_NODE_OUTPUT, generator);
	}

	private Flux<ChatResponse> handlePlanGenerate(OverAllState state) {
		// 获取查询增强节点的输出
		String canonicalQuery = StateUtil.getCanonicalQuery(state);
		log.info("Using processed query for planning: {}", canonicalQuery);

		// 检查是否为修复模式
		String validationError = StateUtil.getStringValue(state, PLAN_VALIDATION_ERROR, null);
		if (validationError != null) {
			log.info("Regenerating plan with user feedback: {}", validationError);
		}
		else {
			log.info("Generating initial plan");
		}

		// 构建提示参数
		String semanticModel = (String) state.value(GENEGRATED_SEMANTIC_MODEL_PROMPT).orElse("");
		SchemaDTO schemaDTO = StateUtil.getObjectValue(state, TABLE_RELATION_OUTPUT, SchemaDTO.class);
		String schemaStr = PromptHelper.buildMixMacSqlDbPrompt(schemaDTO, true);

		// 构建用户提示
		String userPrompt = buildUserPrompt(canonicalQuery, validationError, state);
		String evidence = StateUtil.getStringValue(state, EVIDENCE);

		// 构建模板参数
		BeanOutputConverter<Plan> beanOutputConverter = new BeanOutputConverter<>(Plan.class);
		Map<String, Object> params = Map.of("user_question", userPrompt, "schema", schemaStr, "evidence", evidence,
				"semantic_model", semanticModel, "plan_validation_error", formatValidationError(validationError),
				"format", beanOutputConverter.getFormat());
		// 生成计划
		String plannerPrompt = PromptConstant.getPlannerPromptTemplate().render(params);
		log.debug("Planner prompt: as follows \n{}\n", plannerPrompt);

		// 调用LLM生成计划
		return llmService.callUser(plannerPrompt);
	}

	private Flux<ChatResponse> handleNl2SqlOnly() {
		return Flux.just(ChatResponseUtil.createPureResponse(Plan.nl2SqlPlan()));
	}

	private String buildUserPrompt(String input, String validationError, OverAllState state) {
		if (validationError == null) {
			return input;
		}

		String previousPlan = StateUtil.getStringValue(state, PLANNER_NODE_OUTPUT, "");
		return String.format(
				"IMPORTANT: User rejected previous plan with feedback: \"%s\"\n\n" + "Original question: %s\n\n"
						+ "Previous rejected plan:\n%s\n\n"
						+ "CRITICAL: Generate new plan incorporating user feedback (\"%s\")",
				validationError, input, previousPlan, validationError);
	}

	private String formatValidationError(String validationError) {
		return validationError != null ? String
			.format("**USER FEEDBACK (CRITICAL)**: %s\n\n**Must incorporate this feedback.**", validationError) : "";
	}

}
