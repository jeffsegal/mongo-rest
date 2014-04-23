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
import org.junit.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
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
 * Date: 4/22/14
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExampleMockMongoConfig.class)
@DirtiesContext
public class ExampleValidationTest {

	Logger log = LoggerFactory.getLogger(this.getClass());

	DocumentValidationTest<Author> authorValidationTest;
	DocumentValidationTest<Book> bookValidationTest;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	@Qualifier("authorService")
	CrudService<Author> authorCrudService;

	@Autowired
	@Qualifier("bookService")
	CrudService<Book> bookCrudService;

	@Autowired
	AuthorDocumentBuilder authorDocumentBuilder;

	@Autowired
	BookDocumentBuilder bookDocumentBuilder;

	@Autowired
	PersistenceListenerManager<Author> authorPersistenceListenerManager;

	@Autowired
	PersistenceListenerManager<Book> bookPersistenceListenerManager;

	@Test
	public void test() {
		authorValidationTest = new DocumentValidationTest<>();
		authorValidationTest.setDocumentBuilder(authorDocumentBuilder);
		authorValidationTest.setPersistenceListenerManager(authorPersistenceListenerManager);
		authorValidationTest.setRepository(authorRepository);
		authorValidationTest.setService(authorCrudService);
		authorValidationTest.testDocuments();
	}

}
