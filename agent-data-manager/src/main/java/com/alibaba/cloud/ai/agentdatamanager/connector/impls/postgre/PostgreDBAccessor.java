
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.postgre;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 */

@Service("postgreAccessor")
public class PostgreDBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "PostgreSQL_Accessor";

	protected PostgreDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {

		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.POSTGRESQL.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.POSTGRESQL.getTypeName().equalsIgnoreCase(type);
	}

}
