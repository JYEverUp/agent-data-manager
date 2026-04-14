
package com.alibaba.cloud.ai.agentdatamanager.connector.pool;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DB connection pool factory
 */
@Component
public class DBConnectionPoolFactory {

	private final Map<String, DBConnectionPool> poolMap = new ConcurrentHashMap<>();

	public DBConnectionPoolFactory(List<DBConnectionPool> pools) {
		pools.forEach(this::register);
	}

	public void register(DBConnectionPool pool) {
		poolMap.put(pool.getConnectionPoolType(), pool);
	}

	public boolean isRegistered(String type) {
		return poolMap.containsKey(type);
	}

	/**
	 * Get corresponding DB connection pool based on database type
	 * @param type database type
	 * @return DB connection pool
	 */
	public DBConnectionPool getPoolByType(String type) {
		DBConnectionPool direct = poolMap.get(type);
		if (direct != null) {
			return direct;
		}
		return poolMap.values().stream().filter(p -> p.supportedDataSourceType(type)).findFirst().orElse(null);
	}

	// todo: 写一层缓存
	public DBConnectionPool getPoolByDbType(String type) {
		return poolMap.values()
			.stream()
			.filter(p -> p.supportedDataSourceType(type))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No DB connection pool found for type: " + type));
	}

}
