
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.impl;

import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.DatasourceTypeHandler;
import org.springframework.stereotype.Component;

@Component
public class PostgreSqlDatasourceTypeHandler implements DatasourceTypeHandler {

	@Override
	public String typeName() {
		return BizDataSourceTypeEnum.POSTGRESQL.getTypeName();
	}

	@Override
	public String buildConnectionUrl(Datasource datasource) {
		if (!hasRequiredConnectionFields(datasource)) {
			return datasource.getConnectionUrl();
		}
		// 提取数据库名（format: "database|schema"，只取database部分）
		String databaseName = datasource.getDatabaseName();
		if (databaseName != null && databaseName.contains("|")) {
			databaseName = databaseName.split("\\|")[0];
		}
		return String.format(
				"jdbc:postgresql://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
				datasource.getHost(), datasource.getPort(), databaseName);
	}

	@Override
	public String extractSchemaName(Datasource datasource) {
		// 提取schema名（format: "database|schema"，取schema部分）
		String databaseName = datasource.getDatabaseName();
		if (databaseName != null && databaseName.contains("|")) {
			String[] parts = databaseName.split("\\|");
			return parts.length > 1 ? parts[1] : parts[0];
		}
		return databaseName;
	}

}
