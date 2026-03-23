package com.alibaba.cloud.ai.agentdatamanager.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelCheckVo {

    private boolean chatModelReady;

    private boolean embeddingModelReady;

    private boolean ready;

}
