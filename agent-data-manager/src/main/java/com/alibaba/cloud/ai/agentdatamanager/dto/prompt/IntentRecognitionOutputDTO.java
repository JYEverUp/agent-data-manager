
package com.alibaba.cloud.ai.agentdatamanager.dto.prompt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.NoArgsConstructor;

// 对应 模板intent-recognition.txt的输出
@Data
@NoArgsConstructor
public class IntentRecognitionOutputDTO {

	// 意图分类结果，值为"《闲聊或无关指令》"或"《可能的数据分析请求》"
	@JsonProperty("classification")
	@JsonPropertyDescription("意图分类结果，值为：《闲聊或无关指令》或《可能的数据分析请求》")
	private String classification;

}
