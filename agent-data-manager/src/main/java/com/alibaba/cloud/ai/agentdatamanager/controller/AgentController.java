
package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.entity.Agent;
import com.alibaba.cloud.ai.agentdatamanager.service.agent.AgentService;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiKeyResponse;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/** Agent Management Controller */
@Slf4j
@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AgentController {

	private final AgentService agentService;

	/** Get agent list */
	@GetMapping("/list")
	public List<Agent> list(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<Agent> result;
		if (StringUtils.isNotBlank(keyword)) {
			result = agentService.search(keyword);
		}
		else if (StringUtils.isNotBlank(status)) {
			result = agentService.findByStatus(status);
		}
		else {
			result = agentService.findAll();
		}
		return result;
	}

	/** Get agent details by ID */
	@GetMapping("/{id}")
	public Agent get(@PathVariable Long id) {
		return checkAgentExists(id);
	}

	/** Create agent */
	@PostMapping
	public Agent create(@RequestBody Agent agent) {
		// Set default status
		if (StringUtils.isBlank(agent.getStatus())) {
			agent.setStatus("draft");
		}
		return agentService.save(agent);
	}

	/** Update agent */
	@PutMapping("/{id}")
	public Agent update(@PathVariable Long id, @RequestBody Agent agent) {
		checkAgentExists(id);
		agent.setId(id);
		return agentService.save(agent);
	}

	/** Delete agent */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		checkAgentExists(id);
		agentService.deleteById(id);
	}

	/** Publish agent */
	@PostMapping("/{id}/publish")
	public Agent publish(@PathVariable Long id) {
		Agent agent = checkAgentExists(id);
		agent.setStatus("published");
		return agentService.save(agent);
	}

	/** Offline agent */
	@PostMapping("/{id}/offline")
	public Agent offline(@PathVariable Long id) {
		Agent agent = checkAgentExists(id);
		agent.setStatus("offline");
		return agentService.save(agent);
	}

	/** Get masked API Key status */
	@GetMapping("/{id}/api-key")
	public ApiResponse<ApiKeyResponse> getApiKey(@PathVariable Long id) {
		Agent agent = checkAgentExists(id);
		String masked = agentService.getApiKeyMasked(id);
		return buildApiKeyResponse(masked, agent.getApiKeyEnabled(), "获取 API Key 成功");
	}

	/** Generate API Key */
	@PostMapping("/{id}/api-key/generate")
	public ApiResponse<ApiKeyResponse> generateApiKey(@PathVariable Long id) {
		checkAgentExists(id);
		Agent agent = agentService.generateApiKey(id);
		return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "生成 API Key 成功");
	}

	/** Reset API Key */
	@PostMapping("/{id}/api-key/reset")
	public ApiResponse<ApiKeyResponse> resetApiKey(@PathVariable Long id) {
		checkAgentExists(id);
		Agent agent = agentService.resetApiKey(id);
		return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "重置 API Key 成功");
	}

	/** Delete API Key */
	@DeleteMapping("/{id}/api-key")
	public ApiResponse<ApiKeyResponse> deleteApiKey(@PathVariable Long id) {
		checkAgentExists(id);
		Agent agent = agentService.deleteApiKey(id);
		return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "删除 API Key 成功");
	}

	/** Toggle API Key enable flag */
	@PostMapping("/{id}/api-key/enable")
	public ApiResponse<ApiKeyResponse> toggleApiKey(@PathVariable Long id, @RequestParam("enabled") boolean enabled) {
		checkAgentExists(id);
		Agent agent = agentService.toggleApiKey(id, enabled);
		return buildApiKeyResponse(agent.getApiKey() == null ? null : "****", agent.getApiKeyEnabled(),
				"更新 API Key 状态成功");
	}

	private Agent checkAgentExists(Long id) {
		Agent agent = agentService.findById(id);
		if (agent == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "agent with id: %d not found".formatted(id));
		}
		return agent;
	}

	private ApiResponse<ApiKeyResponse> buildApiKeyResponse(String apiKey, Integer apiKeyEnabled, String message) {
		return ApiResponse.success(message, new ApiKeyResponse(apiKey, apiKeyEnabled));
	}

}
