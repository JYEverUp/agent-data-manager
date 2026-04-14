
package com.alibaba.cloud.ai.agentdatamanager.util;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.Accessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AccessorFactory;
import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.AgentDatasourceService;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.DatasourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Utility class for processing database.
 */
@Slf4j
@Component
@AllArgsConstructor
public class DatabaseUtil {

	private final AccessorFactory accessorFactory;

	private final AgentDatasourceService agentDatasourceService;

	private final DatasourceService datasourceService;

	public DbConfigBO getAgentDbConfig(Long agentId) {
		log.info("Getting datasource config for agent: {}", agentId);

		// Get the enabled data source for the agent
		AgentDatasource activeDatasource = agentDatasourceService.getCurrentAgentDatasource(agentId);
		// Convert to DbConfig
		DbConfigBO dbConfig = datasourceService.getDbConfig(activeDatasource.getDatasource());
		log.info("Successfully created DbConfig for agent {}: url={}, schema={}, type={}", agentId, dbConfig.getUrl(),
				dbConfig.getSchema(), dbConfig.getDialectType());

		return dbConfig;
	}

	public Accessor getAgentAccessor(Long agentId) {
		DbConfigBO dbConfig = getAgentDbConfig(agentId);
		return accessorFactory.getAccessorByDbConfig(dbConfig);
	}

}
