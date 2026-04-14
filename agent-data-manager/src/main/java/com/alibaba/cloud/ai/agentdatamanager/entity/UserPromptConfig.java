
package com.alibaba.cloud.ai.agentdatamanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPromptConfig {

	/**
	 * Configuration ID
	 */
	private String id;

	/**
	 * Configuration name
	 */
	private String name;

	/**
	 * Prompt type (e.g., report-generator, planner, etc.)
	 */
	private String promptType;

	/**
	 * Associated agent ID, null means global configuration
	 */
	private Long agentId;

	/**
	 * User-defined system prompt content
	 */
	private String systemPrompt;

	/**
	 * Whether to enable this configuration
	 */
	@Builder.Default
	private Boolean enabled = true;

	/**
	 * Configuration description
	 */
	private String description;

	/**
	 * Configuration priority (higher number = higher priority)
	 */
	@Builder.Default
	private Integer priority = 0;

	/**
	 * Configuration order for display
	 */
	@Builder.Default
	private Integer displayOrder = 0;

	/**
	 * Creation time
	 */
	private LocalDateTime createTime;

	/**
	 * Update time
	 */
	private LocalDateTime updateTime;

	/**
	 * Creator
	 */
	private String creator;

	public String getOptimizationPrompt() {
		return this.systemPrompt;
	}

	public void setOptimizationPrompt(String optimizationPrompt) {
		this.systemPrompt = optimizationPrompt;
	}

}
