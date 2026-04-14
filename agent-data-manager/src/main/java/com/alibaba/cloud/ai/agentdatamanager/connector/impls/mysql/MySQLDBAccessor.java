
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.mysql;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 */

@Service("mysqlAccessor")
public class MySQLDBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "MySQL_Accessor";

	protected MySQLDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {

		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.MYSQL.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.MYSQL.getTypeName().equalsIgnoreCase(type);
	}

}
