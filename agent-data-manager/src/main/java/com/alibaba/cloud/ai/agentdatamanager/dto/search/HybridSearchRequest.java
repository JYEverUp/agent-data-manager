
package com.alibaba.cloud.ai.agentdatamanager.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.vectorstore.filter.Filter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HybridSearchRequest implements Serializable {

	// === 基础参数 ===
	private String query;

	private Integer topK;

	private double similarityThreshold = 0.0;

	private Filter.Expression filterExpression;

	// 向量检索权重
	@Builder.Default
	private Double vectorWeight = 0.5;

	// 关键词检索权重
	@Builder.Default
	private Double keywordWeight = 0.5;

	// 是否开启重排序模型
	@Builder.Default
	private boolean useRerank = false;

	// 扩展参数包将来某种数据库的特有参数
	@Builder.Default
	private Map<String, Object> extraParams = new HashMap<>();

	public org.springframework.ai.vectorstore.SearchRequest toVectorSearchRequest() {
		return org.springframework.ai.vectorstore.SearchRequest.builder()
			.query(this.query)
			.topK(this.topK)
			.similarityThreshold(this.similarityThreshold)
			.filterExpression(this.filterExpression)
			.build();
	}

}
