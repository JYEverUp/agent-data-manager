
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.sqlserver;

import com.alibaba.cloud.ai.agentdatamanager.connector.pool.AbstractDBConnectionPool;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import static com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum.*;

/**
 * @date 2025/12/14 17:34
 */
@Service("sqlServerJdbcConnectionPool")
public class SqlServerJdbcConnectionPool extends AbstractDBConnectionPool {

	@Override
	public String getDriver() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
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
			case "S0001" -> DATABASE_NOT_EXIST_42000;
			case "42000" -> DATABASE_NOT_EXIST_42000;
			default -> OTHERS;
		};
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.SQL_SERVER.getTypeName().equalsIgnoreCase(type);
	}

	@Override
	public String getConnectionPoolType() {
		return BizDataSourceTypeEnum.SQL_SERVER.getTypeName();
	}

}
