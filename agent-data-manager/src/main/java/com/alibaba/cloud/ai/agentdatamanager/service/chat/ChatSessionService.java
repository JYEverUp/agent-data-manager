
package com.alibaba.cloud.ai.agentdatamanager.service.chat;

import com.alibaba.cloud.ai.agentdatamanager.entity.ChatSession;

import java.util.List;

/**
 * Chat Session Service Class
 */
public interface ChatSessionService {

	/**
	 * Get session list by agent ID
	 */
	List<ChatSession> findByAgentId(Integer agentId);

	/**
	 * Create a new session
	 */
	ChatSession createSession(Integer agentId, String title, Long userId);

	/**
	 * Find session by id.
	 */
	ChatSession findBySessionId(String sessionId);

	/**
	 * Clear all sessions for an agent
	 */
	void clearSessionsByAgentId(Integer agentId);

	/**
	 * Update the last activity time of a session
	 */
	void updateSessionTime(String sessionId);

	/**
	 * 置顶/取消置顶会话
	 */
	void pinSession(String sessionId, boolean isPinned);

	/**
	 * Rename session
	 */
	void renameSession(String sessionId, String newTitle);

	/**
	 * Delete a single session
	 */
	void deleteSession(String sessionId);

}
