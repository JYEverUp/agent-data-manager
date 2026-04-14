
package com.alibaba.cloud.ai.agentdatamanager.connector.accessor;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.bo.schema.*;
import com.alibaba.cloud.ai.agentdatamanager.connector.DbQueryParameter;
import com.alibaba.cloud.ai.agentdatamanager.enums.BizDataSourceTypeEnum;

import java.util.List;

/**
 * Data access interface definition.
 *
 */

public interface Accessor {

	String getAccessorType();

	boolean supportedDataSourceType(String type);

	default boolean supportedDataSourceType(BizDataSourceTypeEnum typeEnum) {
		return supportedDataSourceType(typeEnum.getTypeName());
	}

	/**
	 * Access the database and execute the specified method with the given parameters.
	 * @param dbConfig database configuration
	 * @param method method name
	 * @param param query parameters
	 * @return result object, which can be a list of database information, schema
	 * information, table information, etc.
	 * @throws Exception if an error occurs during database access
	 */
	<T> T accessDb(DbConfigBO dbConfig, String method, DbQueryParameter param) throws Exception;

	List<DatabaseInfoBO> showDatabases(DbConfigBO dbConfig) throws Exception;

	List<SchemaInfoBO> showSchemas(DbConfigBO dbConfig) throws Exception;

	List<TableInfoBO> showTables(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	List<TableInfoBO> fetchTables(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	List<ColumnInfoBO> showColumns(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	List<ForeignKeyInfoBO> showForeignKeys(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	List<String> sampleColumn(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	ResultSetBO scanTable(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

	ResultSetBO executeSqlAndReturnObject(DbConfigBO dbConfig, DbQueryParameter param) throws Exception;

}
