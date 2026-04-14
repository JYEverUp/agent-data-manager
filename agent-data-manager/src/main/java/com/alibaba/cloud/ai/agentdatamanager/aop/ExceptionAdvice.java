
package com.alibaba.cloud.ai.agentdatamanager.aop;

import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleException(Exception e) {
		log.error("An error occurred: ", e);
		return ResponseEntity.internalServerError().body(ApiResponse.error("An error occurred: " + e.getMessage()));
	}

}
