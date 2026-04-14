
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.h2;

import com.alibaba.cloud.ai.agentdatamanager.connector.pool.AbstractDBConnectionPool;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import static com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum.*;

@Service("h2JdbcConnectionPool")
public class H2JdbcConnectionPool extends AbstractDBConnectionPool {

	@Override
	public String getDriver() {
		return "org.h2.Driver";
	}

	@Override
	public ErrorCodeEnum errorMapping(String sqlState) {
		ErrorCodeEnum ret = ErrorCodeEnum.fromCode(sqlState);
		if (ret != null) {
			return ret;
		}
		return switch (sqlState) {
			case "08S01" -> DATASOURCE_CONNECTION_FAILURE_08S01;
			case "28000" -> PASSWORD_ERROR_28000;
			case "42000" -> DATABASE_NOT_EXIST_42000;
			default -> OTHERS;
		};
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.H2.getTypeName().equals(type);
	}

	@Override
	public String getConnectionPoolType() {
		return BizDataSourceTypeEnum.H2.getTypeName();
	}

}
