
package com.alibaba.cloud.ai.agentdatamanager.connector.accessor;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.bo.schema.*;
import com.alibaba.cloud.ai.agentdatamanager.connector.DbQueryParameter;
import com.alibaba.cloud.ai.agentdatamanager.connector.SqlExecutor;
import com.alibaba.cloud.ai.agentdatamanager.connector.accessor.Accessor;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.AbstractJdbcDdl;
import com.alibaba.cloud.ai.agentdatamanager.connector.ddl.DdlFactory;
import com.alibaba.cloud.ai.agentdatamanager.connector.pool.DBConnectionPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.List;

/**
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractAccessor implements Accessor {

	private final DdlFactory ddlFactory;

	private final DBConnectionPool dbConnectionPool;

	public <T> T accessDb(DbConfigBO dbConfig, String method, DbQueryParameter param) throws Exception {

		try (Connection connection = getConnection(dbConfig)) {

			AbstractJdbcDdl ddlExecutor = (AbstractJdbcDdl) ddlFactory.getDdlExecutorByDbConfig(dbConfig);

			switch (method) {
				case "showDatabases":
					return (T) ddlExecutor.showDatabases(connection);
				case "showSchemas":
					return (T) ddlExecutor.showSchemas(connection);
				case "showTables":
					return (T) ddlExecutor.showTables(connection, param.getSchema(), param.getTablePattern());
				case "fetchTables":
					return (T) ddlExecutor.fetchTables(connection, param.getSchema(), param.getTables());
				case "showColumns":
					return (T) ddlExecutor.showColumns(connection, param.getSchema(), param.getTable());
				case "showForeignKeys":
					return (T) ddlExecutor.showForeignKeys(connection, param.getSchema(), param.getTables());
				case "sampleColumn":
					return (T) ddlExecutor.sampleColumn(connection, param.getSchema(), param.getTable(),
							param.getColumn());
				case "scanTable":
					return (T) ddlExecutor.scanTable(connection, param.getSchema(), param.getTable());
				case "executeSqlAndReturnObject":
					return (T) SqlExecutor.executeSqlAndReturnObject(connection, param.getSchema(), param.getSql());
				default:
					throw new UnsupportedOperationException("Unknown method: " + method);
			}
		}
		catch (Exception e) {

			log.error("Error accessing database with method: {}, reason: {}", method, e.getMessage());
			throw e;
		}
	}

	public List<DatabaseInfoBO> showDatabases(DbConfigBO dbConfig) throws Exception {
		return accessDb(dbConfig, "showDatabases", null);
	}

	public List<SchemaInfoBO> showSchemas(DbConfigBO dbConfig) throws Exception {
		return accessDb(dbConfig, "showSchemas", null);
	}

	public List<TableInfoBO> showTables(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "showTables", param);
	}

	public List<TableInfoBO> fetchTables(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "fetchTables", param);
	}

	public List<ColumnInfoBO> showColumns(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "showColumns", param);
	}

	public List<ForeignKeyInfoBO> showForeignKeys(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "showForeignKeys", param);
	}

	public List<String> sampleColumn(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "sampleColumn", param);
	}

	public ResultSetBO scanTable(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "scanTable", param);
	}

	public ResultSetBO executeSqlAndReturnObject(DbConfigBO dbConfig, DbQueryParameter param) throws Exception {
		return accessDb(dbConfig, "executeSqlAndReturnObject", param);
	}

	public Connection getConnection(DbConfigBO config) {
		return this.dbConnectionPool.getConnection(config);
	}

}
