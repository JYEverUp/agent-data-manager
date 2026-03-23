package com.alibaba.cloud.ai.agentdatamanager.mapper;

import com.alibaba.cloud.ai.agentdatamanager.entity.ModelConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ModelConfigMapper {

    @Select("""
            SELECT id, provider, base_url, api_key, model_name, temperature, is_active, max_tokens,
                   model_type, completions_path, embeddings_path, created_time, updated_time, is_deleted,
                   proxy_enabled, proxy_host, proxy_port, proxy_username, proxy_password
            FROM model_config WHERE is_deleted = 0 ORDER BY created_time DESC
            """)
    List<ModelConfig> findAll();

    @Select("""
            SELECT id, provider, base_url, api_key, model_name, temperature, is_active, max_tokens,
                   model_type, completions_path, embeddings_path, created_time, updated_time, is_deleted,
                   proxy_enabled, proxy_host, proxy_port, proxy_username, proxy_password
            FROM model_config WHERE id = #{id} AND is_deleted = 0
            """)
    ModelConfig findById(Integer id);

    @Select("""
            SELECT id, provider, base_url, api_key, model_name, temperature, is_active, max_tokens,
                   model_type, completions_path, embeddings_path, created_time, updated_time, is_deleted,
                   proxy_enabled, proxy_host, proxy_port, proxy_username, proxy_password
            FROM model_config WHERE model_type = #{modelType} AND is_active = 1 AND is_deleted = 0 LIMIT 1
            """)
    ModelConfig selectActiveByType(@Param("modelType") String modelType);

    @Select("""
            SELECT id, provider, base_url, api_key, model_name, temperature, is_active, max_tokens,
                   model_type, completions_path, embeddings_path, created_time, updated_time, is_deleted,
                   proxy_enabled, proxy_host, proxy_port, proxy_username, proxy_password
            FROM model_config
            WHERE provider = #{provider} AND model_name = #{modelName} AND model_type = #{modelType} AND is_deleted = 0
            LIMIT 1
            """)
    ModelConfig findDuplicate(@Param("provider") String provider, @Param("modelName") String modelName,
            @Param("modelType") String modelType);

    @Update("UPDATE model_config SET is_active = 0 WHERE model_type = #{modelType} AND id != #{currentId} AND is_deleted = 0")
    void deactivateOthers(@Param("modelType") String modelType, @Param("currentId") Integer currentId);

    @Insert("""
            INSERT INTO model_config (provider, base_url, api_key, model_name, temperature, is_active, max_tokens,
                                     model_type, completions_path, embeddings_path, created_time, updated_time, is_deleted,
                                     proxy_enabled, proxy_host, proxy_port, proxy_username, proxy_password)
            VALUES (#{provider}, #{baseUrl}, #{apiKey}, #{modelName}, #{temperature}, #{isActive}, #{maxTokens},
                    #{modelType}, #{completionsPath}, #{embeddingsPath}, NOW(), NOW(), 0,
                    #{proxyEnabled}, #{proxyHost}, #{proxyPort}, #{proxyUsername}, #{proxyPassword})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(ModelConfig modelConfig);

    @Update("""
            <script>
                UPDATE model_config
                <trim prefix="SET" suffixOverrides=",">
                    <if test='provider != null'>provider = #{provider},</if>
                    <if test='baseUrl != null'>base_url = #{baseUrl},</if>
                    <if test='apiKey != null'>api_key = #{apiKey},</if>
                    <if test='modelName != null'>model_name = #{modelName},</if>
                    <if test='temperature != null'>temperature = #{temperature},</if>
                    <if test='isActive != null'>is_active = #{isActive},</if>
                    <if test='maxTokens != null'>max_tokens = #{maxTokens},</if>
                    <if test='modelType != null'>model_type = #{modelType},</if>
                    <if test='completionsPath != null'>completions_path = #{completionsPath},</if>
                    <if test='embeddingsPath != null'>embeddings_path = #{embeddingsPath},</if>
                    <if test='isDeleted != null'>is_deleted = #{isDeleted},</if>
                    <if test='proxyEnabled != null'>proxy_enabled = #{proxyEnabled},</if>
                    <if test='proxyHost != null'>proxy_host = #{proxyHost},</if>
                    <if test='proxyPort != null'>proxy_port = #{proxyPort},</if>
                    <if test='proxyUsername != null'>proxy_username = #{proxyUsername},</if>
                    <if test='proxyPassword != null'>proxy_password = #{proxyPassword},</if>
                    updated_time = NOW()
                </trim>
                WHERE id = #{id}
            </script>
            """)
    int updateById(ModelConfig modelConfig);

    @Update("UPDATE model_config SET is_deleted = 1, updated_time = NOW() WHERE id = #{id}")
    int deleteById(Integer id);

}
