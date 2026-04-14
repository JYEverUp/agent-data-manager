
package com.alibaba.cloud.ai.agentdatamanager.bo.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ResultSetBO implements Cloneable {

	private List<String> column;

	private List<Map<String, String>> data;

	private String errorMsg;

	@Override
	public ResultSetBO clone() {
		return ResultSetBO.builder()
			.column(new ArrayList<>(this.column))
			.data(this.data.stream().map(HashMap::new).collect(Collectors.toList()))
			.build();
	}

}
