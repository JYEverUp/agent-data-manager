
package com.alibaba.cloud.ai.agentdatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Chat Session Entity Class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSession {

	private String id; // UUID

	private Integer agentId;

	private String title;

	private String status; // active, archived, deleted

	@Builder.Default
	private Boolean isPinned = false; // Whether pinned

	private Long userId;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	public ChatSession(String id, Integer agentId, String title, String status, Long userId) {
		this.id = id;
		this.agentId = agentId;
		this.title = title;
		this.status = status;
		this.isPinned = false;
		this.userId = userId;
	}

}
