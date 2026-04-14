
package com.alibaba.cloud.ai.agentdatamanager.mapper;

import com.alibaba.cloud.ai.agentdatamanager.entity.AgentPresetQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgentPresetQuestionMapper {

	@Select("""
			SELECT * FROM agent_preset_question
			         WHERE agent_id = #{agentId} AND is_active = 1
			ORDER BY sort_order ASC, id ASC
			""")
	List<AgentPresetQuestion> selectByAgentId(@Param("agentId") Long agentId);

	@Select("""
			SELECT * FROM agent_preset_question
			         WHERE agent_id = #{agentId}
			ORDER BY sort_order ASC, id ASC
			""")
	List<AgentPresetQuestion> selectAllByAgentId(@Param("agentId") Long agentId);

	/**
	 * Query by id
	 */
	@Select("""
			SELECT * FROM agent_preset_question WHERE id = #{id}
			""")
	AgentPresetQuestion selectById(@Param("id") Long id);

	@Insert("""
			INSERT INTO agent_preset_question (agent_id, question, sort_order, is_active, create_time, update_time)
			VALUES (#{agentId}, #{question}, #{sortOrder}, #{isActive}, NOW(), NOW())
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int insert(AgentPresetQuestion question);

	@Update("""
			<script>
			UPDATE agent_preset_question
			<set>
				<if test="question != null">question = #{question},</if>
				<if test="sortOrder != null">sort_order = #{sortOrder},</if>
				<if test="isActive != null">is_active = #{isActive},</if>
				update_time = NOW()
			</set>
			WHERE id = #{id}
			</script>
			""")
	int update(AgentPresetQuestion question);

	@Delete("""
			DELETE FROM agent_preset_question WHERE id = #{id}
			""")
	int deleteById(@Param("id") Long id);

	@Delete("""
			DELETE FROM agent_preset_question WHERE agent_id = #{agentId}
			""")
	int deleteByAgentId(@Param("agentId") Long agentId);

}
