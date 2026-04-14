
package com.alibaba.cloud.ai.agentdatamanager.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbConfigBO {

	private String schema;

	private String url;

	private String username;

	private String password;

	private String connectionType;

	private String dialectType;

}
