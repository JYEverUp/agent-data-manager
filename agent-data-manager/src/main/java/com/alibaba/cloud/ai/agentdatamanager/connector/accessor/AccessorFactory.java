
package com.alibaba.cloud.ai.agentdatamanager.connector.accessor;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 2025/9/27
 */
@Component
public class AccessorFactory {

	public AccessorFactory(List<Accessor> accessors) {
		accessors.forEach(this::register);
	}

	private final Map<String, Accessor> accessorMap = new ConcurrentHashMap<>();

	public void register(Accessor accessor) {
		accessorMap.put(accessor.getAccessorType(), accessor);
	}

	public boolean isRegistered(String type) {
		return accessorMap.containsKey(type);
	}

	public Accessor getAccessorByDbConfig(DbConfigBO dbConfig) {
		if (dbConfig == null) {
			throw new IllegalArgumentException("dbConfig cannot be null");
		}
		BizDataSourceTypeEnum typeEnum = Arrays.stream(BizDataSourceTypeEnum.values())
			.filter(e -> e.getDialect().equalsIgnoreCase(dbConfig.getDialectType()))
			.filter(e -> e.getProtocol().equalsIgnoreCase(dbConfig.getConnectionType()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException(
					"no accessor registered for dialect: " + dbConfig.getDialectType()));
		return getAccessorByDbTypeEnum(typeEnum);
	}

	// todo: 写一层缓存
	public Accessor getAccessorByDbTypeEnum(BizDataSourceTypeEnum typeEnum) {
		return accessorMap.values()
			.stream()
			.filter(a -> a.supportedDataSourceType(typeEnum.getTypeName()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("no accessor registered for dialect: " + typeEnum));
	}

	public Accessor getAccessorByType(String type) {
		return accessorMap.get(type);
	}

}
