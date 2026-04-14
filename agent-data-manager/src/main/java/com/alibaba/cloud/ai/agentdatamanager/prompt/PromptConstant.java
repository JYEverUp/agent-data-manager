
package com.alibaba.cloud.ai.agentdatamanager.prompt;

import org.springframework.ai.chat.prompt.PromptTemplate;

/**
 * Prompt constant class, dynamically loads prompt files
 *
 */
public class PromptConstant {

	// intent-recognition
	public static PromptTemplate getIntentRecognitionPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("intent-recognition"));
	}

	// evidence-query-rewrite
	public static PromptTemplate getEvidenceQueryRewritePromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("evidence-query-rewrite"));
	}

	// agent-knowledge.txt
	public static PromptTemplate getAgentKnowledgePromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("agent-knowledge"));
	}

	public static PromptTemplate getQueryEnhancementPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("query-enhancement"));
	}

	// feasibility-assessment
	public static PromptTemplate getFeasibilityAssessmentPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("feasibility-assessment"));
	}

	public static PromptTemplate getMixSelectorPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("mix-selector"));
	}

	public static PromptTemplate getSemanticConsistencyPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("semantic-consistency"));
	}

	public static PromptTemplate getNewSqlGeneratorPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("new-sql-generate"));
	}

	public static PromptTemplate getPlannerPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("planner"));
	}

	public static PromptTemplate getReportGeneratorPlainPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("report-generator-plain"));
	}

	public static PromptTemplate getSqlErrorFixerPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("sql-error-fixer"));
	}

	public static PromptTemplate getPythonGeneratorPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("python-generator"));
	}

	public static PromptTemplate getPythonAnalyzePromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("python-analyze"));
	}

	public static PromptTemplate getBusinessKnowledgePromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("business-knowledge"));
	}

	public static PromptTemplate getSemanticModelPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("semantic-model"));
	}

	public static PromptTemplate getJsonFixPromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("json-fix"));
	}

	public static PromptTemplate getDataViewAnalyzePromptTemplate() {
		return new PromptTemplate(PromptLoader.loadPrompt("data-view-analyze"));
	}

}
