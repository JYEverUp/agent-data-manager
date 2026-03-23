package com.alibaba.cloud.ai.agentdatamanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasourceTypeDTO {

    private Integer code;

    private String typeName;

    private String dialect;

    private String protocol;

    private String displayName;

}
