
package com.alibaba.cloud.ai.agentdatamanager.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessKnowledgeVO {

	private Long id;

	private String businessTerm;

	private String description;

	private String synonyms;

	@JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
	private Boolean isRecall;

	private Long agentId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createdTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updatedTime;

	private String embeddingStatus;

	// 操作失败的错误信息
	private String errorMsg;

}
