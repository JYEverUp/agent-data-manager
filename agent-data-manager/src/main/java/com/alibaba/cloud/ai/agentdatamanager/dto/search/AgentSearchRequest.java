
package com.alibaba.cloud.ai.agentdatamanager.dto.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentSearchRequest implements java.io.Serializable {

	@java.io.Serial
	private static final long serialVersionUID = 1L;

	private String agentId;

	private String docVectorType;

	@Builder.Default
	private Double similarityThreshold = 0.2;

	private String query;

	private Integer topK;

}
