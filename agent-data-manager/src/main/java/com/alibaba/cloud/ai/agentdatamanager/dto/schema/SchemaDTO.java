
package com.alibaba.cloud.ai.agentdatamanager.dto.schema;

import com.alibaba.cloud.ai.agentdatamanager.dto.schema.TableDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SchemaDTO {

	private String name;

	private String description;

	private Integer tableCount;

	private List<TableDTO> table;

	private List<String> foreignKeys;

	@Override
	public String toString() {
		return "SchemaDTO{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", tableCount="
				+ tableCount + ", table=" + table + ", foreignKeys=" + foreignKeys + '}';
	}

}
