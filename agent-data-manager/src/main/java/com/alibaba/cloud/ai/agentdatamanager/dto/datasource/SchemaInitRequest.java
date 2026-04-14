
package com.alibaba.cloud.ai.agentdatamanager.dto.datasource;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SchemaInitRequest implements Serializable {

	private DbConfigBO dbConfig;

	private List<String> tables;

	public DbConfigBO getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DbConfigBO dbConfig) {
		this.dbConfig = dbConfig;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "SchemaInitRequest{" + "dbConfig=" + dbConfig + ", tables=" + tables + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SchemaInitRequest that = (SchemaInitRequest) o;
		return Objects.equals(dbConfig, that.dbConfig) && Objects.equals(tables, that.tables);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dbConfig, tables);
	}

}
