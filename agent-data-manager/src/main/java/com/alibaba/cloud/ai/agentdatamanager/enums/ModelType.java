
package com.alibaba.cloud.ai.agentdatamanager.enums;

import lombok.Getter;

/**
 * 模型类型枚举
 */
@Getter
public enum ModelType {

	/**
	 * 对话模型
	 */
	CHAT("CHAT"),

	/**
	 * 嵌入模型
	 */
	EMBEDDING("EMBEDDING");

	private final String code;

	ModelType(String code) {
		this.code = code;
	}

	/**
	 * 根据代码获取枚举
	 */
	public static ModelType fromCode(String code) {
		for (ModelType type : values()) {
			// 严格比对
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("未知的模型类型代码: " + code);
	}

}
