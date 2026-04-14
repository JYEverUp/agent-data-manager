
package com.alibaba.cloud.ai.agentdatamanager.enums;

import lombok.Getter;

@Getter
public enum EmbeddingStatus {

	PENDING("PENDING"), PROCESSING("PROCESSING"), COMPLETED("COMPLETED"), FAILED("FAILED");

	private final String value;

	EmbeddingStatus(String value) {
		this.value = value;
	}

	public static EmbeddingStatus fromValue(String value) {
		for (EmbeddingStatus status : EmbeddingStatus.values()) {
			// 严格比对
			if (status.value.equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Unknown embedding status: " + value);
	}

}
