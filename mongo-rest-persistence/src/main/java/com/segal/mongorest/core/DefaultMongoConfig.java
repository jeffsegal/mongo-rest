package com.segal.mongorest.core;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/14/14
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableMongoRepositories
@ComponentScan(
		basePackages = {"com.segal"},
		excludeFilters = {
				@ComponentScan.Filter(pattern = {".*Mock.*"}, type = FilterType.REGEX),
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
public class DefaultMongoConfig extends MongoConfig {

	@Override
	public MongoOptions mongoOptions() {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
				.autoConnectRetry(true)
				.connectionsPerHost(50)
				.connectTimeout(1000 * 10)
				.maxAutoConnectRetryTime(1000 * 60 * 30)
				.socketFactory(mongoSocketFactory)
				.socketKeepAlive(true)
				.socketTimeout(1000 * 60)
				.build();
		return new MongoOptions(mongoClientOptions);
	}
}
