
package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.dto.ChatMessageDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.ChatMessage;
import com.alibaba.cloud.ai.agentdatamanager.entity.ChatSession;
import com.alibaba.cloud.ai.agentdatamanager.service.chat.ChatMessageService;
import com.alibaba.cloud.ai.agentdatamanager.service.chat.ChatSessionService;
import com.alibaba.cloud.ai.agentdatamanager.service.chat.SessionTitleService;
import com.alibaba.cloud.ai.agentdatamanager.util.ReportTemplateUtil;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Chat Controller
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

	private final ChatSessionService chatSessionService;

	private final ChatMessageService chatMessageService;

	private final SessionTitleService sessionTitleService;

	private final ReportTemplateUtil reportTemplateUtil;

	/**
	 * Get session list for an agent
	 */
	@GetMapping("/agent/{id}/sessions")
	public ResponseEntity<List<ChatSession>> getAgentSessions(@PathVariable(value = "id") Integer id) {
		List<ChatSession> sessions = chatSessionService.findByAgentId(id);
		return ResponseEntity.ok(sessions);
	}

	/**
	 * Create a new session
	 */
	@PostMapping("/agent/{id}/sessions")
	public ResponseEntity<ChatSession> createSession(@PathVariable(value = "id") Integer id,
			@RequestBody(required = false) Map<String, Object> request) {
		String title = request != null ? (String) request.get("title") : null;
		Long userId = request != null ? (Long) request.get("userId") : null;

		ChatSession session = chatSessionService.createSession(id, title, userId);
		return ResponseEntity.ok(session);
	}

	/**
	 * Clear all sessions for an agent
	 */
	@DeleteMapping("/agent/{id}/sessions")
	public ResponseEntity<ApiResponse> clearAgentSessions(@PathVariable(value = "id") Integer id) {
		chatSessionService.clearSessionsByAgentId(id);
		return ResponseEntity.ok(ApiResponse.success("会话已清空"));
	}

	/**
	 * Get message list for a session
	 */
	@GetMapping("/sessions/{sessionId}/messages")
	public ResponseEntity<List<ChatMessage>> getSessionMessages(@PathVariable(value = "sessionId") String sessionId) {
		List<ChatMessage> messages = chatMessageService.findBySessionId(sessionId);
		return ResponseEntity.ok(messages);
	}

	/**
	 * Save message to session
	 */
	@PostMapping("/sessions/{sessionId}/messages")
	public ResponseEntity<ChatMessage> saveMessage(@PathVariable(value = "sessionId") String sessionId,
			@RequestBody ChatMessageDTO request) {
		try {
			if (request == null) {
				return ResponseEntity.badRequest().build();
			}
			ChatMessage message = ChatMessage.builder()
				.sessionId(sessionId)
				.role(request.getRole())
				.content(request.getContent())
				.messageType(request.getMessageType())
				.metadata(request.getMetadata())
				.build();

			ChatMessage savedMessage = chatMessageService.saveMessage(message);

			// Update session activity time
			chatSessionService.updateSessionTime(sessionId);

			if (request.isTitleNeeded()) {
				sessionTitleService.scheduleTitleGeneration(sessionId, message.getContent());
			}

			return ResponseEntity.ok(savedMessage);
		}
		catch (Exception e) {
			log.error("Save message error for session {}: {}", sessionId, e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * 置顶/取消置顶会话
	 */
	@PutMapping("/sessions/{sessionId}/pin")
	public ResponseEntity<ApiResponse> pinSession(@PathVariable(value = "sessionId") String sessionId,
			@RequestParam(value = "isPinned") Boolean isPinned) {
		try {
			chatSessionService.pinSession(sessionId, isPinned);
			String message = isPinned ? "会话已置顶" : "会话已取消置顶";
			return ResponseEntity.ok(ApiResponse.success(message));
		}
		catch (Exception e) {
			log.error("Pin session error for session {}: {}", sessionId, e.getMessage(), e);
			return ResponseEntity.internalServerError().body(ApiResponse.error("操作失败"));
		}
	}

	/**
	 * Rename session
	 */
	@PutMapping("/sessions/{sessionId}/rename")
	public ResponseEntity<ApiResponse> renameSession(@PathVariable(value = "sessionId") String sessionId,
			@RequestParam(value = "title") String title) {
		try {
			if (!StringUtils.hasText(title)) {
				return ResponseEntity.badRequest().body(ApiResponse.error("标题不能为空"));
			}

			chatSessionService.renameSession(sessionId, title.trim());
			return ResponseEntity.ok(ApiResponse.success("会话已重命名"));
		}
		catch (Exception e) {
			log.error("Rename session error for session {}: {}", sessionId, e.getMessage(), e);
			return ResponseEntity.internalServerError().body(ApiResponse.error("重命名失败"));
		}
	}

	/**
	 * Delete a single session
	 */
	@DeleteMapping("/sessions/{sessionId}")
	public ResponseEntity<ApiResponse> deleteSession(@PathVariable(value = "sessionId") String sessionId) {
		try {
			chatSessionService.deleteSession(sessionId);
			return ResponseEntity.ok(ApiResponse.success("会话已删除"));
		}
		catch (Exception e) {
			log.error("Delete session error for session {}: {}", sessionId, e.getMessage(), e);
			return ResponseEntity.internalServerError().body(ApiResponse.error("删除失败"));
		}
	}

	/**
	 * Download HTML report
	 */
	@PostMapping("/sessions/{sessionId}/reports/html")
	public ResponseEntity<byte[]> convertAndDownloadHtml(@PathVariable(value = "sessionId") String sessionId,
			@RequestBody String content) {
		try {
			if (!StringUtils.hasText(content)) {
				return ResponseEntity.badRequest().build();
			}
			log.debug("Download HTML report for session {}", sessionId);
			StringBuilder htmlContent = new StringBuilder();
			htmlContent.append(reportTemplateUtil.getHeader());
			htmlContent.append(content);
			htmlContent.append(reportTemplateUtil.getFooter());
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			String filename = "report_" + timestamp + ".html";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("text", "html", StandardCharsets.UTF_8));
			headers.setContentDispositionFormData("attachment", filename);
			return ResponseEntity.ok().headers(headers).body(htmlContent.toString().getBytes(StandardCharsets.UTF_8));
		}
		catch (Exception e) {
			log.error("Download HTML report error for session {}: {}", sessionId, e.getMessage(), e);
			return ResponseEntity.internalServerError().build();
		}
	}

}
