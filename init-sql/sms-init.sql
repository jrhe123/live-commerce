CREATE DATABASE live_msg CHARACTER  set utf8mb3 COLLATE=utf8_bin;

CREATE TABLE live_msg.`t_sms` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT "primary key",
  `code` int unsigned DEFAULT '0' COMMENT 'verify code',
  `phone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT "phone number",
  `sendTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'send time',
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
