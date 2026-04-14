
package com.alibaba.cloud.ai.agentdatamanager.service.hybrid.fusion.impl;

import com.alibaba.cloud.ai.agentdatamanager.service.hybrid.fusion.FusionStrategy;
import org.springframework.ai.document.Document;

import java.util.List;

public class WeightedAverageStrategy implements FusionStrategy {

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> fuseResults(int topK, List<Document>... resultLists) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
