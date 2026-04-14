
package com.alibaba.cloud.ai.agentdatamanager.service.datasource;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;

import java.util.List;

public interface AgentDatasourceService {

	/** Initialize agent's database schema using datasource */
	Boolean initializeSchemaForAgentWithDatasource(Long agentId, Integer datasourceId, List<String> tables);

	List<AgentDatasource> getAgentDatasource(Long agentId);

	default AgentDatasource getCurrentAgentDatasource(Long agentId) {
		return getAgentDatasource(agentId).stream()
			.filter(a -> a.getIsActive() != 0)
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Agent " + agentId + " has no active datasource"));
	}

	AgentDatasource addDatasourceToAgent(Long agentId, Integer datasourceId);

	void removeDatasourceFromAgent(Long agentId, Integer datasourceId);

	AgentDatasource toggleDatasourceForAgent(Long agentId, Integer datasourceId, Boolean isActive);

	void updateDatasourceTables(Long agentId, Integer datasourceId, List<String> tables);

}
