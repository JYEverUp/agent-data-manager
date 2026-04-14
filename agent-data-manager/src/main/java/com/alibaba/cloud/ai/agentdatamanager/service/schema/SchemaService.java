
package com.alibaba.cloud.ai.agentdatamanager.service.schema;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.dto.datasource.SchemaInitRequest;
import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SchemaDTO;
import org.springframework.ai.document.Document;

import java.util.List;

public interface SchemaService {

	Boolean schema(Integer datasourceId, SchemaInitRequest schemaInitRequest) throws Exception;

	List<Document> getTableDocumentsByDatasource(Integer datasourceId, String query);

	void extractDatabaseName(SchemaDTO schemaDTO, DbConfigBO dbConfig);

	void buildSchemaFromDocuments(String agentId, List<Document> columnDocumentList, List<Document> tableDocuments,
			SchemaDTO schemaDTO);

	List<Document> getTableDocuments(Integer datasourceId, List<String> tableNames);

	List<Document> getColumnDocumentsByTableName(Integer datasourceId, List<String> tableNames);

}
