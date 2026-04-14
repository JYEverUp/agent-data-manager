
package com.alibaba.cloud.ai.agentdatamanager.enums;

import lombok.Getter;

@Getter
public enum SplitterType {

	TOKEN("token"), RECURSIVE("recursive"), SENTENCE("sentence"), PARAGRAPH("paragraph"), SEMANTIC("semantic");

	private final String value;

	SplitterType(String value) {
		this.value = value;
	}

}
