CREATE TABLE `cfg_global` (
  `system_name` varchar(32) NOT NULL COMMENT '系统名称',
  `group_name` varchar(32) NOT NULL COMMENT '分组名称',
  `config_key` varchar(32) NOT NULL COMMENT '配置标识',
  `config_value` varchar(1024) NOT NULL COMMENT '配置内容',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_valid` int NOT NULL DEFAULT '1' COMMENT '记录有效状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全局配置表';