
package com.alibaba.cloud.ai.agentdatamanager.util;

import com.alibaba.cloud.ai.agentdatamanager.enums.TextType;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import java.util.List;

/**
 */
public class ChatResponseUtil {

	public static ChatResponse createResponse(String statusMessage) {
		return createPureResponse(statusMessage + "\n");
	}

	public static ChatResponse createPureResponse(String message) {
		AssistantMessage assistantMessage = new AssistantMessage(message);
		Generation generation = new Generation(assistantMessage);
		return new ChatResponse(List.of(generation));
	}

	// 这样无法达到效果，先弃用。如果不得不需要这个逻辑，再重新定义
	@Deprecated
	public static ChatResponse createTrimResponse(String message, TextType textType) {
		return createPureResponse(message.replace(textType.getStartSign(), "").replace(textType.getEndSign(), ""));
	}

	public static String getText(ChatResponse chatResponse) {
		Generation result = chatResponse.getResult();
		if (result == null) {
			return "";
		}
		AssistantMessage output = result.getOutput();
		if (output == null) {
			return "";
		}
		return output.getText() == null ? "" : output.getText();
	}

}
