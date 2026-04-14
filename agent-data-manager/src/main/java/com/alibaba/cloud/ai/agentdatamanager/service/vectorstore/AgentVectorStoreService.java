
package com.alibaba.cloud.ai.agentdatamanager.service.vectorstore;

import com.alibaba.cloud.ai.agentdatamanager.dto.search.AgentSearchRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.Filter;

import java.util.List;
import java.util.Map;

public interface AgentVectorStoreService {

	/**
	 * 查询某个Agent的文档 总入口
	 */
	List<Document> search(AgentSearchRequest searchRequest);

	Boolean deleteDocumentsByVectorType(String agentId, String vectorType) throws Exception;

	Boolean deleteDocumentsByMetedata(String agentId, Map<String, Object> metadata);

	Boolean deleteDocumentsByMetadata(Map<String, Object> metadata);

	/**
	 * Get documents for specified agent
	 */
	List<Document> getDocumentsForAgent(String agentId, String query, String vectorType);

	List<Document> getDocumentsForAgent(String agentId, String query, String vectorType, int topK, double threshold);

	// 通过元数据过滤精确查找
	List<Document> getDocumentsOnlyByFilter(Filter.Expression filterExpression, Integer topK);

	boolean hasDocuments(String agentId);

	void addDocuments(String agentId, List<Document> documents);

}
