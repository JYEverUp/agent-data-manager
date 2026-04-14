
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.h2;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 */

@Service("h2Accessor")
public class H2DBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "H2_Accessor";

	protected H2DBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {

		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.H2.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.H2.getTypeName().equalsIgnoreCase(type);
	}

}
