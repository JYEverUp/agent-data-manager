package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.converter.ModelConfigConverter;
import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.ModelConfig;
import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;
import com.alibaba.cloud.ai.agentdatamanager.exception.BusinessException;
import com.alibaba.cloud.ai.agentdatamanager.mapper.ModelConfigMapper;
import com.alibaba.cloud.ai.agentdatamanager.vo.ModelCheckVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ModelConfigService {

    private final ModelConfigMapper modelConfigMapper;

    public ModelConfigService(ModelConfigMapper modelConfigMapper) {
        this.modelConfigMapper = modelConfigMapper;
    }

    public List<ModelConfigDTO> list() {
        return modelConfigMapper.findAll()
                .stream()
                .sorted(Comparator.comparing(ModelConfig::getId))
                .map(ModelConfigConverter::toDTO)
                .toList();
    }

    public void add(ModelConfigDTO config) {
        validateBusinessRules(config, false);
        ModelConfig entity = ModelConfigConverter.toEntity(config);
        if (Boolean.TRUE.equals(entity.getIsActive())) {
            deactivateSameType(entity.getModelType(), -1);
        }
        modelConfigMapper.insert(entity);
    }

    public void update(ModelConfigDTO config) {
        if (config.getId() == null) {
            throw new BusinessException("id is required");
        }
        ModelConfig existing = modelConfigMapper.findById(config.getId());
        if (existing == null) {
            throw new BusinessException("config not found");
        }
        validateBusinessRules(config, true);
        ModelConfig updated = ModelConfigConverter.toEntity(config);
        if (Boolean.TRUE.equals(updated.getIsActive())) {
            deactivateSameType(updated.getModelType(), updated.getId());
        }
        modelConfigMapper.updateById(updated);
    }

    public void delete(Integer id) {
        if (id == null || modelConfigMapper.deleteById(id) == 0) {
            throw new BusinessException("config not found");
        }
    }

    public void activate(Integer id) {
        ModelConfig target = modelConfigMapper.findById(id);
        if (target == null) {
            throw new BusinessException("config not found");
        }
        deactivateSameType(target.getModelType(), id);
        target.setIsActive(Boolean.TRUE);
        modelConfigMapper.updateById(target);
    }

    public ModelCheckVo checkReady() {
        boolean chatReady = getActiveConfigByType(ModelType.CHAT) != null;
        boolean embeddingReady = getActiveConfigByType(ModelType.EMBEDDING) != null;
        return ModelCheckVo.builder()
                .chatModelReady(chatReady)
                .embeddingModelReady(embeddingReady)
                .ready(chatReady && embeddingReady)
                .build();
    }

    public ModelConfigDTO getActiveConfigByType(ModelType modelType) {
        return ModelConfigConverter.toDTO(modelConfigMapper.selectActiveByType(modelType.name()));
    }

    public ModelConfig findById(Integer id) {
        return modelConfigMapper.findById(id);
    }

    private void validateBusinessRules(ModelConfigDTO config, boolean updating) {
        validateUrl(config.getBaseUrl());
        if (Boolean.TRUE.equals(config.getProxyEnabled())) {
            if (StringUtils.isBlank(config.getProxyHost())) {
                throw new BusinessException("proxyHost is required when proxyEnabled is true");
            }
            if (config.getProxyPort() == null || config.getProxyPort() < 1 || config.getProxyPort() > 65535) {
                throw new BusinessException("proxyPort must be between 1 and 65535");
            }
        }
        if (config.getModelType() == ModelType.CHAT && StringUtils.isBlank(config.getApiKey())
                && !"custom".equalsIgnoreCase(config.getProvider())) {
            throw new BusinessException("apiKey must not be empty");
        }
        if (config.getApiKey() == null) {
            config.setApiKey("");
        }
        if (config.getModelType() == ModelType.CHAT && StringUtils.isBlank(config.getCompletionsPath())) {
            config.setCompletionsPath("/v1/chat/completions");
        }
        if (config.getModelType() == ModelType.EMBEDDING && StringUtils.isBlank(config.getEmbeddingsPath())) {
            config.setEmbeddingsPath("/v1/embeddings");
        }
        ensureUnique(config, updating);
    }

    private void ensureUnique(ModelConfigDTO config, boolean updating) {
        ModelConfig duplicate = modelConfigMapper.findDuplicate(config.getProvider(), config.getModelName(),
                config.getModelType().name());
        if (duplicate != null && (!updating || !Objects.equals(duplicate.getId(), config.getId()))) {
            throw new BusinessException("config already exists for the same provider, modelType and modelName");
        }
    }

    private void deactivateSameType(ModelType modelType, Integer excludeId) {
        modelConfigMapper.deactivateOthers(modelType.name(), excludeId);
    }

    private void validateUrl(String baseUrl) {
        try {
            URI uri = URI.create(baseUrl);
            if (StringUtils.isBlank(uri.getScheme()) || StringUtils.isBlank(uri.getHost())) {
                throw new IllegalArgumentException("baseUrl is invalid");
            }
        }
        catch (Exception ex) {
            throw new BusinessException("baseUrl is invalid");
        }
    }

    private void validatePath(String path, String fieldName) {
        if (!path.startsWith("/")) {
            throw new BusinessException(fieldName + " must start with /");
        }
    }

}
