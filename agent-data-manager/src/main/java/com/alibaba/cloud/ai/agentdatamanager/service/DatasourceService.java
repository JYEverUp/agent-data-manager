package com.alibaba.cloud.ai.agentdatamanager.service;

import com.alibaba.cloud.ai.agentdatamanager.entity.Datasource;
import com.alibaba.cloud.ai.agentdatamanager.enums.DatasourceTypeEnum;
import com.alibaba.cloud.ai.agentdatamanager.exception.BusinessException;
import com.alibaba.cloud.ai.agentdatamanager.mapper.DatasourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasourceService {

    private final DatasourceMapper datasourceMapper;

    public List<Datasource> getAllDatasource() {
        return datasourceMapper.selectAll();
    }

    public List<Datasource> getDatasourceByStatus(String status) {
        return datasourceMapper.selectByStatus(status);
    }

    public List<Datasource> getDatasourceByType(String type) {
        return datasourceMapper.selectByType(type);
    }

    public Datasource getDatasourceById(Integer id) {
        return datasourceMapper.selectById(id);
    }

    public Datasource createDatasource(Datasource datasource) {
        fillDefaults(datasource);
        datasource.setConnectionUrl(resolveConnectionUrl(datasource));
        datasourceMapper.insert(datasource);
        return datasource;
    }

    public Datasource updateDatasource(Integer id, Datasource datasource) {
        Datasource existing = requireDatasource(id);
        datasource.setId(id);
        datasource.setConnectionUrl(resolveConnectionUrl(datasource));
        if (datasource.getStatus() == null) {
            datasource.setStatus(existing.getStatus());
        }
        if (datasource.getTestStatus() == null) {
            datasource.setTestStatus(existing.getTestStatus());
        }
        if (datasource.getUsername() == null) {
            datasource.setUsername("");
        }
        if (datasource.getPassword() == null) {
            datasource.setPassword("");
        }
        datasourceMapper.updateById(datasource);
        return datasourceMapper.selectById(id);
    }

    public void deleteDatasource(Integer id) {
        if (datasourceMapper.deleteById(id) == 0) {
            throw new BusinessException("Datasource not found: " + id);
        }
    }

    public boolean testConnection(Integer id) {
        Datasource datasource = getDatasourceById(id);
        if (datasource == null) {
            return false;
        }
        try {
            boolean connectionSuccess = realConnectionTest(datasource);
            log.info("{} test connection result: {}", datasource.getName(), connectionSuccess);
            datasourceMapper.updateTestStatusById(id, connectionSuccess ? "success" : "failed");
            return connectionSuccess;
        }
        catch (Exception ex) {
            datasourceMapper.updateTestStatusById(id, "failed");
            log.error("Error testing connection for datasource ID {}: {}", id, ex.getMessage(), ex);
            return false;
        }
    }

    private boolean realConnectionTest(Datasource datasource) {
        try (Connection ignored = createConnection(datasource, true)) {
            return true;
        }
        catch (Exception ex) {
            log.error("Datasource connection test failed for type={}, name={}, message={}", datasource.getType(),
                    datasource.getName(), ex.getMessage());
            return false;
        }
    }

    public List<String> getDatasourceTables(Integer datasourceId) throws Exception {
        log.info("Getting tables for datasource: {}", datasourceId);

        Datasource datasource = requireDatasource(datasourceId);
        try (Connection connection = createConnection(datasource, false)) {
            List<String> tableNames = queryDatasourceTables(connection, datasource).stream()
                    .distinct()
                    .sorted(Comparator.naturalOrder())
                    .toList();
            log.info("Found {} tables for datasource: {}", tableNames.size(), datasourceId);
            return tableNames;
        }
    }

    private List<String> queryDatasourceTables(Connection connection, Datasource datasource) throws Exception {
        return switch (normalizeType(datasource.getType())) {
            case "mysql" -> queryTableNames(connection,
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? LIMIT 2000",
                    connection.getCatalog());
            case "postgresql" -> queryTableNames(connection,
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = ? LIMIT 2000",
                    resolveSchema(datasource));
            case "oracle" -> queryTableNames(connection,
                    "SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = ? AND ROWNUM <= 2000 ORDER BY TABLE_NAME",
                    resolveOracleSchema(connection, datasource));
            case "sqlserver" -> queryTableNames(connection,
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = 'BASE TABLE' "
                            + "ORDER BY TABLE_NAME OFFSET 0 ROWS FETCH NEXT 2000 ROWS ONLY",
                    resolveSqlServerSchema(connection, datasource));
            case "hive" -> queryHiveTableNames(connection, datasource);
            case "dameng" -> queryTableNames(connection, "SELECT TABLE_NAME FROM USER_TABLES");
            case "h2" -> queryTableNames(connection,
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? LIMIT 2000",
                    resolveH2Schema(connection, datasource));
            default -> queryByMetadata(connection, datasource);
        };
    }

    private List<String> queryByMetadata(Connection connection, Datasource datasource) throws Exception {
        List<String> result = new ArrayList<>();
        String catalog = resolveCatalog(datasource);
        String schema = resolveSchema(datasource);
        try (ResultSet rs = connection.getMetaData().getTables(catalog, schema, "%", new String[] { "TABLE" })) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (StringUtils.isNotBlank(tableName)) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    private List<String> queryHiveTableNames(Connection connection, Datasource datasource) throws Exception {
        String schema = resolveSchema(datasource);
        String sql = StringUtils.isNotBlank(schema) ? "SHOW TABLES IN " + schema : "SHOW TABLES";
        return queryTableNames(connection, sql);
    }

    private List<String> queryTableNames(Connection connection, String sql, String... params) throws Exception {
        List<String> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    if (StringUtils.isNotBlank(tableName)) {
                        result.add(tableName);
                    }
                }
            }
        }
        return result;
    }

    private Datasource requireDatasource(Integer id) {
        Datasource datasource = datasourceMapper.selectById(id);
        if (datasource == null) {
            throw new BusinessException("Datasource not found: " + id);
        }
        return datasource;
    }

    private void fillDefaults(Datasource datasource) {
        if (datasource.getStatus() == null) {
            datasource.setStatus("active");
        }
        if (datasource.getTestStatus() == null) {
            datasource.setTestStatus("unknown");
        }
        if (datasource.getUsername() == null) {
            datasource.setUsername("");
        }
        if (datasource.getPassword() == null) {
            datasource.setPassword("");
        }
    }

    private Connection createConnection(Datasource datasource, boolean normalizeForTest) throws Exception {
        String url = resolveConnectionUrl(datasource);
        if (normalizeForTest) {
            url = normalizeTestUrl(datasource, url);
        }
        return DriverManager.getConnection(url, datasource.getUsername(), datasource.getPassword());
    }

    private String resolveConnectionUrl(Datasource datasource) {
        if (StringUtils.isNotBlank(datasource.getConnectionUrl())) {
            return datasource.getConnectionUrl();
        }
        DatasourceTypeEnum type = DatasourceTypeEnum.fromTypeName(datasource.getType());
        if (type == null) {
            throw new BusinessException("Unsupported datasource type: " + datasource.getType());
        }
        String host = StringUtils.defaultString(datasource.getHost());
        Integer port = datasource.getPort();
        String db = StringUtils.defaultString(datasource.getDatabaseName());
        return switch (type) {
            case MYSQL -> String.format(
                    "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowMultiQueries=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai",
                    host, port, db);
            case POSTGRESQL -> {
                String databaseName = db;
                if (databaseName.contains("|")) {
                    databaseName = databaseName.split("\\|")[0];
                }
                yield String.format(
                        "jdbc:postgresql://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
                        host, port, databaseName);
            }
            case DAMENG -> String.format("jdbc:dm://%s:%d", host, port);
            case SQL_SERVER -> "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + db + ";encrypt=false";
            case ORACLE -> String.format("jdbc:oracle:thin:@%s:%d/%s", host, port, db);
            case HIVE -> String.format("jdbc:hive2://%s:%d/%s", host, port, db);
            case H2 -> String.format(
                    "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=true;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE", db);
        };
    }

    private String normalizeTestUrl(Datasource datasource, String url) {
        if ("mysql".equalsIgnoreCase(datasource.getType())) {
            String updated = url;
            String lowerUrl = updated.toLowerCase(Locale.ROOT);
            if (!lowerUrl.contains("servertimezone=")) {
                updated = appendParam(updated, "serverTimezone", "Asia/Shanghai");
                lowerUrl = updated.toLowerCase(Locale.ROOT);
            }
            if (!lowerUrl.contains("usessl=")) {
                updated = appendParam(updated, "useSSL", "false");
            }
            return updated;
        }
        return url;
    }

    private String appendParam(String url, String key, String value) {
        return url + (url.contains("?") ? "&" : "?") + key + "=" + value;
    }

    private String resolveCatalog(Datasource datasource) {
        if ("mysql".equalsIgnoreCase(datasource.getType())) {
            return datasource.getDatabaseName();
        }
        return null;
    }

    private String resolveSchema(Datasource datasource) {
        if ("postgresql".equalsIgnoreCase(datasource.getType())) {
            String databaseName = datasource.getDatabaseName();
            if (databaseName != null && databaseName.contains("|")) {
                String[] parts = databaseName.split("\\|");
                return parts.length > 1 ? parts[1] : parts[0];
            }
            return databaseName;
        }
        if ("oracle".equalsIgnoreCase(datasource.getType())) {
            String databaseName = datasource.getDatabaseName();
            if (databaseName != null && databaseName.contains("|")) {
                String[] parts = databaseName.split("\\|");
                if (parts.length == 2) {
                    return parts[1];
                }
            }
            return null;
        }
        if ("dameng".equalsIgnoreCase(datasource.getType())) {
            return StringUtils.upperCase(datasource.getUsername());
        }
        return null;
    }

    private String resolveOracleSchema(Connection connection, Datasource datasource) throws Exception {
        String schema = resolveSchema(datasource);
        if (StringUtils.isNotBlank(schema)) {
            return StringUtils.upperCase(schema);
        }
        String currentSchema = connection.getSchema();
        if (StringUtils.isNotBlank(currentSchema)) {
            return StringUtils.upperCase(currentSchema);
        }
        return StringUtils.upperCase(connection.getMetaData().getUserName());
    }

    private String resolveSqlServerSchema(Connection connection, Datasource datasource) throws Exception {
        String schema = resolveSchema(datasource);
        if (StringUtils.isNotBlank(schema)) {
            return schema;
        }
        String currentSchema = connection.getSchema();
        return StringUtils.isNotBlank(currentSchema) ? currentSchema : "dbo";
    }

    private String resolveH2Schema(Connection connection, Datasource datasource) throws Exception {
        String schema = resolveSchema(datasource);
        if (StringUtils.isNotBlank(schema)) {
            return schema;
        }
        String currentSchema = connection.getSchema();
        return StringUtils.isNotBlank(currentSchema) ? currentSchema : "PUBLIC";
    }

    private String normalizeType(String type) {
        return StringUtils.defaultString(type).toLowerCase(Locale.ROOT);
    }

}
