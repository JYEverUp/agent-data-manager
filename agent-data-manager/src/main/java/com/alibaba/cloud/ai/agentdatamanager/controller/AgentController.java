package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.entity.Agent;
import com.alibaba.cloud.ai.agentdatamanager.service.AgentService;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiKeyResponse;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;

    @GetMapping("/list")
    public List<Agent> list(@RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "keyword", required = false) String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            return agentService.search(keyword);
        }
        if (StringUtils.isNotBlank(status)) {
            return agentService.findByStatus(status);
        }
        return agentService.findAll();
    }

    @GetMapping("/{id}")
    public Agent get(@PathVariable Long id) {
        return requireAgent(id);
    }

    @PostMapping
    public Agent create(@RequestBody Agent agent) {
        if (StringUtils.isBlank(agent.getStatus())) {
            agent.setStatus("draft");
        }
        return agentService.save(agent);
    }

    @PutMapping("/{id}")
    public Agent update(@PathVariable Long id, @RequestBody Agent agent) {
        requireAgent(id);
        agent.setId(id);
        return agentService.save(agent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        requireAgent(id);
        agentService.deleteById(id);
    }

    @PostMapping("/{id}/publish")
    public Agent publish(@PathVariable Long id) {
        Agent agent = requireAgent(id);
        agent.setStatus("published");
        return agentService.save(agent);
    }

    @PostMapping("/{id}/offline")
    public Agent offline(@PathVariable Long id) {
        Agent agent = requireAgent(id);
        agent.setStatus("offline");
        return agentService.save(agent);
    }

    @GetMapping("/{id}/api-key")
    public ApiResponse<ApiKeyResponse> getApiKey(@PathVariable Long id) {
        Agent agent = requireAgent(id);
        String masked = agentService.getApiKeyMasked(id);
        return buildApiKeyResponse(masked, agent.getApiKeyEnabled(), "获取 API Key 成功");
    }

    @PostMapping("/{id}/api-key/generate")
    public ApiResponse<ApiKeyResponse> generateApiKey(@PathVariable Long id) {
        requireAgent(id);
        Agent agent = agentService.generateApiKey(id);
        return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "生成 API Key 成功");
    }

    @PostMapping("/{id}/api-key/reset")
    public ApiResponse<ApiKeyResponse> resetApiKey(@PathVariable Long id) {
        requireAgent(id);
        Agent agent = agentService.resetApiKey(id);
        return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "重置 API Key 成功");
    }

    @DeleteMapping("/{id}/api-key")
    public ApiResponse<ApiKeyResponse> deleteApiKey(@PathVariable Long id) {
        requireAgent(id);
        Agent agent = agentService.deleteApiKey(id);
        return buildApiKeyResponse(agent.getApiKey(), agent.getApiKeyEnabled(), "删除 API Key 成功");
    }

    @PostMapping("/{id}/api-key/enable")
    public ApiResponse<ApiKeyResponse> toggleApiKey(@PathVariable Long id, @RequestParam("enabled") boolean enabled) {
        requireAgent(id);
        Agent agent = agentService.toggleApiKey(id, enabled);
        return buildApiKeyResponse(agent.getApiKey() == null ? null : "****", agent.getApiKeyEnabled(),
                "更新 API Key 状态成功");
    }

    private Agent requireAgent(Long id) {
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
