
package com.alibaba.cloud.ai.agentdatamanager.service.business;

import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.businessknowledge.CreateBusinessKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.businessknowledge.UpdateBusinessKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.vo.BusinessKnowledgeVO;

import java.util.List;

// TODO 添加一个分页查询的方法
public interface BusinessKnowledgeService {

	List<BusinessKnowledgeVO> getKnowledge(Long agentId);

	List<BusinessKnowledgeVO> getAllKnowledge();

	List<BusinessKnowledgeVO> searchKnowledge(Long agentId, String keyword);

	BusinessKnowledgeVO getKnowledgeById(Long id);

	BusinessKnowledgeVO addKnowledge(CreateBusinessKnowledgeDTO knowledgeDTO);

	BusinessKnowledgeVO updateKnowledge(Long id, UpdateBusinessKnowledgeDTO knowledgeDTO);

	void deleteKnowledge(Long id);

	void recallKnowledge(Long id, Boolean isRecall);

	void refreshAllKnowledgeToVectorStore(String agentId) throws Exception;

	void retryEmbedding(Long id);

}
