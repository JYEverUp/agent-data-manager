
package com.alibaba.cloud.ai.agentdatamanager.service.nl2sql;

import com.alibaba.cloud.ai.agentdatamanager.bo.DbConfigBO;
import com.alibaba.cloud.ai.agentdatamanager.dto.prompt.SemanticConsistencyDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.prompt.SqlGenerationDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SchemaDTO;
import com.alibaba.cloud.ai.agentdatamanager.util.MarkdownParserUtil;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

public interface Nl2SqlService {

	Flux<ChatResponse> performSemanticConsistency(SemanticConsistencyDTO semanticConsistencyDTO);

	Flux<String> generateSql(SqlGenerationDTO sqlGenerationDTO);

	Flux<ChatResponse> fineSelect(SchemaDTO schemaDTO, String query, String evidence,
			String sqlGenerateSchemaMissingAdvice, DbConfigBO specificDbConfig, Consumer<SchemaDTO> dtoConsumer);

	default String sqlTrim(String sql) {
		return MarkdownParserUtil.extractRawText(sql).trim();
	}

}
