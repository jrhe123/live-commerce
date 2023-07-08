CREATE TABLE `t_user_phone` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT `primary key`,
  `user_id` bigint NOT NULL DEFAULT '-1' COMMENT `user id`,
  `phone` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT `phone number`,
  `status` tinyint NOT NULL DEFAULT '-1' COMMENT `0: inactive, 1: active `,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT `create time`,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT `update time`,
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_phone` (`phone`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;


-- procedure to create 100 tables

DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE live_user.`create_t_user_phone_100`()
BEGIN
  DECLARE i INT;
  DECLARE table_name VARCHAR(30);
  DECLARE table_pre VARCHAR(30);
  DECLARE sql_text VARCHAR(3000);
  DECLARE table_body VARCHAR(2000);
  SET i=0;
  SET table_name='';
  SET sql_text='';
  SET table_body=' (
  id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT \'primary key\',
  user_id bigint NOT NULL DEFAULT -1 COMMENT \'user id\',
  phone varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT \'phone number\',
  status tinyint NOT NULL DEFAULT -1 COMMENT \'0: inactive, 1: active \',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT \'create time\',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT \'update time\',
  PRIMARY KEY (id),
  UNIQUE KEY `udx_phone` (`phone`),
  KEY idx_user_id (user_id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
  ';
      
      WHILE i<100 DO 
        IF i<10 THEN
          SET table_name = CONCAT('t_user_phone_0',i);
        ELSE
          SET table_name = CONCAT('t_user_phone_',i);
        END IF;
        
        SET sql_text=CONCAT('CREATE TABLE ',table_name, table_body);
      
      SELECT sql_text;
      SET @sql_text=sql_text;
      PREPARE stmt FROM @sql_text;
      EXECUTE stmt;
      DEALLOCATE PREPARE stmt;
      set i=i+1;
    END WHILE;

  END;;
DELIMITER ;;

-- execute procedure
CALL `live_user`.`create_t_user_phone_100`();
