
package com.alibaba.cloud.ai.agentdatamanager.service.hybrid.retrieval.impl;

import com.alibaba.cloud.ai.agentdatamanager.dto.search.HybridSearchRequest;
import com.alibaba.cloud.ai.agentdatamanager.service.hybrid.fusion.FusionStrategy;
import com.alibaba.cloud.ai.agentdatamanager.service.hybrid.retrieval.AbstractHybridRetrievalStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 适合测试以及没有继承实现AbstractHybridRetrievalStrategy的向量库（如Pg,milvus）使用，无关键词搜索能力
 */
@Slf4j
public class DefaultHybridRetrievalStrategy extends AbstractHybridRetrievalStrategy {

	public DefaultHybridRetrievalStrategy(ExecutorService executorService, VectorStore vectorStore,
			FusionStrategy fusionStrategy) {
		super(executorService, vectorStore, fusionStrategy);
	}

	@Override
	public List<Document> getDocumentsByKeywords(HybridSearchRequest agentSearchRequest) {
		// keyword默认不操作
		return Collections.emptyList();
	}

}
