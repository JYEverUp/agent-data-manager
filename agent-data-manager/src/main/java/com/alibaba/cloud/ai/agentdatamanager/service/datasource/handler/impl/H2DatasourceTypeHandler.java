
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.impl;

import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.DatasourceTypeHandler;
import org.springframework.stereotype.Component;

@Component
public class H2DatasourceTypeHandler implements DatasourceTypeHandler {

	@Override
	public String typeName() {
		return BizDataSourceTypeEnum.H2.getTypeName();
	}

	@Override
	public String buildConnectionUrl(Datasource datasource) {
		if (!hasRequiredConnectionFields(datasource)) {
			return datasource.getConnectionUrl();
		}
		return String.format(
				"jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=true;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE",
				datasource.getDatabaseName());
	}

}
