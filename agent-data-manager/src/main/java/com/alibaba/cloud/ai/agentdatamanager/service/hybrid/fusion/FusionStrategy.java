
package com.alibaba.cloud.ai.agentdatamanager.service.hybrid.fusion;

import org.springframework.ai.document.Document;

import java.util.List;

/*融合策略接口*/
public interface FusionStrategy {

	@SuppressWarnings("unchecked")
	List<Document> fuseResults(int topK, List<Document>... resultLists);

}
