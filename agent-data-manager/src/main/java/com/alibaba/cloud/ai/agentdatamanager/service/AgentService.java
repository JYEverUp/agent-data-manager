package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.entity.Agent;
import com.alibaba.cloud.ai.agentdatamanager.exception.BusinessException;
import com.alibaba.cloud.ai.agentdatamanager.mapper.AgentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentMapper agentMapper;

    public List<Agent> findAll() {
        return agentMapper.findAll();
    }

    public Agent findById(Long id) {
        return agentMapper.findById(id);
    }

    public List<Agent> findByStatus(String status) {
        return agentMapper.findByStatus(status);
    }

    public List<Agent> search(String keyword) {
        return agentMapper.searchByKeyword(keyword);
    }

    public Agent save(Agent agent) {
        LocalDateTime now = LocalDateTime.now();
        if (agent.getId() == null) {
            agent.setCreateTime(now);
            agent.setUpdateTime(now);
            if (agent.getApiKeyEnabled() == null) {
                agent.setApiKeyEnabled(0);
            }
            if (agent.getHumanReviewEnabled() == null) {
                agent.setHumanReviewEnabled(0);
            }
            agentMapper.insert(agent);
            return agent;
        }

        Agent existing = requireAgent(agent.getId());
        mergeForUpdate(existing, agent);
        existing.setUpdateTime(now);
        agentMapper.updateById(existing);
        return existing;
    }

    public void deleteById(Long id) {
        if (agentMapper.deleteById(id) == 0) {
            throw new BusinessException("Agent not found: " + id);
        }
    }

    public Agent generateApiKey(Long id) {
        Agent agent = requireAgent(id);
        String apiKey = generateKey();
        agentMapper.updateApiKey(id, apiKey, 1);
        agent.setApiKey(apiKey);
        agent.setApiKeyEnabled(1);
        return agent;
    }

    public Agent resetApiKey(Long id) {
        return generateApiKey(id);
    }

    public Agent deleteApiKey(Long id) {
        Agent agent = requireAgent(id);
        agentMapper.updateApiKey(id, null, 0);
        agent.setApiKey(null);
        agent.setApiKeyEnabled(0);
        return agent;
    }

    public Agent toggleApiKey(Long id, boolean enabled) {
        agentMapper.toggleApiKey(id, enabled ? 1 : 0);
        Agent agent = requireAgent(id);
        agent.setApiKeyEnabled(enabled ? 1 : 0);
        return agent;
    }

    public String getApiKeyMasked(Long id) {
        Agent agent = requireAgent(id);
        String apiKey = agent.getApiKey();
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }
        if (apiKey.length() <= 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }

    private Agent requireAgent(Long id) {
        Agent agent = agentMapper.findById(id);
        if (agent == null) {
            throw new BusinessException("Agent not found: " + id);
        }
        return agent;
    }

    private String generateKey() {
        return "sk-" + UUID.randomUUID().toString().replace("-", "");
    }

    private void mergeForUpdate(Agent existing, Agent incoming) {
        existing.setName(incoming.getName());
        existing.setDescription(incoming.getDescription());
        existing.setAvatar(incoming.getAvatar());
        existing.setStatus(incoming.getStatus());
        existing.setPrompt(incoming.getPrompt());
        existing.setCategory(incoming.getCategory());
        existing.setTags(incoming.getTags());
        existing.setHumanReviewEnabled(incoming.getHumanReviewEnabled() == null ? 0 : incoming.getHumanReviewEnabled());
    }

}
