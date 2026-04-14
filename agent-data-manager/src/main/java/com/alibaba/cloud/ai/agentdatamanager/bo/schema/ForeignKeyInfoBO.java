
package com.alibaba.cloud.ai.agentdatamanager.bo.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignKeyInfoBO {

	private String table;

	private String column;

	private String referencedTable;

	private String referencedColumn;

}
