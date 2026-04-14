
package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Agent Entity Class
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Agent {

	private Long id;

	private String name; // Agent name

	private String description; // Agent description

	private String avatar; // Avatar URL

	// todo: 改为枚举
	private String status; // Status: draft-pending publication, published-published,
							// offline-offline

	private String apiKey; // API Key for external access, format sk-xxx

	@Builder.Default
	private Integer apiKeyEnabled = 0; // 0/1 toggle for API access

	private String prompt; // Custom Prompt configuration

	private String category; // Category

	private Long adminId; // Admin ID

	private String tags; // Tags, comma separated

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updateTime;

}
