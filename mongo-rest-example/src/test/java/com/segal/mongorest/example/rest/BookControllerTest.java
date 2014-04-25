package com.segal.mongorest.example.rest;

import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.example.ExampleMockRestConfig;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.web.DocumentControllerTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/24/14
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ExampleMockRestConfig.class)
public class BookControllerTest extends DocumentControllerTest<Book> {

	@PostConstruct
	public void init() {
		setBaseUrl("/" + Book.class.getSimpleName().toLowerCase());
	}

	@Override
	@Autowired
	@Qualifier("bookControllerDocumentProvider")
	public void setDocumentProvider(DocumentProvider<Book> documentProvider) {
		super.setDocumentProvider(documentProvider);
	}

	@Override
	@Autowired
	@Qualifier("bookRepository")
	public void setCrudRepository(CrudRepository<Book, String> crudRepository) {
		super.setCrudRepository(crudRepository);
	}

}
