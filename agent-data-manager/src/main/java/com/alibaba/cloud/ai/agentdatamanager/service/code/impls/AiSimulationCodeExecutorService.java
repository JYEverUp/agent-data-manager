
package com.alibaba.cloud.ai.agentdatamanager.service.code.impls;

import com.alibaba.cloud.ai.agentdatamanager.service.code.CodePoolExecutorService;
import com.alibaba.cloud.ai.agentdatamanager.service.llm.LlmService;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用AI模拟运行Python代码（便于在无Docker环境测试）
 *
 * @since 2025/7/30
 */
@Slf4j
public class AiSimulationCodeExecutorService implements CodePoolExecutorService {

	private static final String SYSTEM_PROMPT = """
			你将模拟Python的执行，根据我提供的代码和输入数据，并给出最终的数据结果。
			在模拟运行时，请按照以下要求操作：
			1. 仔细理解代码和输入数据的内容。
			2. 输出模拟运行结果。
			**要求**：仅输出模拟运行结果，禁止包含任何额外说明或自然语言。
			""";

	private final LlmService llmService;

	public AiSimulationCodeExecutorService(LlmService llmService) {
		this.llmService = llmService;
	}

	@Override
	public TaskResponse runTask(TaskRequest request) {
		String userPrompt = String.format("""
				【代码】
				```python
				%s
				```
				【标准输入】
				```json
				%s
				```
				""", request.code(), request.input());
		String output = llmService.toStringFlux(llmService.call(SYSTEM_PROMPT, userPrompt))
			.collect(StringBuilder::new, StringBuilder::append)
			.map(StringBuilder::toString)
			.block();
		return TaskResponse.success(output);
	}

}
