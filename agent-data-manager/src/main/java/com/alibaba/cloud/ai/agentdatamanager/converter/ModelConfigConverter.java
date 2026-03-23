package com.alibaba.cloud.ai.agentdatamanager.converter;

import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.ModelConfig;

public final class ModelConfigConverter {

    private ModelConfigConverter() {
    }

    public static ModelConfigDTO toDTO(ModelConfig entity) {
        if (entity == null) {
            return null;
        }
        return ModelConfigDTO.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .apiKey(entity.getApiKey())
                .baseUrl(entity.getBaseUrl())
                .modelName(entity.getModelName())
                .modelType(entity.getModelType())
                .completionsPath(entity.getCompletionsPath())
                .embeddingsPath(entity.getEmbeddingsPath())
                .temperature(entity.getTemperature())
                .maxTokens(entity.getMaxTokens())
                .isActive(entity.getIsActive())
                .proxyEnabled(entity.getProxyEnabled())
                .proxyHost(entity.getProxyHost())
                .proxyPort(entity.getProxyPort())
                .proxyUsername(entity.getProxyUsername())
                .proxyPassword(entity.getProxyPassword())
                .build();
    }

    public static ModelConfig toEntity(ModelConfigDTO dto) {
        ModelConfig entity = new ModelConfig();
        entity.setId(dto.getId());
        entity.setProvider(dto.getProvider());
        entity.setBaseUrl(dto.getBaseUrl());
        entity.setApiKey(dto.getApiKey());
        entity.setModelName(dto.getModelName());
        entity.setTemperature(dto.getTemperature());
        entity.setIsActive(dto.getIsActive());
        entity.setMaxTokens(dto.getMaxTokens());
        entity.setModelType(dto.getModelType());
        entity.setCompletionsPath(dto.getCompletionsPath());
        entity.setEmbeddingsPath(dto.getEmbeddingsPath());
        entity.setProxyEnabled(dto.getProxyEnabled());
        entity.setProxyHost(dto.getProxyHost());
        entity.setProxyPort(dto.getProxyPort());
        entity.setProxyUsername(dto.getProxyUsername());
        entity.setProxyPassword(dto.getProxyPassword());
        return entity;
    }

}
