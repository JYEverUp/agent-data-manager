package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.ModelConfig;
import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelConfigOpsService {

    private final ModelConfigService modelConfigService;

    private final DynamicModelFactory modelFactory;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(rollbackFor = Exception.class)
    public void updateAndRefresh(ModelConfigDTO dto) {
        modelConfigService.update(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void activateConfig(Integer id) {
        ModelConfig entity = modelConfigService.findById(id);
        if (entity == null) {
            throw new RuntimeException("配置不存在");
        }
        modelConfigService.activate(id);
        log.info("Config ID={} activated successfully.", id);
    }

    public void testConnection(ModelConfigDTO config) {
        try {
            if (config.getModelType() == ModelType.CHAT) {
                testChatModel(config);
            }
            else if (config.getModelType() == ModelType.EMBEDDING) {
                testEmbeddingModel(config);
            }
            else {
                throw new IllegalArgumentException("未知的模型类型: " + config.getModelType());
            }
        }
        catch (Exception e) {
            try {
                log.error("Failed to test model connection. Config: {}", objectMapper.writeValueAsString(config), e);
            }
            catch (JsonProcessingException e1) {
                log.error("Failed to convert config to JSON. Config: {}", config, e1);
            }
            throw new RuntimeException(parseErrorMessage(e));
        }
    }

    private void testChatModel(ModelConfigDTO config) {
        log.info("Testing Chat Model connection, provider: {}, modelName: {}", config.getProvider(),
                config.getModelName());
        ChatModel tempModel = modelFactory.createChatModel(config);
        String response = tempModel.call("Hello");
        if (!StringUtils.hasText(response)) {
            throw new RuntimeException("模型返回内容为空");
        }
        log.info("Chat Model test passed. Response: {}", response);
    }

    private void testEmbeddingModel(ModelConfigDTO config) {
        log.info("Testing Embedding Model connection, provider: {} modelName: {}", config.getProvider(),
                config.getModelName());
        EmbeddingModel tempModel = modelFactory.createEmbeddingModel(config);
        float[] embedding = tempModel.embed("Test");
        if (embedding == null || embedding.length == 0) {
            throw new RuntimeException("模型生成的向量为空");
        }
        log.info("Embedding Model test passed. Dimension: {}", embedding.length);
    }

    private String parseErrorMessage(Exception e) {
        String message = e.getMessage() == null ? "连接测试失败" : e.getMessage();
        if (message.contains("401")) {
            return "鉴权失败 (401)，请检查 API Key 是否正确。";
        }
        if (message.contains("404")) {
            return "接口未找到 (404)，请检查 Base URL 或者路径配置地址。";
        }
        if (message.contains("429")) {
            return "请求过多或余额不足 (429)，请检查厂商额度。";
        }
        return message;
    }

}
