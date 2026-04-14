
package com.alibaba.cloud.ai.agentdatamanager.connector.ddl;

import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;

public interface Ddl {

	BizDataSourceTypeEnum getDataSourceType();

	default boolean supportedDataSourceType(String type) {
		return getDataSourceType().getTypeName().equals(type);
	}

	default boolean supportedDataSourceType(BizDataSourceTypeEnum type) {
		return getDataSourceType().equals(type);
	}

	default String getDdlType() {
		return getDataSourceType().getProtocol() + "@" + getDataSourceType().getDialect();
	}

}
