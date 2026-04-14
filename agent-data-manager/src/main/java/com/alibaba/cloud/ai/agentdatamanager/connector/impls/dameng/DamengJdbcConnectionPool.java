
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.dameng;

import com.alibaba.cloud.ai.agentdatamanager.connector.pool.AbstractDBConnectionPool;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum;
import org.springframework.stereotype.Service;

import static com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum.OTHERS;

@Service("damengJdbcConnectionPool")
public class DamengJdbcConnectionPool extends AbstractDBConnectionPool {

	private static final String DRIVER = "dm.jdbc.driver.DmDriver";

	@Override
	public String getDriver() {
		return DRIVER;
	}

	@Override
	public ErrorCodeEnum errorMapping(String sqlState) {
		ErrorCodeEnum ret = ErrorCodeEnum.fromCode(sqlState);
		if (ret != null && ret != OTHERS) {
			return ret;
		}
		return OTHERS;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.DAMENG.getTypeName().equals(type);
	}

	@Override
	public String getConnectionPoolType() {
		return BizDataSourceTypeEnum.DAMENG.getTypeName();
	}

}
