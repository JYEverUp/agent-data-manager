
package com.alibaba.cloud.ai.agentdatamanager.converter;

import com.alibaba.cloud.ai.agentdatamanager.dto.knowledge.agentknowledge.CreateKnowledgeDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.AgentKnowledge;
import com.alibaba.cloud.ai.agentdatamanager.enums.EmbeddingStatus;
import com.alibaba.cloud.ai.agentdatamanager.enums.KnowledgeType;
import com.alibaba.cloud.ai.agentdatamanager.vo.AgentKnowledgeVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AgentKnowledgeConverter {

	// toVo
	public AgentKnowledgeVO toVo(AgentKnowledge po) {
		AgentKnowledgeVO vo = new AgentKnowledgeVO();
		vo.setId(po.getId());
		vo.setAgentId(po.getAgentId());
		vo.setTitle(po.getTitle());
		vo.setType(po.getType() != null ? po.getType().getCode() : null);
		vo.setQuestion(po.getQuestion());
		vo.setContent(po.getContent());
		vo.setIsRecall(po.getIsRecall() == 1);
		vo.setEmbeddingStatus(po.getEmbeddingStatus());
		vo.setSplitterType(po.getSplitterType());
		vo.setErrorMsg(po.getErrorMsg());
		vo.setCreatedTime(po.getCreatedTime());
		vo.setUpdatedTime(po.getUpdatedTime());
		return vo;
	}

	public AgentKnowledge toEntityForCreate(CreateKnowledgeDTO createKnowledgeDto, String storagePath) {
		// 创建AgentKnowledge对象
		AgentKnowledge knowledge = new AgentKnowledge();
		knowledge.setAgentId(createKnowledgeDto.getAgentId());
		knowledge.setTitle(createKnowledgeDto.getTitle());
		knowledge.setType(KnowledgeType.valueOf(createKnowledgeDto.getType()));
		knowledge.setQuestion(createKnowledgeDto.getQuestion());
		knowledge.setContent(createKnowledgeDto.getContent());
		knowledge.setIsRecall(1); // 默认为召回状态
		knowledge.setIsDeleted(0); // 默认为未删除
		knowledge.setEmbeddingStatus(EmbeddingStatus.PENDING); // 初始状态为待处理
		knowledge.setIsResourceCleaned(0); // 默认为物理资源未清理

		// 设置创建和更新时间
		LocalDateTime now = LocalDateTime.now();
		knowledge.setCreatedTime(now);
		knowledge.setUpdatedTime(now);

		// 如果是文档类型，设置文件相关信息
		if (createKnowledgeDto.getFile() != null && !createKnowledgeDto.getFile().isEmpty()) {
			knowledge.setSourceFilename(createKnowledgeDto.getFile().getOriginalFilename());
			knowledge.setFilePath(storagePath);
			knowledge.setFileSize(createKnowledgeDto.getFile().getSize());
			knowledge.setFileType(createKnowledgeDto.getFile().getContentType());
		}

		// 设置分块策略类型，默认值为token
		String splitterType = createKnowledgeDto.getSplitterType();
		if (splitterType == null || splitterType.isBlank()) {
			splitterType = "token";
		}
		knowledge.setSplitterType(splitterType);

		return knowledge;
	}

}
