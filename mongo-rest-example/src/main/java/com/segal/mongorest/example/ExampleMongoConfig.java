package com.segal.mongorest.example;

import com.mongodb.MongoOptions;
import com.segal.mongorest.core.MongoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/17/14
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.segal.mongorest.example.repository"})
@EnableMongoRepositories
public class ExampleMongoConfig extends MongoConfig {

	@Override
	@Bean
	public MongoOptions mongoOptions() {
		return getDefaultMongoOptions();
	}

	@Override
	protected String getDatabaseName() {
		return "mongorest";
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.segal.mongorest.example.repository";
	}

}
