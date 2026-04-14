
package com.alibaba.cloud.ai.agentdatamanager.util;

public class MarkdownParserUtil {

	public static String extractText(String markdownCode) {
		String code = extractRawText(markdownCode);
		// Correctly handle various newline character types: \r\n, \n, \r, but maintain
		// compatibility with NewLineParser.format()
		return code.replaceAll("\r\n", " ").replaceAll("\n", " ").replaceAll("\r", " ");
	}

	public static String extractRawText(String markdownCode) {
		// Find the start of a code block (3 or more backticks)
		int startIndex = -1;
		int delimiterLength = 0;

		for (int i = 0; i <= markdownCode.length() - 3; i++) {
			if (markdownCode.substring(i, i + 3).equals("```")) {
				startIndex = i;
				delimiterLength = 3;
				// Count additional backticks
				while (i + delimiterLength < markdownCode.length() && markdownCode.charAt(i + delimiterLength) == '`') {
					delimiterLength++;
				}
				break;
			}
		}

		if (startIndex == -1) {
			return markdownCode; // No code block found
		}

		// Skip the opening delimiter and optional language specification
		int contentStart = startIndex + delimiterLength;
		while (contentStart < markdownCode.length() && markdownCode.charAt(contentStart) != '\n') {
			contentStart++;
		}
		if (contentStart < markdownCode.length() && markdownCode.charAt(contentStart) == '\n') {
			contentStart++; // Skip the newline after language spec
		}

		// Find the closing delimiter
		String closingDelimiter = "`".repeat(delimiterLength);
		int endIndex = markdownCode.indexOf(closingDelimiter, contentStart);

		if (endIndex == -1) {
			// No closing delimiter found, return from content start to end
			return markdownCode.substring(contentStart);
		}

		// Extract just the content between delimiters
		return markdownCode.substring(contentStart, endIndex);
	}

}
