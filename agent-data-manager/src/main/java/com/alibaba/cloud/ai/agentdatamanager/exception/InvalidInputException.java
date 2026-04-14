
package com.alibaba.cloud.ai.agentdatamanager.exception;

import lombok.Getter;

public class InvalidInputException extends RuntimeException {

	@Getter
	private Object data;

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(String message, Object data) {
		super(message);
		this.data = data;
	}

}
