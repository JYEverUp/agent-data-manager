
package com.alibaba.cloud.ai.agentdatamanager.service.chat;

import com.alibaba.cloud.ai.agentdatamanager.entity.ChatMessage;
import com.alibaba.cloud.ai.agentdatamanager.mapper.ChatMessageMapper;
import com.alibaba.cloud.ai.agentdatamanager.service.chat.ChatMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Chat Message Service Class
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

	private final ChatMessageMapper chatMessageMapper;

	@Override
	public List<ChatMessage> findBySessionId(String sessionId) {
		return chatMessageMapper.selectBySessionId(sessionId);
	}

	@Override
	public ChatMessage saveMessage(ChatMessage message) {
		chatMessageMapper.insert(message);
		log.info("Saved message: {} for session: {}", message.getId(), message.getSessionId());
		return message;
	}

}
