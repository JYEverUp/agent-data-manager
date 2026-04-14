
package com.alibaba.cloud.ai.agentdatamanager.enums;

import java.util.Optional;

public enum DatabaseDialectEnum {

	MYSQL("MySQL"),

	SQLite("SQLite"),

	POSTGRESQL("PostgreSQL"),

	H2("H2"),

	DAMENG("Dameng"),

	SQL_SERVER("SqlServer"),

	ORACLE("Oracle"),

	HIVE("Hive");

	public final String code;

	DatabaseDialectEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Optional<DatabaseDialectEnum> getByCode(String code) {
		for (DatabaseDialectEnum value : values()) {
			if (value.code.equals(code)) {
				return Optional.of(value);
			}
		}
		return Optional.empty();
	}

}
