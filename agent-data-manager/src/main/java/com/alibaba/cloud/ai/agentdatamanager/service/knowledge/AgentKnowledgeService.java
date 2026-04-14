
package com.alibaba.cloud.ai.agentdatamanager.service.knowledge;

import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.agentknowledge.AgentKnowledgeQueryDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.agentknowledge.CreateKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.agentknowledge.UpdateKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.vo.AgentKnowledgeVO;
import com.alibaba.cloud.ai.agentdatamanager.vo.PageResult;

public interface AgentKnowledgeService {

	AgentKnowledgeVO getKnowledgeById(Integer id);

	AgentKnowledgeVO createKnowledge(CreateKnowledgeDTO createKnowledgeDto);

	AgentKnowledgeVO updateKnowledge(Integer id, UpdateKnowledgeDTO updateKnowledgeDto);

	boolean deleteKnowledge(Integer id);

	PageResult<AgentKnowledgeVO> queryByConditionsWithPage(AgentKnowledgeQueryDTO queryDTO);

	AgentKnowledgeVO updateKnowledgeRecallStatus(Integer id, Boolean recalled);

	void retryEmbedding(Integer id);

}
