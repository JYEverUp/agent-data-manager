package com.alibaba.cloud.ai.agentdatamanager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AgentDatasourceTablesMapper {

    @Select("select table_name from agent_datasource_tables where agent_datasource_id = #{agentDatasourceId}")
    List<String> getAgentDatasourceTables(@Param("agentDatasourceId") int agentDatasourceId);

    @Delete("<script>DELETE FROM agent_datasource_tables WHERE agent_datasource_id = #{agentDatasourceId}<if test='tables != null and tables.size() > 0'> AND table_name NOT IN (<foreach collection='tables' item='table' separator=','>#{table}</foreach>)</if></script>")
    int removeExpireTables(@Param("agentDatasourceId") int agentDatasourceId, @Param("tables") List<String> tables);

    @Delete("DELETE FROM agent_datasource_tables WHERE agent_datasource_id = #{agentDatasourceId}")
    int removeAllTables(@Param("agentDatasourceId") int agentDatasourceId);

    @Insert("<script>INSERT IGNORE INTO agent_datasource_tables (agent_datasource_id, table_name) VALUES <if test='tables != null and tables.size() > 0'><foreach collection='tables' item='table' separator=','>(#{agentDatasourceId}, #{table})</foreach></if></script>")
    int insertNewTables(@Param("agentDatasourceId") int agentDatasourceId, @Param("tables") List<String> tables);

    default int updateAgentDatasourceTables(int agentDatasourceId, List<String> tables) {
        if (tables.isEmpty()) {
            throw new IllegalArgumentException("tables cannot be empty");
        }
        int deleteCount = removeExpireTables(agentDatasourceId, tables);
        int insertCount = insertNewTables(agentDatasourceId, tables);
        return deleteCount + insertCount;
    }

}
