
package com.alibaba.cloud.ai.agentdatamanager.workflow.dispatcher;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;
import lombok.extern.slf4j.Slf4j;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.*;

/**
 */
@Slf4j
public class SemanticConsistenceDispatcher implements EdgeAction {

	@Override
	public String apply(OverAllState state) {
		Boolean validate = (Boolean) state.value(SEMANTIC_CONSISTENCY_NODE_OUTPUT).orElse(false);
		log.info("语义一致性校验结果: {}，跳转节点配置", validate);
		if (validate) {
			log.info("语义一致性校验通过，跳转到SQL运行节点。");
			return SQL_EXECUTE_NODE;
		}
		else {
			log.info("语义一致性校验未通过，跳转到SQL生成节点。");
			return SQL_GENERATE_NODE;
		}
	}

}
