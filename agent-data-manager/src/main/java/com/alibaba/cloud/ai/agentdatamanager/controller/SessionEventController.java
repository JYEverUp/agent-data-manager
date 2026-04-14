
package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.service.chat.SessionEventPublisher;
import com.alibaba.cloud.ai.agentdatamanager.vo.SessionUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionEventController {

	private final SessionEventPublisher sessionEventPublisher;

	@GetMapping(value = "/agent/{agentId}/sessions/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<SessionUpdateEvent>> streamSessionUpdates(@PathVariable Integer agentId,
			ServerHttpResponse response) {
		response.getHeaders().add("Cache-Control", "no-cache");
		response.getHeaders().add("Connection", "keep-alive");
		response.getHeaders().add("Access-Control-Allow-Origin", "*");

		log.debug("Client subscribed to session update stream for agent {}", agentId);
		return sessionEventPublisher.register(agentId)
			.doFinally(
					signal -> log.debug("Session update stream finished for agent {} with signal {}", agentId, signal));
	}

}
