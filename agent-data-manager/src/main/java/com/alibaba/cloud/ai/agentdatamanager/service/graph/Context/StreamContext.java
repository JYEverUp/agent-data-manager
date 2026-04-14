
package com.alibaba.cloud.ai.agentdatamanager.service.graph.Context;

import com.alibaba.cloud.ai.agentdatamanager.enums.TextType;
import com.alibaba.cloud.ai.agentdatamanager.vo.GraphNodeResponse;
import io.opentelemetry.api.trace.Span;
import lombok.Data;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 流式处理上下文，封装每个 threadId 的所有相关状态
 *
 * @since 2025/11/28
 */
@Data
public class StreamContext {

	private Disposable disposable;

	private Sinks.Many<ServerSentEvent<GraphNodeResponse>> sink;

	private Span span;

	private TextType textType;

	/**
	 * 收集流式输出内容，用于 Langfuse 上报
	 */
	private final StringBuilder outputCollector = new StringBuilder();

	public void appendOutput(String chunk) {
		outputCollector.append(chunk);
	}

	public String getCollectedOutput() {
		return outputCollector.toString();
	}

	/**
	 * 标记是否已经清理，用于防止重复清理
	 */
	private final AtomicBoolean cleaned = new AtomicBoolean(false);

	/**
	 * 清理所有资源 线程安全：使用 AtomicBoolean 确保只执行一次
	 */
	public void cleanup() {
		// 使用 compareAndSet 确保只执行一次清理
		if (!cleaned.compareAndSet(false, true)) {
			return;
		}

		// 清理 Disposable
		Disposable localDisposable = disposable;
		if (localDisposable != null && !localDisposable.isDisposed()) {
			try {
				localDisposable.dispose();
			}
			catch (Exception e) {
				// 忽略清理过程中的异常
			}
		}

		// 清理 Sink
		Sinks.Many<ServerSentEvent<GraphNodeResponse>> localSink = sink;
		if (localSink != null) {
			try {
				localSink.tryEmitComplete();
			}
			catch (Exception e) {
				// 忽略清理过程中的异常
			}
		}
	}

	/**
	 * 检查是否已经清理
	 */
	public boolean isCleaned() {
		return cleaned.get();
	}

}
