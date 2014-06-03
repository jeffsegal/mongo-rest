package com.segal.mongorest.example;

import com.mongodb.MongoOptions;
import com.segal.mongorest.core.MongoConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@Import(ExampleMockRepositoryConfig.class)
@PropertySource("classpath:mongorest.properties")
public class ExampleMockMongoConfig extends MongoConfig {

	@Bean
	@Qualifier("classpathToScan")
	String packages() {
		return "com.segal.mongorest";
	}

	@Bean
	@Override
	public MongoOptions mongoOptions() {
		return getDefaultMongoOptions();
	}

}
