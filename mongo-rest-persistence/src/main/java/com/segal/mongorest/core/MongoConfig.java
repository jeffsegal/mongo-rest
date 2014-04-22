package com.segal.mongorest.core;

import com.mongodb.Mongo;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import com.segal.mongorest.core.service.DefaultMongoCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.CrudRepository;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class MongoConfig extends AbstractMongoConfiguration {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${com.segal.mongorest.mongodb.port}")
	int mongoPort;

	@Value("${com.segal.mongorest.mongodb.host}")
	String mongoHost;

	@Value("${com.segal.mongorest.mongodb.dbname}")
	String dbname;

	@Value("${com.segal.mongorest.mongodb.username}")
	String username;

	@Value("${com.segal.mongorest.mongodb.password}")
	String password;

	@Value("${com.segal.mongorest.mongodb.useSsl}")
	boolean useSsl = false;

	@Autowired
	SocketFactory mongoSocketFactory;

	@Autowired
	MongoOptions mongoOptions;

	@Autowired
	Mongo mongo;

	@Autowired
	MongoTemplate mongoTemplate;

	private DefaultMongoCrudService getMongoService(MongoRepositoryFactoryBean factoryBean,
	                                            Class<? extends CrudRepository> repositoryInterface) {
		factoryBean.setMongoOperations(mongoTemplate);
		factoryBean.setRepositoryInterface(repositoryInterface);
		factoryBean.afterPropertiesSet();
		return new DefaultMongoCrudService((CrudRepository) factoryBean.getObject());
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongo, dbname, new UserCredentials(username, password));
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

	@Bean
	public abstract MongoOptions mongoOptions();

	@Bean
	public MongoFactoryBean mongoFactoryBean() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost(mongoHost);
		mongo.setPort(mongoPort);
		mongo.setMongoOptions(mongoOptions);
		return mongo;
	}

	@Bean
	public SocketFactory mongoSocketFactory() {
		if (useSsl) return SSLSocketFactory.getDefault();
		else return SocketFactory.getDefault();
	}

	@Override
	protected String getDatabaseName() {
		return dbname;
	}

	@Override
	public Mongo mongo() throws Exception {
		return mongo;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
