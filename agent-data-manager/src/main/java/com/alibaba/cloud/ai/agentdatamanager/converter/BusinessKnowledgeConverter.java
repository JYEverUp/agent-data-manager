
package com.alibaba.cloud.ai.agentdatamanager.converter;

import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.businessknowledge.CreateBusinessKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.BusinessKnowledge;
import com.alibaba.cloud.ai.agentdatamanager.enums.EmbeddingStatus;
import com.alibaba.cloud.ai.agentdatamanager.vo.BusinessKnowledgeVO;
import org.springframework.stereotype.Component;

@Component
public class BusinessKnowledgeConverter {

	public BusinessKnowledgeVO toVo(BusinessKnowledge po) {
		return BusinessKnowledgeVO.builder()
			.id(po.getId())
			.businessTerm(po.getBusinessTerm())
			.description(po.getDescription())
			.synonyms(po.getSynonyms())
			.isRecall(po.getIsRecall() == 1)
			.agentId(po.getAgentId())
			.createdTime(po.getCreatedTime())
			.updatedTime(po.getUpdatedTime())
			.embeddingStatus(po.getEmbeddingStatus() != null ? po.getEmbeddingStatus().getValue() : null)
			.errorMsg(po.getErrorMsg())
			.build();
	}

	// toEntityForCreate
	public BusinessKnowledge toEntityForCreate(CreateBusinessKnowledgeDTO dto) {
		return BusinessKnowledge.builder()
			.businessTerm(dto.getBusinessTerm())
			.description(dto.getDescription())
			.synonyms(dto.getSynonyms())
			.agentId(dto.getAgentId())
			.isRecall(dto.getIsRecall() ? 1 : 0)
			.isDeleted(0)
			.embeddingStatus(EmbeddingStatus.PROCESSING)
			.build();

	}

}
