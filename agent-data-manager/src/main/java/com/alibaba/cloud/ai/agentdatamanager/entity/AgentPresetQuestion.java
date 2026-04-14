
package com.alibaba.cloud.ai.agentdatamanager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Agent Preset Question Entity Class
 */
@Data
@NoArgsConstructor
public class AgentPresetQuestion {

	private Long id;

	private Long agentId;

	private String question;

	private Integer sortOrder;

	private Boolean isActive;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	public AgentPresetQuestion(Long agentId, String question, Integer sortOrder) {
		this.agentId = agentId;
		this.question = question;
		this.sortOrder = sortOrder;
		this.isActive = false;
	}

}
