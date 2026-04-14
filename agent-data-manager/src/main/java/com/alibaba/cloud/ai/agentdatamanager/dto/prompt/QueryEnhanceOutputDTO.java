
package com.alibaba.cloud.ai.agentdatamanager.dto.prompt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 对应 模板query-enhancement.txt的输出
@Data
@NoArgsConstructor
public class QueryEnhanceOutputDTO {

	// 经LLM重写后的 规范化查询
	@JsonProperty("canonical_query")
	@JsonPropertyDescription("对用户最终意图的单一、清晰的重写，包含绝对时间和解析后的业务术语")
	private String canonicalQuery;

	// 基于canonicalQuery的扩展查询
	@JsonProperty("expanded_queries")
	@JsonPropertyDescription("基于完整信息的扩展问题表述")
	private List<String> expandedQueries;

}
