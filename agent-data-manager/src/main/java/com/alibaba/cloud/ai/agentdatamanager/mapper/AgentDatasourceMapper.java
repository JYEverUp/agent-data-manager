package com.alibaba.cloud.ai.agentdatamanager.mapper;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AgentDatasourceMapper {

    @Select("""
            SELECT * FROM agent_datasource WHERE agent_id = #{agentId} ORDER BY create_time DESC
            """)
    List<AgentDatasource> selectByAgentId(@Param("agentId") Long agentId);

    @Select("""
            SELECT * FROM agent_datasource WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}
            """)
    AgentDatasource selectByAgentIdAndDatasourceId(@Param("agentId") Long agentId,
            @Param("datasourceId") Integer datasourceId);

    @Update("UPDATE agent_datasource SET is_active = 0, update_time = NOW() WHERE agent_id = #{agentId}")
    int disableAllByAgentId(@Param("agentId") Long agentId);

    @Select("SELECT COUNT(*) FROM agent_datasource WHERE agent_id = #{agentId} AND is_active = 1 AND datasource_id != #{excludeDatasourceId}")
    int countActiveByAgentIdExcluding(@Param("agentId") Long agentId,
            @Param("excludeDatasourceId") Integer excludeDatasourceId);

    @Insert("INSERT INTO agent_datasource (agent_id, datasource_id, is_active, create_time, update_time) VALUES (#{agentId}, #{datasourceId}, 1, NOW(), NOW())")
    int createNewRelationEnabled(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId);

    @Update("UPDATE agent_datasource SET is_active = #{isActive}, update_time = NOW() WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}")
    int updateRelation(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId,
            @Param("isActive") Integer isActive);

    @Delete("DELETE FROM agent_datasource WHERE agent_id = #{agentId} AND datasource_id = #{datasourceId}")
    int removeRelation(@Param("agentId") Long agentId, @Param("datasourceId") Integer datasourceId);

    @Delete("DELETE FROM agent_datasource WHERE datasource_id = #{datasourceId}")
    int deleteAllByDatasourceId(@Param("datasourceId") Integer datasourceId);

}
