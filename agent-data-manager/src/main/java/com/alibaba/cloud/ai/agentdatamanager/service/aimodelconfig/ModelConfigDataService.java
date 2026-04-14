
package com.alibaba.cloud.ai.agentdatamanager.service.aimodelconfig;

import com.alibaba.cloud.ai.agentdatamanager.dto.ModelConfigDTO;
import com.alibaba.cloud.ai.agentdatamanager.entity.ModelConfig;
import com.alibaba.cloud.ai.agentdatamanager.enums.ModelType;

import java.util.List;

public interface ModelConfigDataService {

	ModelConfig findById(Integer id);

	void switchActiveStatus(Integer id, ModelType type);

	List<ModelConfigDTO> listConfigs();

	void addConfig(ModelConfigDTO dto);

	ModelConfig updateConfigInDb(ModelConfigDTO dto);

	void deleteConfig(Integer id);

	ModelConfigDTO getActiveConfigByType(ModelType modelType);

}
