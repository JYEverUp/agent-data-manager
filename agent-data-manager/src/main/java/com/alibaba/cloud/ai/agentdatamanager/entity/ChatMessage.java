
package com.alibaba.cloud.ai.agentdatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Chat Message Entity Class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

	private Long id;

	private String sessionId;

	private String role; // user, assistant, system

	private String content;

	private String messageType; // text, sql, result, error

	private String metadata; // JSON格式的元数据

	private LocalDateTime createTime;

}
