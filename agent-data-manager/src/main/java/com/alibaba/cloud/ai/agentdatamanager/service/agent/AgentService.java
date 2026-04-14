
package com.alibaba.cloud.ai.agentdatamanager.service.agent;

import com.alibaba.cloud.ai.agentdatamanager.entity.Agent;

import java.util.List;

public interface AgentService {

	List<Agent> findAll();

	Agent findById(Long id);

	List<Agent> findByStatus(String status);

	List<Agent> search(String keyword);

	Agent save(Agent agent);

	void deleteById(Long id);

	Agent generateApiKey(Long id);

	Agent resetApiKey(Long id);

	Agent deleteApiKey(Long id);

	Agent toggleApiKey(Long id, boolean enabled);

	String getApiKeyMasked(Long id);

}
