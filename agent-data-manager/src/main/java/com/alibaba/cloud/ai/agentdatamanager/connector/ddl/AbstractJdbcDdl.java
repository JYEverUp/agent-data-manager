
package com.alibaba.cloud.ai.agentdatamanager.connector.ddl;

import com.alibaba.cloud.ai.agentdatamanager.bo.schema.*;
import com.alibaba.cloud.ai.agentdatamanager.util.SqlUtil;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractJdbcDdl implements Ddl {

	@Deprecated
	public abstract List<DatabaseInfoBO> showDatabases(Connection connection);

	public abstract List<SchemaInfoBO> showSchemas(Connection connection);

	public abstract List<TableInfoBO> showTables(Connection connection, String schema, String tablePattern);

	public abstract List<TableInfoBO> fetchTables(Connection connection, String schema, List<String> tables);

	public abstract List<ColumnInfoBO> showColumns(Connection connection, String schema, String table);

	public abstract List<ForeignKeyInfoBO> showForeignKeys(Connection connection, String schema, List<String> tables);

	public abstract List<String> sampleColumn(Connection connection, String schema, String table, String column);

	public abstract ResultSetBO scanTable(Connection connection, String schema, String table);

	public String getSelectSql(String typeName, String tableName, String columnNames, int limit) {
		return SqlUtil.buildSelectSql(typeName, tableName, columnNames, limit);
	}

}
