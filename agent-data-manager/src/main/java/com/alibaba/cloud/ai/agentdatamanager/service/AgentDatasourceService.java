package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.exception.BusinessException;
import com.alibaba.cloud.ai.agentdatamanager.mapper.AgentDatasourceMapper;
import com.alibaba.cloud.ai.agentdatamanager.mapper.AgentDatasourceTablesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentDatasourceService {

    private final DatasourceService datasourceService;

    private final AgentDatasourceMapper agentDatasourceMapper;

    private final AgentDatasourceTablesMapper tablesMapper;

    public List<AgentDatasource> getAgentDatasource(Long agentId) {
        List<AgentDatasource> agentDatasources = agentDatasourceMapper.selectByAgentId(agentId);
        for (AgentDatasource agentDatasource : agentDatasources) {
            if (agentDatasource.getDatasourceId() != null) {
                Datasource datasource = datasourceService.getDatasourceById(agentDatasource.getDatasourceId());
                agentDatasource.setDatasource(datasource);
            }
            List<String> tables = tablesMapper.getAgentDatasourceTables(agentDatasource.getId());
            agentDatasource.setSelectTables(Optional.ofNullable(tables).orElse(List.of()));
        }
        return agentDatasources;
    }

    public AgentDatasource getCurrentAgentDatasource(Long agentId) {
        return getAgentDatasource(agentId).stream()
                .filter(item -> item.getIsActive() != null && item.getIsActive() == 1)
                .findFirst()
                .orElseThrow(() -> new BusinessException("未找到已启用的数据源"));
    }

    @Transactional
    public AgentDatasource addDatasourceToAgent(Long agentId, Integer datasourceId) {
        agentDatasourceMapper.disableAllByAgentId(agentId);
        AgentDatasource existing = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);

        AgentDatasource result;
        if (existing != null) {
            agentDatasourceMapper.updateRelation(agentId, datasourceId, 1);
            tablesMapper.removeAllTables(existing.getId());
            result = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);
        }
        else {
            agentDatasourceMapper.createNewRelationEnabled(agentId, datasourceId);
            result = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);
        }
        result.setSelectTables(List.of());
        result.setDatasource(datasourceService.getDatasourceById(datasourceId));
        return result;
    }

    public void removeDatasourceFromAgent(Long agentId, Integer datasourceId) {
        AgentDatasource relation = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);
        if (relation == null) {
            return;
        }
        tablesMapper.removeAllTables(relation.getId());
        agentDatasourceMapper.removeRelation(agentId, datasourceId);
    }

    public AgentDatasource toggleDatasourceForAgent(Long agentId, Integer datasourceId, Boolean isActive) {
        if (Boolean.TRUE.equals(isActive)) {
            int activeCount = agentDatasourceMapper.countActiveByAgentIdExcluding(agentId, datasourceId);
            if (activeCount > 0) {
                throw new BusinessException("同一智能体下只能启用一个数据源，请先禁用其他数据源后再启用此数据源");
            }
        }
        int updated = agentDatasourceMapper.updateRelation(agentId, datasourceId, Boolean.TRUE.equals(isActive) ? 1 : 0);
        if (updated == 0) {
            throw new BusinessException("未找到相关的数据源关联记录");
        }
        AgentDatasource result = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);
        result.setDatasource(datasourceService.getDatasourceById(datasourceId));
        result.setSelectTables(Optional.ofNullable(tablesMapper.getAgentDatasourceTables(result.getId())).orElse(List.of()));
        return result;
    }

    @Transactional
    public void updateDatasourceTables(Long agentId, Integer datasourceId, List<String> tables) {
        if (agentId == null || datasourceId == null || tables == null) {
            throw new BusinessException("参数不能为空");
        }
        AgentDatasource datasource = agentDatasourceMapper.selectByAgentIdAndDatasourceId(agentId, datasourceId);
        if (datasource == null) {
            throw new BusinessException("未找到对应的数据源关联记录");
        }
        if (tables.isEmpty()) {
            tablesMapper.removeAllTables(datasource.getId());
        }
        else {
            tablesMapper.updateAgentDatasourceTables(datasource.getId(), tables);
        }
    }

    public Boolean initializeSchemaForAgentWithDatasource(Long agentId, Integer datasourceId, List<String> tables) {
        if (agentId == null || datasourceId == null || tables == null || tables.isEmpty()) {
            throw new BusinessException("初始化参数不完整");
        }
        log.info("Initialize schema placeholder for agent {}, datasource {}, tables {}", agentId, datasourceId,
                tables.size());
        return Boolean.TRUE;
    }

}
