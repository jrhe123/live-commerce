# live-commerce

Live commerce platform

## MySQL

- Sharding

  - testing users for this project is 100 Million
  - each row: 8+35+255+20+1+8+4+4+8+8=351b
  - (351+100) \* 100million = 45gb
  - indexing: 45gb \* 10 = 450gb
  - multiple tables: 450gb \* 5 = 2250gb

- Hardware for 10,000 concurrency

  - processor: X86 or ARM
  - storage: SSD 2000GB
  - CPU: 32
  - RAM: 96GB
  - Connection: 12000
  - Cost: $24104.6 per year

- Local DB
  - 100 users tables: 1 Million rows per table

```
CREATE DATABASE live_user CHARACTER  set utf8mb3 COLLATE=utf8_bin;
```

```
DELIMITER $$

CREATE
  PROCEDURE live_user.create_t_user_100()
  BEGIN

      DECLARE i INT;
      DECLARE table_name VARCHAR(30);
      DECLARE table_pre VARCHAR(30);
      DECLARE sql_text VARCHAR(3000);
      DECLARE table_body VARCHAR(2000);
      SET i=0;
      SET table_name='';

      SET sql_text='';
      SET table_body = '(
          user_id bigint NOT NULL DEFAULT -1,
          nick_name varchar(35)  DEFAULT NULL,
          avatar varchar(255)  DEFAULT NULL,
          true_name varchar(20)  DEFAULT NULL,
          sex tinyint(1) DEFAULT NULL COMMENT \'0-male，1-female\',
          born_date datetime DEFAULT NULL,
          work_city int(9) DEFAULT NULL,
          born_city int(9) DEFAULT NULL,
          create_time datetime DEFAULT CURRENT_TIMESTAMP,
          update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (user_id)
      ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;';

      WHILE i<100 DO
        IF i<10 THEN
            SET table_name = CONCAT('t_user_0',i);
        ELSE
            SET table_name = CONCAT('t_user_',i);
        END IF;

        SET sql_text=CONCAT('CREATE TABLE ',table_name, table_body);

        SELECT sql_text;
        SET @sql_text=sql_text;
        PREPARE stmt FROM @sql_text;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
        SET i=i+1;
      END WHILE;

  END$$
DELIMITER ;
```

```
CALL `live_user`.`create_t_user_100`();
```
