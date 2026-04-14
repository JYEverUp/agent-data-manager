
package com.alibaba.cloud.ai.agentdatamanager.workflow.node;

import com.alibaba.cloud.ai.agentdatamanager.util.StateUtil;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.NodeAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.*;

/**
 * Human feedback node for plan review and modification.
 *
 */
@Slf4j
@Component
public class HumanFeedbackNode implements NodeAction {

	@Override
	public Map<String, Object> apply(OverAllState state) throws Exception {
		Map<String, Object> updated = new HashMap<>();

		// 检查最大修复次数
		int repairCount = StateUtil.getObjectValue(state, PLAN_REPAIR_COUNT, Integer.class, 0);
		if (repairCount >= 3) {
			log.warn("Max repair attempts (3) exceeded, ending process");
			updated.put("human_next_node", "END");
			return updated;
		}

		Map<String, Object> feedbackData = StateUtil.getObjectValue(state, HUMAN_FEEDBACK_DATA, Map.class, Map.of());
		if (feedbackData.isEmpty()) {
			updated.put("human_next_node", "WAIT_FOR_FEEDBACK");
			return updated;
		}

		// 处理反馈结果
		Object approvedValue = feedbackData.getOrDefault("feedback", true);
		boolean approved = approvedValue instanceof Boolean approvedBoolean ? approvedBoolean
				: Boolean.parseBoolean(approvedValue.toString());

		if (approved) {
			log.info("Plan approved → execution");
			updated.put("human_next_node", PLAN_EXECUTOR_NODE);
			updated.put(HUMAN_REVIEW_ENABLED, false);
		}
		else {
			log.info("Plan rejected → regeneration (attempt {})", repairCount + 1);
			updated.put("human_next_node", PLANNER_NODE);
			updated.put(PLAN_REPAIR_COUNT, repairCount + 1);
			updated.put(PLAN_CURRENT_STEP, 1);
			updated.put(HUMAN_REVIEW_ENABLED, true);

			// 保存用户反馈内容
			String feedbackContent = feedbackData.getOrDefault("feedback_content", "").toString();
			updated.put(PLAN_VALIDATION_ERROR,
					StringUtils.hasLength(feedbackContent) ? feedbackContent : "Plan rejected by user");
			// 这边清空旧的计划输出
			updated.put(PLANNER_NODE_OUTPUT, "");
		}

		return updated;
	}

}
