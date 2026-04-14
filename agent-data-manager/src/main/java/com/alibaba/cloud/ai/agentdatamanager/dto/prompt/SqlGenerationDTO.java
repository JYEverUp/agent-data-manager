
package com.alibaba.cloud.ai.agentdatamanager.dto.prompt;

import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SchemaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SqlGenerationDTO {

	private String evidence;

	private String query;

	private SchemaDTO schemaDTO;

	private String sql;

	private String exceptionMessage;

	private String executionDescription;

	private String dialect;

}
