
package com.alibaba.cloud.ai.agentdatamanager.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common API Response Class
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {

	private boolean success;

	private String message;

	private T data;

	public ApiResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ApiResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message);
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message);
	}

	public static <T> ApiResponse<T> error(String message, T data) {
		return new ApiResponse<>(false, message, data);
	}

}
