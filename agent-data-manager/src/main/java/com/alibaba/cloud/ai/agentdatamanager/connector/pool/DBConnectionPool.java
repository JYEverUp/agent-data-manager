
package com.alibaba.cloud.ai.agentdatamanager.connector.pool;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.enums.ErrorCodeEnum;

import java.sql.Connection;

/**
 * DataAgent data connection pool, used to maintain the data source connection information
 * required by DataAgent
 */

public interface DBConnectionPool extends AutoCloseable {

	/**
	 * Ping the database to check if the connection is valid.
	 * @param config the database configuration
	 * @return ErrorCodeEnum indicating the result of the ping operation
	 */
	ErrorCodeEnum ping(DbConfigBO config);

	/**
	 * Get a database connection from the pool.
	 * @param config the database configuration
	 * @return a Connection object representing the database connection
	 */
	Connection getConnection(DbConfigBO config);

	boolean supportedDataSourceType(String type);

	String getConnectionPoolType();

}
