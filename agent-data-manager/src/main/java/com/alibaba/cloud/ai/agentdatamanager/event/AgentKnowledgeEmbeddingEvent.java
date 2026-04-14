
package com.alibaba.cloud.ai.agentdatamanager.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class AgentKnowledgeEmbeddingEvent extends ApplicationEvent {

	private final Integer knowledgeId;

	private final String splitterType;

	public AgentKnowledgeEmbeddingEvent(Object source, Integer knowledgeId, String splitterType) {
		super(source, Clock.systemDefaultZone());
		this.knowledgeId = knowledgeId;
		this.splitterType = splitterType;
	}

}
