
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.dameng;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

@Service("damengAccessor")
public class DamengDBAccessor extends AbstractAccessor {

	private static final String ACCESSOR_TYPE = "Dameng_Accessor";

	protected DamengDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {
		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.DAMENG.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.DAMENG.getTypeName().equalsIgnoreCase(type);
	}

}
