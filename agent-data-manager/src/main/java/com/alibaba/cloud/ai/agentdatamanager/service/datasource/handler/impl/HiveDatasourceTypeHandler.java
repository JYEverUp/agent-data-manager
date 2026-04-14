
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.impl;

import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.DatasourceTypeHandler;
import org.springframework.stereotype.Component;

/**
 * Hive 数据源类型处理器
 */
@Component
public class HiveDatasourceTypeHandler implements DatasourceTypeHandler {

	@Override
	public String typeName() {
		return BizDataSourceTypeEnum.HIVE.getTypeName();
	}

	@Override
	public String buildConnectionUrl(Datasource datasource) {
		if (!hasRequiredConnectionFields(datasource)) {
			return datasource.getConnectionUrl();
		}

		return String.format("jdbc:hive2://%s:%d/%s", datasource.getHost(), datasource.getPort(),
				datasource.getDatabaseName());
	}

	@Override
	public String normalizeTestUrl(Datasource datasource, String url) {
		return url;
	}

}
