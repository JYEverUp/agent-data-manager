
package com.alibaba.cloud.ai.agentdatamanager.service.agent;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentPresetQuestion;

import java.util.List;

public interface AgentPresetQuestionService {

	/**
	 * Get the list of preset questions by agent ID (only active ones, ordered by
	 * sort_order and id)
	 */
	List<AgentPresetQuestion> findByAgentId(Long agentId);

	/**
	 * Get all preset questions by agent ID (including inactive ones, ordered by
	 * sort_order and id)
	 */
	List<AgentPresetQuestion> findAllByAgentId(Long agentId);

	/**
	 * Create a new preset question
	 */
	AgentPresetQuestion create(AgentPresetQuestion question);

	/**
	 * Update an existing preset question
	 */
	void update(Long id, AgentPresetQuestion question);

	/**
	 * Delete a preset question by ID
	 */
	void deleteById(Long id);

	/**
	 * Delete all preset questions for a given agent
	 */
	void deleteByAgentId(Long agentId);

	/**
	 * Batch save preset questions: delete all existing ones for the agent, then insert
	 * the new list
	 */
	void batchSave(Long agentId, List<AgentPresetQuestion> questions);

}
