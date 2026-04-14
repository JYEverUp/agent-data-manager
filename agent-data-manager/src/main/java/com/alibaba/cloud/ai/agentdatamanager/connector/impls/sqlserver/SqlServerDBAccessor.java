
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.sqlserver;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @date 2025/12/14 17:34
 */
@Service("sqlserverAccessor")
public class SqlServerDBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "SqlServer_Accessor";

	public SqlServerDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {
		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.SQL_SERVER.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.SQL_SERVER.getTypeName().equalsIgnoreCase(type);
	}

}
