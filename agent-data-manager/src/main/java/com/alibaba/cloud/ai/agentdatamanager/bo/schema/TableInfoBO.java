
package com.alibaba.cloud.ai.agentdatamanager.bo.schema;

import com.alibaba.cloud.ai.agentdatamanager.bo.schema.ColumnInfoBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfoBO {

	private String schema;

	private String name;

	private String description;

	private String type;

	private String foreignKey;

	private List<String> primaryKeys;

	private List<ColumnInfoBO> columns;

}
