package com.segal.mongorest.example.persistence;

import com.segal.mongorest.core.DocumentValidationTest;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.example.ExampleMockMongoConfig;
import com.segal.mongorest.example.pojo.Book;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
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

	@Override
	@Autowired
	@Qualifier("bookRepository")
	public void setRepository(CrudRepository repository) {
		super.setRepository(repository);
	}

	@Override
	@Autowired
	@Qualifier("bookService")
	public void setService(CrudService<Book> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("bookDocumentProvider")
	public void setDocumentProvider(DocumentProvider<Book> documentProvider) {
		super.setDocumentProvider(documentProvider);
	}

	@Override
	@Autowired
	@Qualifier("bookPersistenceListenerManager")
	public void setPersistenceListenerManager(PersistenceListenerManager<Book> persistenceListenerManager) {
		super.setPersistenceListenerManager(persistenceListenerManager);
	}

}
