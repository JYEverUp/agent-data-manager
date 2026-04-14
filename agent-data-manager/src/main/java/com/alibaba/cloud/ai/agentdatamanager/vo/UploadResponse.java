
package com.alibaba.cloud.ai.agentdatamanager.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用上传响应实体。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {

	private boolean success;

	private String message;

	private String url;

	private String filename;

	public static UploadResponse ok(String message, String url, String filename) {
		UploadResponse r = new UploadResponse();
		r.setSuccess(true);
		r.setMessage(message);
		r.setUrl(url);
		r.setFilename(filename);
		return r;
	}

	public static UploadResponse error(String message) {
		UploadResponse r = new UploadResponse();
		r.setSuccess(false);
		r.setMessage(message);
		return r;
	}

}
