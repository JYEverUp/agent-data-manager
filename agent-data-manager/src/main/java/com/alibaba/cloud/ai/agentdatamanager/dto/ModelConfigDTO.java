package com.alibaba.cloud.ai.agentdatamanager.dto;

import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelConfigDTO {

    private Integer id;

    @NotBlank(message = "provider must not be empty")
    private String provider;

    private String apiKey;

    @NotBlank(message = "baseUrl must not be empty")
    private String baseUrl;

    @NotBlank(message = "modelName must not be empty")
    private String modelName;

    @NotNull(message = "modelType must not be empty")
    private ModelType modelType;

    private String completionsPath;

    private String embeddingsPath;

    @DecimalMin(value = "0.0", message = "temperature must be >= 0")
    @DecimalMax(value = "2.0", message = "temperature must be <= 2")
    @Builder.Default
    private Double temperature = 0.0;

    @Min(value = 100, message = "maxTokens must be >= 100")
    @Max(value = 10000, message = "maxTokens must be <= 10000")
    @Builder.Default
    private Integer maxTokens = 2000;

    @Builder.Default
    private Boolean isActive = Boolean.FALSE;

    @Builder.Default
    private Boolean proxyEnabled = Boolean.FALSE;

    private String proxyHost;

    private Integer proxyPort;

    private String proxyUsername;

    private String proxyPassword;

}
