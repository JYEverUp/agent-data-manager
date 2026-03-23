package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModelConfig {

    private Integer id;

    private String provider;

    private String baseUrl;

    private String apiKey;

    private String modelName;

    private Double temperature;

    private Boolean isActive = false;

    private Integer maxTokens;

    private ModelType modelType;

    private String completionsPath;

    private String embeddingsPath;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Integer isDeleted;

    private Boolean proxyEnabled;

    private String proxyHost;

    private Integer proxyPort;

    private String proxyUsername;

    private String proxyPassword;

}
