
package com.alibaba.cloud.ai.agentdatamanager.service.datasource;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.entity.LogicalRelation;

import java.util.List;

public interface DatasourceService {

	/**
	 * Get all data source list
	 */
	List<Datasource> getAllDatasource();

	/**
	 * Get data source list by status
	 */
	List<Datasource> getDatasourceByStatus(String status);

	/**
	 * Get data source list by type
	 */
	List<Datasource> getDatasourceByType(String type);

	/**
	 * Get data source details by ID
	 */
	Datasource getDatasourceById(Integer id);

	/**
	 * Create data source
	 */
	Datasource createDatasource(Datasource datasource);

	/**
	 * Update data source
	 */
	Datasource updateDatasource(Integer id, Datasource datasource);

	/**
	 * Delete data source
	 */
	void deleteDatasource(Integer id);

	/**
	 * Update data source test status
	 */
	void updateTestStatus(Integer id, String testStatus);

	/**
	 * Test data source connection
	 */
	boolean testConnection(Integer id);

	/**
	 * Get data source list associated with agent
	 */
	// 应该使用 AgentDatasourceService 中的方法
	@Deprecated
	List<AgentDatasource> getAgentDatasource(Long agentId);

	List<String> getDatasourceTables(Integer datasourceId) throws Exception;

	/**
	 * 获取数据源表的字段列表
	 */
	List<String> getTableColumns(Integer datasourceId, String tableName) throws Exception;

	DbConfigBO getDbConfig(Datasource datasource);

	/**
	 * 获取数据源的逻辑外键列表
	 */
	List<LogicalRelation> getLogicalRelations(Integer datasourceId);

	/**
	 * 添加逻辑外键
	 */
	LogicalRelation addLogicalRelation(Integer datasourceId, LogicalRelation logicalRelation);

	/**
	 * 更新逻辑外键
	 */
	LogicalRelation updateLogicalRelation(Integer datasourceId, Integer relationId, LogicalRelation logicalRelation);

	/**
	 * 删除逻辑外键
	 */
	void deleteLogicalRelation(Integer datasourceId, Integer logicalRelationId);

	/**
	 * 批量保存逻辑外键（替换现有的所有外键）
	 */
	List<LogicalRelation> saveLogicalRelations(Integer datasourceId, List<LogicalRelation> logicalRelations);

}
