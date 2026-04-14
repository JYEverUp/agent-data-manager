
package com.alibaba.cloud.ai.agentdatamanager.connector.impls.hive;

import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.AbstractAccessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPoolFactory;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Service;

/**
 * Hive 数据源访问器实现
 */
@Service("hiveAccessor")
public class HiveDBAccessor extends AbstractAccessor {

	private final static String ACCESSOR_TYPE = "Hive_Accessor";

	protected HiveDBAccessor(DdlFactory ddlFactory, DBConnectionPoolFactory poolFactory) {
		super(ddlFactory, poolFactory.getPoolByDbType(BizDataSourceTypeEnum.HIVE.getTypeName()));
	}

	@Override
	public String getAccessorType() {
		return ACCESSOR_TYPE;
	}

	@Override
	public boolean supportedDataSourceType(String type) {
		return BizDataSourceTypeEnum.HIVE.getTypeName().equalsIgnoreCase(type);
	}

}
