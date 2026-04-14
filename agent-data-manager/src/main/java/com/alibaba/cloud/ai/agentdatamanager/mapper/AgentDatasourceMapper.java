
package com.alibaba.cloud.ai.agentdatamanager.mapper;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgentDatasourceMapper {

	/** Query associated data sources by agent ID (including data source details) */
	@Select("SELECT ad.*, d.name, d.type, d.host, d.port, d.database_name, "
			+ "d.connection_url, d.username, d.password, d.status, d.test_status, d.description "
			+ "FROM agent_datasource ad " + "LEFT JOIN datasource d ON ad.datasource_id = d.id "
			+ "WHERE ad.agent_id = #{agentId} " + "ORDER BY ad.create_time DESC")
	List<AgentDatasource> selectByAgentIdWithDatasource(@Param("agentId") Long agentId);

	/** Query associated data sources by agent ID */
	@Select("SELECT * FROM agent_datasource WHERE agent_id = #{agentId} ORDER BY create_time DESC")
	List<AgentDatasource> selectByAgentId(@Param("agentId") Long agentId);

	/** Query active datasource ID by agent ID */
	@Select("SELECT datasource_id FROM agent_datasource WHERE agent_id = #{agentId} AND is_active = 1")
	Integer selectActiveDatasourceIdByAgentId(@Param("agentId") Long agentId);

	/** Query association by agent ID and data source ID */
	@Select("SELECT * FROM agent_datasource WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}")
	AgentDatasource selectByAgentIdAndDatasourceId(@Param("agentId") Long agentId,
			@Param("datasourceId") Integer datasourceId);

	/** Disable all data sources for an agent */
	@Update("UPDATE agent_datasource SET is_active = 0 WHERE agent_id = #{agentId}")
	int disableAllByAgentId(@Param("agentId") Long agentId);

	/**
	 * Count the number of enabled data sources for an agent (excluding the specified data
	 * source)
	 */
	@Select("SELECT COUNT(*) FROM agent_datasource WHERE agent_id = #{agentId} AND is_active = 1 AND datasource_id != #{excludeDatasourceId}")
	int countActiveByAgentIdExcluding(@Param("agentId") Long agentId,
			@Param("excludeDatasourceId") Integer excludeDatasourceId);

	@Delete("DELETE FROM agent_datasource WHERE datasource_id = #{datasourceId}")
	int deleteAllByDatasourceId(@Param("datasourceId") Integer datasourceId);

	@Insert("INSERT INTO agent_datasource (agent_id, datasource_id, is_active) VALUES (#{agentId}, #{datasourceId}, 1)")
	int createNewRelationEnabled(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId);

	@Update("UPDATE agent_datasource SET is_active = #{isActive} WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}")
	int updateRelation(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId,
			@Param("isActive") Integer isActive);

	default int enableRelation(Long agentId, Integer datasourceId) {
		return updateRelation(agentId, datasourceId, 1);
	}

	@Delete("DELETE FROM agent_datasource WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}")
	int removeRelation(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId);

}
