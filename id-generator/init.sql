CREATE DATABASE live_common  CHARACTER  set utf8mb3 COLLATE=utf8_bin;

CREATE TABLE `live_common`.`t_id_generate_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'primary id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'remark',
  `next_threshold` bigint DEFAULT NULL COMMENT 'current id threashold value',
  `init_num` bigint DEFAULT NULL COMMENT 'init value',
  `current_start` bigint DEFAULT NULL COMMENT 'current id starting value',
  `step` int DEFAULT NULL COMMENT 'id increment range',
  `is_seq` tinyint DEFAULT NULL COMMENT 'non-seq: 0, seq: 1',
  `id_prefix` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'business logic prefix if exists, could be null',
  `version` int NOT NULL DEFAULT '0' COMMENT 'optimistic lock version',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `t_id_generate_config` (`id`, `remark`, 
`next_threshold`, `init_num`, `current_start`, `step`, 
`is_seq`, `id_prefix`, `version`, `create_time`,
`update_time`)
VALUES
(1, 'user id generator strategy', 10050, 10000, 10000, 50, 0, 
'user_id', 0, '2023-05-23 12:38:21', '2023-05-23 23:31:45');