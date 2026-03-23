package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.dto.ToggleDatasourceDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.UpdateDatasourceTablesDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.AgentDatasource;
import com.alibaba.cloud.ai.agentdatamanager.service.AgentDatasourceService;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agent/{agentId}/datasources")
public class AgentDatasourceController {

    private final AgentDatasourceService agentDatasourceService;

    @PostMapping("/init")
    public ApiResponse<?> initSchema(@PathVariable Long agentId) {
        AgentDatasource agentDatasource = agentDatasourceService.getCurrentAgentDatasource(agentId);
        Integer datasourceId = agentDatasource.getDatasourceId();
        List<String> tables = Optional.ofNullable(agentDatasource.getSelectTables()).orElse(List.of());
        Boolean result = agentDatasourceService.initializeSchemaForAgentWithDatasource(agentId, datasourceId, tables);
        return result ? ApiResponse.success("Schema初始化成功") : ApiResponse.error("Schema初始化失败");
    }

    @GetMapping
    public ApiResponse<List<AgentDatasource>> getAgentDatasource(@PathVariable Long agentId) {
        List<AgentDatasource> datasources = agentDatasourceService.getAgentDatasource(agentId);
        return ApiResponse.success("操作成功", datasources);
    }

    @GetMapping("/active")
    public ApiResponse<AgentDatasource> getActiveAgentDatasource(@PathVariable Long agentId) {
        AgentDatasource datasource = agentDatasourceService.getCurrentAgentDatasource(agentId);
        return ApiResponse.success("操作成功", datasource);
    }

    @PostMapping("/{datasourceId}")
    public ApiResponse<AgentDatasource> addDatasourceToAgent(@PathVariable Long agentId,
            @PathVariable Integer datasourceId) {
        AgentDatasource agentDatasource = agentDatasourceService.addDatasourceToAgent(agentId, datasourceId);
        return ApiResponse.success("数据源添加成功", agentDatasource);
    }

    @PostMapping("/tables")
    public ApiResponse<?> updateDatasourceTables(@PathVariable Long agentId,
            @RequestBody UpdateDatasourceTablesDTO dto) {
        dto.setTables(Optional.ofNullable(dto.getTables()).orElse(List.of()));
        agentDatasourceService.updateDatasourceTables(agentId, dto.getDatasourceId(), dto.getTables());
        return ApiResponse.success("更新成功");
    }

    @DeleteMapping("/{datasourceId}")
    public ApiResponse<?> removeDatasourceFromAgent(@PathVariable Long agentId, @PathVariable Integer datasourceId) {
        agentDatasourceService.removeDatasourceFromAgent(agentId, datasourceId);
        return ApiResponse.success("数据源已移除");
    }

    @PutMapping("/toggle")
    public ApiResponse<AgentDatasource> toggleDatasourceForAgent(@PathVariable Long agentId,
            @RequestBody ToggleDatasourceDTO dto) {
        AgentDatasource agentDatasource = agentDatasourceService.toggleDatasourceForAgent(agentId,
                dto.getDatasourceId(), dto.getIsActive());
        return ApiResponse.success(Boolean.TRUE.equals(dto.getIsActive()) ? "数据源已启用" : "数据源已禁用", agentDatasource);
    }

}
