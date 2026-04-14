
package com.alibaba.cloud.ai.agentdatamanager.service.hybrid.retrieval;

import com.alibaba.cloud.ai.agentdatamanager.dto.search.HybridSearchRequest;
import org.springframework.ai.document.Document;

import java.util.List;

public interface HybridRetrievalStrategy {

	/**
	 * 查询某个agent下文档类型为vectorType的文档，通过query、关键词进行混合检索
	 * @return 混合检索后的文档
	 */
	List<Document> retrieve(HybridSearchRequest request);

}
