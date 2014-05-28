package com.segal.mongorest.example;

import com.mongodb.MongoOptions;
import com.segal.mongorest.core.MongoConfig;
import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.service.DefaultMongoCrudService;
import com.segal.mongorest.core.util.TimeProvider;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.example.repository.AuthorRepository;
import com.segal.mongorest.example.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.repository.CrudRepository;

import static org.easymock.EasyMock.createNiceMock;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.segal"},
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import(MockRepositoryConfig.class)
@PropertySource("classpath:mongorest.properties")
public class ExampleMockMongoConfig extends MongoConfig {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Bean
	@Qualifier("classpathToScan")
	String packages() {
		return "com.segal.mongorest";
	}

	@Bean
	@Qualifier("authorService")
	@DocumentType("author")
	public DefaultMongoCrudService<Author> authorService() {
		return getMongoService(authorRepository);
	}

	@Bean
	@Qualifier("bookService")
	@DocumentType("book")
	public DefaultMongoCrudService<Book> bookService() {
		return getMongoService(bookRepository);
	}

	private DefaultMongoCrudService getMongoService(CrudRepository crudRepository) {
		return new DefaultMongoCrudService(crudRepository, createNiceMock(TimeProvider.class));
	}

	@Bean
	@Override
	public MongoOptions mongoOptions() {
		return getDefaultMongoOptions();
	}

}
