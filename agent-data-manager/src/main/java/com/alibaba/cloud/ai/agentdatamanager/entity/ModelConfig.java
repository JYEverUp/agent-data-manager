
package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModelConfig {

	private Integer id;

	// 厂商标识 (方便前端展示回显，实际调用主要靠 baseUrl)
	private String provider;

	// 关键配置
	private String baseUrl;

	private String apiKey;

	private String modelName;

	private Double temperature;

	private Boolean isActive = false;

	private Integer maxTokens;

	// 模型类型
	// 可选值："CHAT", "EMBEDDING"
	private ModelType modelType;

	private String completionsPath;

	private String embeddingsPath;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createdTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updatedTime;

	// 0=未删除, 1=已删除
	private Integer isDeleted;

	// ai-proxy设置（默认关闭，使用直连）
	private Boolean proxyEnabled;

	private String proxyHost;

	private Integer proxyPort;

	private String proxyUsername;

	private String proxyPassword;

}
