
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.DbAccessTypeEnum;
import org.springframework.util.StringUtils;

public interface DatasourceTypeHandler {

	String typeName();

	default String connectionType() {
		return DbAccessTypeEnum.JDBC.getCode();
	}

	default String dialectType() {
		return typeName();
	}

	default boolean supports(String type) {
		return typeName().equalsIgnoreCase(type);
	}

	default boolean hasRequiredConnectionFields(Datasource datasource) {
		return datasource.getHost() != null && datasource.getPort() != null && datasource.getDatabaseName() != null;
	}

	default String buildConnectionUrl(Datasource datasource) {
		return datasource.getConnectionUrl();
	}

	default String resolveConnectionUrl(Datasource datasource) {
		String existing = datasource.getConnectionUrl();
		if (StringUtils.hasText(existing)) {
			return existing;
		}
		return buildConnectionUrl(datasource);
	}

	default String extractSchemaName(Datasource datasource) {
		return datasource.getDatabaseName();
	}

	default DbConfigBO toDbConfig(Datasource datasource) {
		DbConfigBO config = new DbConfigBO();
		config.setUrl(resolveConnectionUrl(datasource));
		config.setUsername(datasource.getUsername());
		config.setPassword(datasource.getPassword());
		config.setConnectionType(connectionType());
		config.setDialectType(dialectType());
		config.setSchema(extractSchemaName(datasource));
		return config;
	}

	default String normalizeTestUrl(Datasource datasource, String url) {
		return url;
	}

}
