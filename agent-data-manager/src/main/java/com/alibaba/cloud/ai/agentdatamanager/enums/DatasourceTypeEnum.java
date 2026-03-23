package com.alibaba.cloud.ai.agentdatamanager.enums;

import java.util.Arrays;
import java.util.List;

public enum DatasourceTypeEnum {

    MYSQL(1, "mysql", "mysql", "jdbc", "MySQL"),
    POSTGRESQL(2, "postgresql", "postgresql", "jdbc", "PostgreSQL"),
    DAMENG(5, "dameng", "dameng", "jdbc", "Dameng"),
    SQL_SERVER(6, "sqlserver", "sqlserver", "jdbc", "SQL Server"),
    ORACLE(7, "oracle", "oracle", "jdbc", "Oracle"),
    HIVE(8, "hive", "hive", "jdbc", "Hive"),
    H2(4, "h2", "h2", "jdbc", "H2");

    private final Integer code;

    private final String typeName;

    private final String dialect;

    private final String protocol;

    private final String displayName;

    DatasourceTypeEnum(Integer code, String typeName, String dialect, String protocol, String displayName) {
        this.code = code;
        this.typeName = typeName;
        this.dialect = dialect;
        this.protocol = protocol;
        this.displayName = displayName;
    }

    public Integer getCode() {
        return code;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDialect() {
        return dialect;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DatasourceTypeEnum fromTypeName(String typeName) {
        for (DatasourceTypeEnum value : values()) {
            if (value.typeName.equalsIgnoreCase(typeName)) {
                return value;
            }
        }
        return null;
    }

    public static List<DatasourceTypeEnum> standardTypes() {
        return Arrays.asList(MYSQL, POSTGRESQL, DAMENG, SQL_SERVER, ORACLE, HIVE);
    }

}
