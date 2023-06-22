package org.example.live.framework.datasource.starter.config;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingJdbcDatasourceAutoInitConnectionConfig {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(ShardingJdbcDatasourceAutoInitConnectionConfig.class);
	
	@Bean
	public ApplicationRunner runner(DataSource dataSource) {
		return args -> {
			LOGGER.info("dataSource: {}", dataSource);
			Connection connection = dataSource.getConnection();
		};
	}
}
