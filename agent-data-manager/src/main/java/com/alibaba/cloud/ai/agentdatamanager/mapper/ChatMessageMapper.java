
package com.alibaba.cloud.ai.agentdatamanager.mapper;

import com.alibaba.cloud.ai.agentdatamanager.entity.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

	/**
	 * Query message list by session ID
	 */
	@Select("""
			SELECT * FROM chat_message
			WHERE session_id = #{sessionId}
			ORDER BY create_time ASC
			""")
	List<ChatMessage> selectBySessionId(@Param("sessionId") String sessionId);

	/**
	 * Query by id
	 */
	@Select("""
			SELECT * FROM chat_message
			WHERE id = #{id}
			""")
	ChatMessage selectById(@Param("id") Long id);

	/**
	 * Query message count by session ID
	 */
	@Select("""
			SELECT COUNT(*) FROM chat_message
			WHERE session_id = #{sessionId}
			""")
	int countBySessionId(@Param("sessionId") String sessionId);

	/**
	 * Query message list by session ID and role
	 */
	@Select("""
			SELECT * FROM chat_message
			WHERE session_id = #{sessionId}
			AND role = #{role}
			ORDER BY create_time ASC
			""")
	List<ChatMessage> selectBySessionIdAndRole(@Param("sessionId") String sessionId, @Param("role") String role);

	@Insert("""
			INSERT INTO chat_message (session_id, role, content, message_type, metadata, create_time)
			VALUES (#{sessionId}, #{role}, #{content}, #{messageType}, #{metadata}, NOW())
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int insert(ChatMessage message);

	@Delete("""
			DELETE FROM chat_message
			WHERE id = #{id}
			""")
	int deleteById(@Param("id") Long id);

}
