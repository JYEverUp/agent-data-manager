
package com.alibaba.cloud.ai.agentdatamanager.workflow.dispatcher;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;

import static com.alibaba.cloud.ai.graph.StateGraph.END;

/**
 * Dispatcher for human feedback node routing.
 *
 */
public class HumanFeedbackDispatcher implements EdgeAction {

	@Override
	public String apply(OverAllState state) throws Exception {
		String nextNode = (String) state.value("human_next_node", END);

		// 如果是等待反馈状态，返回END让图暂停
		if ("WAIT_FOR_FEEDBACK".equals(nextNode)) {
			return END;
		}

		return nextNode;
	}

}
