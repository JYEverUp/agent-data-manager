
package com.alibaba.cloud.ai.agentdatamanager.workflow.dispatcher;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;
import lombok.extern.slf4j.Slf4j;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.FEASIBILITY_ASSESSMENT_NODE_OUTPUT;
import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.PLANNER_NODE;
import static com.alibaba.cloud.ai.graph.StateGraph.END;

@Slf4j
public class FeasibilityAssessmentDispatcher implements EdgeAction {

	@Override
	public String apply(OverAllState state) throws Exception {
		// value的值是和 resources/feasibility-assessment.txt的输出一致，例如
		// 【需求类型】：《数据分析》
		// 【语种类型】：《中文》
		// 【需求内容】：查询所有“核心用户”的数量
		String value = state.value(FEASIBILITY_ASSESSMENT_NODE_OUTPUT, END);

		if (value != null && value.contains("【需求类型】：《数据分析》")) {
			log.info("[FeasibilityAssessmentNodeDispatcher]需求类型为数据分析，进入PlannerNode节点");
			return PLANNER_NODE;
		}
		else {
			log.info("[FeasibilityAssessmentNodeDispatcher]需求类型非数据分析，返回END节点");
			return END;
		}
	}

}
