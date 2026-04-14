
package com.alibaba.cloud.ai.agentdatamanager.dto.datasource;

public record SqlRetryDto(String reason, boolean semanticFail, boolean sqlExecuteFail) {

	public static SqlRetryDto semantic(String reason) {
		return new SqlRetryDto(reason, true, false);
	}

	public static SqlRetryDto sqlExecute(String reason) {
		return new SqlRetryDto(reason, false, true);
	}

	public static SqlRetryDto empty() {
		return new SqlRetryDto("", false, false);
	}

}
