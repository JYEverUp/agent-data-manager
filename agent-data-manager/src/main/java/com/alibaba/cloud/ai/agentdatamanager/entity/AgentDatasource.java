
package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDatasource {

	private Integer id;

	private Long agentId;

	private Integer datasourceId;

	private Integer isActive;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	// Associated data source object (for joint query)
	private Datasource datasource;

	// 当前数据源选中的表
	private List<String> selectTables;

	public AgentDatasource(Long agentId, Integer datasourceId) {
		this.agentId = agentId;
		this.datasourceId = datasourceId;
		this.isActive = 1;
	}

}
