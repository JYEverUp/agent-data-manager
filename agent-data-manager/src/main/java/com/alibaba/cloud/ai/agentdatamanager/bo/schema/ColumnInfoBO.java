
package com.alibaba.cloud.ai.agentdatamanager.bo.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfoBO {

	private String name;

	private String tableName;

	private String description;

	private String type;

	private boolean primary;

	private boolean notnull;

	private String samples;

}
