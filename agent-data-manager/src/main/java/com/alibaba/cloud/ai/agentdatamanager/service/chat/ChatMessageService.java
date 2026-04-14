
package com.alibaba.cloud.ai.agentdatamanager.service.chat;

import com.alibaba.cloud.ai.agentdatamanager.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {

	/**
	 * Get message list by session ID
	 */
	List<ChatMessage> findBySessionId(String sessionId);

	/**
	 * Save message
	 */
	ChatMessage saveMessage(ChatMessage message);

}
