package com.segal.mongorest.example;

import com.segal.mongorest.core.DocumentValidationTest;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import com.segal.mongorest.example.builder.AuthorDocumentBuilder;
import com.segal.mongorest.example.builder.BookDocumentBuilder;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.example.repository.AuthorRepository;
import com.segal.mongorest.example.repository.BookRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExampleMockMongoConfig.class)
@DirtiesContext
public class BookValidationTest extends DocumentValidationTest<Book> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BookRepository bookRepository;

	@Autowired
	@Qualifier("bookService")
	CrudService<Book> bookCrudService;

	@Autowired
	BookDocumentBuilder bookDocumentBuilder;

	@Autowired
	PersistenceListenerManager<Book> bookPersistenceListenerManager;

	@Before
	public void init() {
		log.info("Initializing " + this.getClass());
		this.setDocumentBuilder(bookDocumentBuilder);
		this.setPersistenceListenerManager(bookPersistenceListenerManager);
		this.setRepository(bookRepository);
		this.setService(bookCrudService);
		this.testDocuments();
	}

}