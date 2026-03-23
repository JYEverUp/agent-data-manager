package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

    private Long id;

    private String name;

    private String description;

    private String avatar;

    private String status;

    private String apiKey;

    @Builder.Default
    private Integer apiKeyEnabled = 0;

    private String prompt;

    private String category;

    private Long adminId;

    private String tags;

    @Builder.Default
    private Integer humanReviewEnabled = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
