package com.segal.mongorest.example;

import com.segal.mongorest.core.DocumentValidationTest;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.util.ApplicationRegistry;
import com.segal.mongorest.example.builder.AuthorDocumentProvider;
import com.segal.mongorest.example.builder.BookDocumentProvider;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.example.repository.AuthorRepository;
import com.segal.mongorest.example.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ExampleMockMongoConfig.class)
//@DirtiesContext
@Deprecated
public class ExampleValidationTest {

	Logger log = LoggerFactory.getLogger(this.getClass());

	DocumentValidationTest<Author> authorValidationTest;
	DocumentValidationTest<Book> bookValidationTest;

	@Autowired
	ApplicationRegistry applicationRegistry;

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
	AuthorDocumentProvider authorDocumentBuilder;

	@Autowired
	BookDocumentProvider bookDocumentBuilder;

	//	@Test
	public void test() {
		authorValidationTest = new DocumentValidationTest<>();
		authorValidationTest.setDocumentProvider(authorDocumentBuilder);
		authorValidationTest.setRepository(authorRepository);
		authorValidationTest.setService(authorCrudService);
		authorValidationTest.testDocuments();
	}

}
