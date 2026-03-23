CREATE TABLE IF NOT EXISTS agent (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL COMMENT '智能体名称',
    description TEXT COMMENT '智能体描述',
    avatar TEXT COMMENT '头像URL',
    status VARCHAR(50) DEFAULT 'draft' COMMENT '状态：draft-待发布，published-已发布，offline-已下线',
    api_key VARCHAR(255) DEFAULT NULL COMMENT '访问 API Key，格式 sk-xxx',
    api_key_enabled TINYINT DEFAULT 0 COMMENT 'API Key 是否启用：0-禁用，1-启用',
    prompt TEXT COMMENT '自定义Prompt配置',
    category VARCHAR(100) COMMENT '分类',
    admin_id BIGINT COMMENT '管理员ID',
    tags TEXT COMMENT '标签，逗号分隔',
    human_review_enabled TINYINT DEFAULT 0 COMMENT '是否开启人工审核：0-关闭，1-开启',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_agent_name (name),
    INDEX idx_agent_status (status),
    INDEX idx_agent_category (category),
    INDEX idx_agent_admin_id (admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS datasource (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL COMMENT '数据源名称',
    type VARCHAR(50) NOT NULL COMMENT '数据源类型：mysql, postgresql',
    host VARCHAR(255) NOT NULL COMMENT '主机地址',
    port INT NOT NULL COMMENT '端口号',
    database_name VARCHAR(255) NOT NULL COMMENT '数据库名称',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    connection_url VARCHAR(1000) COMMENT '完整连接URL',
    status VARCHAR(50) DEFAULT 'inactive' COMMENT '状态：active-启用，inactive-禁用',
    test_status VARCHAR(50) DEFAULT 'unknown' COMMENT '连接测试状态：success-成功，failed-失败，unknown-未知',
    description TEXT COMMENT '描述',
    creator_id BIGINT COMMENT '创建者ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_datasource_name (name),
    INDEX idx_datasource_type (type),
    INDEX idx_datasource_status (status),
    INDEX idx_datasource_creator_id (creator_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS agent_datasource (
    id INT NOT NULL AUTO_INCREMENT,
    agent_id BIGINT NOT NULL COMMENT '智能体ID',
    datasource_id INT NOT NULL COMMENT '数据源ID',
    is_active TINYINT DEFAULT 0 COMMENT '是否启用：0-禁用，1-启用',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_agent_datasource (agent_id, datasource_id),
    INDEX idx_agent_datasource_agent_id (agent_id),
    INDEX idx_agent_datasource_datasource_id (datasource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS agent_datasource_tables (
    id INT NOT NULL AUTO_INCREMENT,
    agent_datasource_id INT NOT NULL COMMENT 'agent_datasource主键',
    table_name VARCHAR(255) NOT NULL COMMENT '选中的表名',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_agent_datasource_table (agent_datasource_id, table_name),
    INDEX idx_agent_datasource_tables_rel (agent_datasource_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `model_config` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `provider` varchar(255) NOT NULL COMMENT '厂商标识',
    `base_url` varchar(255) NOT NULL COMMENT '模型服务基础地址',
    `api_key` varchar(255) NOT NULL COMMENT 'API密钥',
    `model_name` varchar(255) NOT NULL COMMENT '模型名称',
    `temperature` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '温度参数',
    `is_active` tinyint(1) DEFAULT '0' COMMENT '是否启用',
    `max_tokens` int(11) DEFAULT '2000' COMMENT '输出最大token数',
    `model_type` varchar(20) NOT NULL DEFAULT 'CHAT' COMMENT '模型类型 (CHAT/EMBEDDING)',
    `completions_path` varchar(255) DEFAULT NULL COMMENT '对话模型请求路径',
    `embeddings_path` varchar(255) DEFAULT NULL COMMENT '向量模型请求路径',
    `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` int(11) DEFAULT '0' COMMENT '0=未删除, 1=已删除',
    `proxy_enabled` tinyint(1) DEFAULT '0' COMMENT '是否启用代理：0-禁用，1-启用',
    `proxy_host` varchar(255) DEFAULT NULL COMMENT '代理主机地址',
    `proxy_port` int(11) DEFAULT NULL COMMENT '代理端口',
    `proxy_username` varchar(255) DEFAULT NULL COMMENT '代理用户名（可选）',
    `proxy_password` varchar(255) DEFAULT NULL COMMENT '代理密码（可选）',
    PRIMARY KEY (`id`),
    KEY `idx_model_config_type_active` (`model_type`, `is_active`, `is_deleted`),
    KEY `idx_model_config_provider_name` (`provider`, `model_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
