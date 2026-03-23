package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.dto.DatasourceTypeDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.DatasourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.DatasourceService;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/datasource")
public class DatasourceController {

    private final DatasourceService datasourceService;

    @GetMapping("/types")
    public ApiResponse<List<DatasourceTypeDTO>> getDatasourceTypes() {
        List<DatasourceTypeDTO> types = DatasourceTypeEnum.standardTypes().stream()
                .map(type -> DatasourceTypeDTO.builder()
                        .code(type.getCode())
                        .typeName(type.getTypeName())
                        .dialect(type.getDialect())
                        .protocol(type.getProtocol())
                        .displayName(type.getDisplayName())
                        .build())
                .toList();
        return ApiResponse.success("获取数据源类型成功", types);
    }

    @GetMapping
    public List<Datasource> getAllDatasource(@RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type) {
        if (StringUtils.isNotBlank(status)) {
            return datasourceService.getDatasourceByStatus(status);
        }
        if (StringUtils.isNotBlank(type)) {
            return datasourceService.getDatasourceByType(type);
        }
        return datasourceService.getAllDatasource();
    }

    @GetMapping("/{id}")
    public Datasource getDatasourceById(@PathVariable Integer id) {
        return checkDatasourceExists(id);
    }

    @GetMapping("/{id}/tables")
    public List<String> getDatasourceTables(@PathVariable Integer id) {
        checkDatasourceExists(id);
        try {
            return datasourceService.getDatasourceTables(id);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public Datasource createDatasource(@RequestBody Datasource datasource) {
        return datasourceService.createDatasource(datasource);
    }

    @PutMapping("/{id}")
    public Datasource updateDatasource(@PathVariable Integer id, @RequestBody Datasource datasource) {
        checkDatasourceExists(id);
        return datasourceService.updateDatasource(id, datasource);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDatasource(@PathVariable Integer id) {
        checkDatasourceExists(id);
        datasourceService.deleteDatasource(id);
        return ApiResponse.success("数据源删除成功");
    }

    @PostMapping("/{id}/test")
    public ApiResponse<Boolean> testConnection(@PathVariable Integer id) {
        boolean success = datasourceService.testConnection(id);
        return success ? ApiResponse.success("连接测试成功") : ApiResponse.error("连接测试失败");
    }

    private Datasource checkDatasourceExists(Integer id) {
        Datasource datasource = datasourceService.getDatasourceById(id);
        if (datasource == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Datasource: [%s] not found".formatted(id));
        }
        return datasource;
    }

}
