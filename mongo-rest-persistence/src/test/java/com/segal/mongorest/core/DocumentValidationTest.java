package com.segal.mongorest.core;

import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListener;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.InvalidDocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import com.segal.mongorest.core.support.DocumentBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import static org.easymock.EasyMock.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/17/14
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentValidationTest<T extends BaseDocument> {

	Logger log = LoggerFactory.getLogger(this.getClass());
	String mockId = "NOT_NULL";

	protected CrudRepository<T, String> repository;
	protected CrudService<T> service;
	protected DocumentBuilder<T> documentBuilder;
	protected PersistenceListenerManager<T> persistenceListenerManager;

	public DocumentValidationTest() {
	}

	public DocumentValidationTest(CrudRepository<T, String> repository, CrudService<T> service,
	                              DocumentBuilder<T> documentBuilder, PersistenceListenerManager<T> persistenceListenerManager) {
		this.repository = repository;
		this.service = service;
		this.documentBuilder = documentBuilder;
		this.persistenceListenerManager = persistenceListenerManager;
	}

	@Rule
	public ExpectedException rule = ExpectedException.none();

	@Test
	public void testDocuments() {
		for (DocumentTestResult<T> result : documentBuilder.createDocuments()) {
			if (result instanceof ValidDocumentTestResult) {
				testValidSave((ValidDocumentTestResult<T>) result);
			}
			else if (result instanceof InvalidDocumentTestResult) {
				try {
					testInvalidSave((InvalidDocumentTestResult<T>) result);
				} catch (Exception e) {
					log.info("Received expected exception: " + e.getMessage());
				}
			}
			else throw new IllegalArgumentException("Unexpected DocumentTestResult of type: " + result.getClass());
		}
	}

	public void testInvalidSave(InvalidDocumentTestResult<T> result) throws Exception {
		rule = ExpectedException.none();
		rule.expect(result.getExceptionClass());
		service.create((T) result.getDocument());
	}

	public void testValidSave(ValidDocumentTestResult<T> result) {
		PersistenceListener<T> mockPersistenceListener = createNiceMock(PersistenceListener.class);
		persistenceListenerManager.addPersistenceListener(mockPersistenceListener);

		if (result.isUpdate()) {
			result.getDocument().setId(mockId);
			mockPersistenceListener.documentUpdated(result.getDocument());
		}
		else {
			mockPersistenceListener.documentAdded(result.getDocument());
		}
		resetToNice(repository);
		// This is the real test - want to make sure that save actually gets called for input that should pass validation
		expect(repository.save((result.getDocument()))).andReturn(result.getDocument());
		replay(repository, mockPersistenceListener);

		if (result.isUpdate()) {
			service.update(result.getDocument());
		}
		else {
			service.create(result.getDocument());
		}
		verify(mockPersistenceListener);

		persistenceListenerManager.removePersistenceListener(mockPersistenceListener);
	}

	@Test
	public void testDelete() {
		log.info("Attempting to delete ID '" + mockId + "'...");
		doTestDelete(mockId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDelete() {
		log.info("Attempting to delete a null ID...");
		doTestDelete(null);
	}

	private void doTestDelete(String id) {
		service.delete(id);
		PersistenceListener<T> mockPersistenceListener = createNiceMock(PersistenceListener.class);
		persistenceListenerManager.addPersistenceListener(mockPersistenceListener);
		mockPersistenceListener.documentDeleted(id);
		replay(mockPersistenceListener);
		service.delete(id);
		verify(mockPersistenceListener);
		persistenceListenerManager.removePersistenceListener(mockPersistenceListener);
	}

	public void setRepository(CrudRepository repository) {
		this.repository = repository;
	}

	public void setService(CrudService<T> service) {
		this.service = service;
	}

	public void setDocumentBuilder(DocumentBuilder<T> documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

	public void setPersistenceListenerManager(PersistenceListenerManager<T> persistenceListenerManager) {
		this.persistenceListenerManager = persistenceListenerManager;
	}

}
