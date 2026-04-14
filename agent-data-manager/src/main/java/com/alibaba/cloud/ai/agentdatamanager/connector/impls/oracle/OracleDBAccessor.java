
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.oracle;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 */

@Service("oracleAccessor")
public class OracleDBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "Oracle_Accessor";

	protected OracleDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {

		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.ORACLE.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.ORACLE.getTypeName().equalsIgnoreCase(type);
	}

}
