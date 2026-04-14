
package com.alibaba.cloud.ai.agentdatamanager.workflow.dispatcher;

import com.alibaba.cloud.ai.agentdatamanager.dto.datasource.SqlRetryDto;
import com.alibaba.cloud.ai.agentdatamanager.util.StateUtil;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.action.EdgeAction;
import lombok.extern.slf4j.Slf4j;

import static com.alibaba.cloud.ai.agentdatamanager.constant.Constant.*;

/**
 */
@Slf4j
public class SQLExecutorDispatcher implements EdgeAction {

	@Override
	public String apply(OverAllState state) {
		SqlRetryDto retryDto = StateUtil.getObjectValue(state, SQL_REGENERATE_REASON, SqlRetryDto.class);
		if (retryDto.sqlExecuteFail()) {
			log.warn("SQL运行失败，需要重新生成！");
			return SQL_GENERATE_NODE;
		}
		else {
			log.info("SQL运行成功，返回PlanExecutorNode。");
			return PLAN_EXECUTOR_NODE;
		}
	}

}
