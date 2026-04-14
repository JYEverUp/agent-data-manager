
package com.alibaba.cloud.ai.agentdatamanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Datasource {

	private Integer id;

	private String name;

	private String type;

	private String host;

	private Integer port;

	private String databaseName;

	private String username;

	private String password;

	private String connectionUrl;

	private String status;

	private String testStatus;

	private String description;

	private Long creatorId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	@Override
	public String toString() {
		return "Datasource{" + "id=" + id + ", name='" + name + '\'' + ", type='" + type + '\'' + ", host='" + host
				+ '\'' + ", port=" + port + ", databaseName='" + databaseName + '\'' + ", status='" + status + '\''
				+ ", testStatus='" + testStatus + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ '}';
	}

}
