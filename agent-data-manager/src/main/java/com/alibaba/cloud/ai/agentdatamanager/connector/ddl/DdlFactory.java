
package com.alibaba.cloud.ai.agentdatamanager.connector.ddl;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DdlFactory {

	private final Map<String, Ddl> ddlExecutorSet = new ConcurrentHashMap<>();

	public DdlFactory(List<Ddl> ddls) {
		ddls.forEach(this::registry);
	}

	public void registry(Ddl ddlExecutor) {
		ddlExecutorSet.put(ddlExecutor.getDdlType(), ddlExecutor);
	}

	public boolean isRegistered(String type) {
		return ddlExecutorSet.containsKey(type);
	}

	public Ddl getDdlExecutorByDbConfig(DbConfigBO dbConfig) {
		BizDataSourceTypeEnum type = BizDataSourceTypeEnum.fromTypeName(dbConfig.getDialectType());
		if (type == null) {
			throw new RuntimeException("unknown db type");
		}
		return getDdlExecutorByDbType(type);
	}

	// todo: 写一层缓存
	public Ddl getDdlExecutorByDbType(BizDataSourceTypeEnum type) {
		return ddlExecutorSet.values()
			.stream()
			.filter(d -> d.supportedDataSourceType(type))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("no ddl executor found for " + type));
	}

	public Ddl getDdlExecutorByType(String type) {
		return ddlExecutorSet.get(type);
	}

}
