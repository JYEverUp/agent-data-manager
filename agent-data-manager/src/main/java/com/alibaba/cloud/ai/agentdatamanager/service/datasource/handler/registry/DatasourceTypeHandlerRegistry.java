
package com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.registry;

import com.alibaba.cloud.ai.agentdatamanager.service.datasource.handler.DatasourceTypeHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DatasourceTypeHandlerRegistry {

	private final Map<String, DatasourceTypeHandler> handlerMap = new ConcurrentHashMap<>();

	public DatasourceTypeHandlerRegistry(List<DatasourceTypeHandler> handlers) {
		handlers.forEach(this::register);
	}

	public void register(DatasourceTypeHandler handler) {
		handlerMap.put(normalizeType(handler.typeName()), handler);
	}

	public boolean isRegistered(String type) {
		return handlerMap.containsKey(normalizeType(type));
	}

	public DatasourceTypeHandler getRequired(String type) {
		if (!StringUtils.hasText(type)) {
			throw new IllegalArgumentException("Datasource type cannot be blank");
		}
		DatasourceTypeHandler handler = handlerMap.get(normalizeType(type));
		if (handler == null) {
			throw new IllegalStateException("Unsupported datasource type: " + type);
		}
		return handler;
	}

	private String normalizeType(String type) {
		if (!StringUtils.hasText(type)) {
			return "";
		}
		return type.trim().toLowerCase(Locale.ROOT);
	}

}
