
package com.alibaba.cloud.ai.agentdatamanager.service.graph;

import com.alibaba.cloud.ai.agentdatamanager.dto.GraphRequest;
import com.alibaba.cloud.ai.agentdatamanager.vo.GraphNodeResponse;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Sinks;

/**
 * @since 2025/10/30
 */
public interface GraphService {

	/**
	 * 自然语言转SQL，仅返回SQL代码结果
	 * @param naturalQuery 自然语言
	 * @param agentId Agent Id
	 * @return SQL结果
	 * @throws GraphRunnerException 图运行异常
	 */
	String nl2sql(String naturalQuery, String agentId) throws GraphRunnerException;

	/**
	 * 流式处理NL2SQL或者DataAgent请求
	 * @param sink 输出Sink
	 * @param graphRequest 请求体
	 */
	void graphStreamProcess(Sinks.Many<ServerSentEvent<GraphNodeResponse>> sink, GraphRequest graphRequest);

	/**
	 * 停止指定 threadId 的流式处理
	 * @param threadId 线程ID
	 */
	void stopStreamProcessing(String threadId);

}
