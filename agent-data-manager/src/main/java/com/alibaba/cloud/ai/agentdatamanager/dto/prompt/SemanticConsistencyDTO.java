
package com.alibaba.cloud.ai.agentdatamanager.dto.prompt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SemanticConsistencyDTO {

	private String dialect;

	private String sql;

	private String executionDescription;

	private String schemaInfo;

	private String userQuery;

	private String evidence;

}
