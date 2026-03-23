package com.alibaba.cloud.ai.agentdatamanager.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDatasourceTablesDTO {

    private Integer datasourceId;

    private List<String> tables;

}
