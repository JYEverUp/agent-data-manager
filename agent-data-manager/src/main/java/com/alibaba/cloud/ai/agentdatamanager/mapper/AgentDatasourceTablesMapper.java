
package com.alibaba.cloud.ai.agentdatamanager.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgentDatasourceTablesMapper {

	// 选择当前智能体数据源所有表
	@Select("select table_name from agent_datasource_tables where agent_datasource_id = #{agentDatasourceId}")
	List<String> getAgentDatasourceTables(@Param("agentDatasourceId") int agentDatasourceId);

	// 删除当前列表中不存在的表
	@Delete("<script>" + "DELETE FROM agent_datasource_tables WHERE agent_datasource_id = #{agentDatasourceId}"
			+ "<if test='tables != null and tables.size() > 0'>" + " AND table_name NOT IN ("
			+ "<foreach collection='tables' item='table' separator=','>#{table}</foreach>" + ")" + "</if>"
			+ "</script>")
	int removeExpireTables(@Param("agentDatasourceId") int agentDatasourceId, @Param("tables") List<String> tables);

	// 删除当前智能体数据源所有表
	@Delete("DELETE FROM agent_datasource_tables WHERE agent_datasource_id = #{agentDatasourceId}")
	int removeAllTables(@Param("agentDatasourceId") int agentDatasourceId);

	// 插入用户选择的列表
	@Insert("<script>" + "INSERT IGNORE INTO agent_datasource_tables (agent_datasource_id, table_name) VALUES "
			+ "<if test='tables != null and tables.size() > 0'>"
			+ "<foreach collection='tables' item='table' separator=','>" + "(#{agentDatasourceId}, #{table})"
			+ "</foreach>" + "</if>" + "</script>")
	int insertNewTables(@Param("agentDatasourceId") int agentDatasourceId, @Param("tables") List<String> tables);

	// 更新用户的选择（tables不能为空）
	default int updateAgentDatasourceTables(int agentDatasourceId, List<String> tables) {
		if (tables.isEmpty()) {
			throw new IllegalArgumentException("tables cannot be empty");
		}
		int deleteCount = removeExpireTables(agentDatasourceId, tables);
		int insertCount = insertNewTables(agentDatasourceId, tables);
		return deleteCount + insertCount;
	}

}
