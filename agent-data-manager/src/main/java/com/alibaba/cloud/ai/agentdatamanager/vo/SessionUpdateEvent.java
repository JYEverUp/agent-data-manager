
package com.alibaba.cloud.ai.agentdatamanager.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SessionUpdateEvent {

	public static final String TYPE_TITLE_UPDATED = "title-updated";

	String type;

	String sessionId;

	String title;

	public static SessionUpdateEvent titleUpdated(String sessionId, String title) {
		return SessionUpdateEvent.builder().type(TYPE_TITLE_UPDATED).sessionId(sessionId).title(title).build();
	}

}
