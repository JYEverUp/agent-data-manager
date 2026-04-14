
package com.alibaba.cloud.ai.agentdatamanager.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class AgentKnowledgeDeletionEvent extends ApplicationEvent {

	private final Integer knowledgeId;

	public AgentKnowledgeDeletionEvent(Object source, Integer knowledgeId) {
		super(source, Clock.systemDefaultZone());
		this.knowledgeId = knowledgeId;
	}

}
