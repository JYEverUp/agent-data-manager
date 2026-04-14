
package com.alibaba.cloud.ai.agentdatamanager.dto.datasource;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDatasourceTablesDTO {

	@NotNull(message = "datasourceId cannot be null")
	private Integer datasourceId;

	private List<String> tables;

}
