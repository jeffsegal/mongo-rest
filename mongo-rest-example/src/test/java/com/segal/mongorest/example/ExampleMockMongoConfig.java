package com.segal.mongorest.example;

import com.mongodb.MongoOptions;
import com.segal.mongorest.core.DocumentValidationTest;
import com.segal.mongorest.core.MongoConfig;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.DefaultMongoCrudService;
import com.segal.mongorest.core.service.PersistenceListener;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import com.segal.mongorest.example.builder.AuthorDocumentBuilder;
import com.segal.mongorest.example.builder.BookDocumentBuilder;
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

import static org.easymock.EasyMock.createMock;
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
@PropertySource("mongorest.properties")
public class ExampleMockMongoConfig extends MongoConfig implements PersistenceListener {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PersistenceListenerManager<Author> authorPersistenceListenerManager;

	@Autowired
	PersistenceListenerManager<Book> bookPersistenceListenerManager;

	@Bean
	public AuthorDocumentBuilder authorDocumentBuilder() {
		return new AuthorDocumentBuilder();
	}

	@Bean
	public BookDocumentBuilder bookDocumentBuilder() {
		return new BookDocumentBuilder();
	}

	@Bean
	public PersistenceListenerManager<Author> authorPersistenceListenerManager() {
		PersistenceListenerManager manager = new PersistenceListenerManager<>();
		manager.addPersistenceListener(this);
		return manager;
	}

	@Bean
	public PersistenceListenerManager<Book> bookPersistenceListenerManager() {
		PersistenceListenerManager manager = new PersistenceListenerManager<>();
		manager.addPersistenceListener(this);
		return manager;
	}

	@Bean
	@Qualifier("authorService")
	public DefaultMongoCrudService<Author> authorService() {
		return getMongoService(authorRepository, authorPersistenceListenerManager);
	}

	@Bean
	@Qualifier("bookService")
	public DefaultMongoCrudService<Book> bookService() {
		return getMongoService(bookRepository, bookPersistenceListenerManager);
	}

	private DefaultMongoCrudService getMongoService(CrudRepository crudRepository,
	                                                PersistenceListenerManager persistenceListenerManager) {
		return new DefaultMongoCrudService(crudRepository, persistenceListenerManager);
	}

	@Bean
	AuthorRepository authorRepository() {
		return createNiceMock(AuthorRepository.class);
	}

	@Bean
	BookRepository bookRepository() {
		return createNiceMock(BookRepository.class);
	}

	@Bean
	@Override
	public MongoOptions mongoOptions() {
		return getDefaultMongoOptions();
	}

	@Override
	public void documentAdded(BaseDocument pojo) {
		log.info("document added");
	}

	@Override
	public void documentUpdated(BaseDocument pojo) {
		// no-op
	}

	@Override
	public void documentDeleted(String id) {
		// no-op
	}
}
