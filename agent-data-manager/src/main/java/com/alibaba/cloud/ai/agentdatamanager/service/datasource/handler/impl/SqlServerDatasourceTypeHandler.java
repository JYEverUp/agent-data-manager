
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.impl;

import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.DatasourceTypeHandler;
import org.springframework.stereotype.Component;

@Component
public class SqlServerDatasourceTypeHandler implements DatasourceTypeHandler {

	@Override
	public String typeName() {
		return BizDataSourceTypeEnum.SQL_SERVER.getTypeName();
	}

}
