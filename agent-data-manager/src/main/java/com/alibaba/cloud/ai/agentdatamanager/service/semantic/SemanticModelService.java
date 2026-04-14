
package com.alibaba.cloud.ai.agentdatamanager.service.semantic;

import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SemanticModelAddDTO;
import com.alibaba.cloud.ai.agentdatamanager.dto.schema.SemanticModelBatchImportDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.SemanticModel;
import com.alibaba.cloud.ai.agentdatamanager.vo.BatchImportResult;

import java.io.InputStream;
import java.util.List;

public interface SemanticModelService {

	List<SemanticModel> getAll();

	List<SemanticModel> getEnabledByAgentId(Long agentId);

	List<SemanticModel> getByAgentIdAndTableNames(Long agentId, List<String> tableNames);

	SemanticModel getById(Long id);

	void addSemanticModel(SemanticModel semanticModel);

	boolean addSemanticModel(SemanticModelAddDTO dto);

	void enableSemanticModel(Long id);

	void disableSemanticModel(Long id);

	List<SemanticModel> getByAgentId(Long agentId);

	List<SemanticModel> search(String keyword);

	void deleteSemanticModel(Long id);

	void updateSemanticModel(Long id, SemanticModel semanticModel);

	default void addSemanticModels(List<SemanticModel> semanticModels) {
		semanticModels.forEach(this::addSemanticModel);
	}

	default void enableSemanticModels(List<Long> ids) {
		ids.forEach(this::enableSemanticModel);
	}

	default void disableSemanticModels(List<Long> ids) {
		ids.forEach(this::disableSemanticModel);
	}

	default void deleteSemanticModels(List<Long> ids) {
		ids.forEach(this::deleteSemanticModel);
	}

	BatchImportResult batchImport(SemanticModelBatchImportDTO dto);

	/**
	 * 从Excel文件导入语义模型
	 * @param inputStream Excel文件输入流
	 * @param filename 文件名
	 * @param agentId 智能体ID
	 * @return 导入结果
	 */
	BatchImportResult importFromExcel(InputStream inputStream, String filename, Long agentId);

}
