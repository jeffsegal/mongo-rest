package com.segal.mongorest.core;

import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListener;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.InvalidDocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import org.easymock.EasyMockSupport;
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
public class DocumentValidationTest<T extends BaseDocument> extends EasyMockSupport {

	Logger log = LoggerFactory.getLogger(this.getClass());
	String mockId = "NOT_NULL";

	protected CrudRepository<T, String> repository;
	protected CrudService<T> service;
	protected DocumentProvider<T> documentProvider;
	protected PersistenceListenerManager<T> persistenceListenerManager;

	public DocumentValidationTest() {
	}

	public DocumentValidationTest(CrudRepository<T, String> repository, CrudService<T> service,
	                              DocumentProvider<T> documentProvider, PersistenceListenerManager<T> persistenceListenerManager) {
		this.repository = repository;
		this.service = service;
		this.documentProvider = documentProvider;
		this.persistenceListenerManager = persistenceListenerManager;
	}

	@Rule
	public ExpectedException rule = ExpectedException.none();

	@Test
	public void testDocuments() {
		for (DocumentTestResult<T> result : documentProvider.createDocuments()) {
			log.info("Validating: " + result);
			if (result instanceof ValidDocumentTestResult) {
				if (DocumentTestResult.Operation.create.equals(result.getOperation()) ||
						DocumentTestResult.Operation.update.equals(result.getOperation())) {
					testValidSave((ValidDocumentTestResult<T>) result);
				}
				else if (DocumentTestResult.Operation.find.equals(result.getOperation())) {
					testValidFind((ValidDocumentTestResult<T>) result);
				}
				else throw new IllegalArgumentException("Unexpected Operation type: " + result.getOperation());
			}
			else if (result instanceof InvalidDocumentTestResult) {
				if (DocumentTestResult.Operation.create.equals(result.getOperation()) ||
						DocumentTestResult.Operation.update.equals(result.getOperation())) {
					try {
						testInvalidSave((InvalidDocumentTestResult<T>) result);
					} catch (Exception e) {
						log.info("Received expected exception: " + e.getMessage());
					}
				}
				else if (DocumentTestResult.Operation.find.equals(result.getOperation())) {
					try {
						testInvalidFind((InvalidDocumentTestResult<T>) result);
					} catch (Exception e) {
						log.info("Received expected exception: " + e.getMessage());
					}
				}
				else throw new IllegalArgumentException("Unexpected Operation of type: " + result.getOperation());
			}
			else throw new IllegalArgumentException("Unexpected DocumentTestResult of type: " + result.getClass());
		}
	}

	public void testValidFind(ValidDocumentTestResult<T> result) {
		resetToNice(repository);
		// This is the real test - want to make sure that save actually gets called for input that should pass validation
		expect(repository.findOne((result.getDocument().getId()))).andReturn(result.getDocument());
		replay(repository);
		T document = service.findOne(result.getDocument().getId());
		verify(repository);
	}

	public void testInvalidFind(InvalidDocumentTestResult<T> result) {
		rule = ExpectedException.none();
		rule.expect(result.getExceptionClass());
		T document = service.findOne(result.getDocument().getId());
	}

	public void testInvalidSave(InvalidDocumentTestResult<T> result) throws Exception {
		rule = ExpectedException.none();
		rule.expect(result.getExceptionClass());
		service.create((T) result.getDocument());
	}

	public void testValidSave(ValidDocumentTestResult<T> result) {
		PersistenceListener<T> mockPersistenceListener = createNiceMock(PersistenceListener.class);
		persistenceListenerManager.addPersistenceListener(mockPersistenceListener);

		if (DocumentTestResult.Operation.update.equals(result.getOperation())) {
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

		if (DocumentTestResult.Operation.update.equals(result.getOperation())) {
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

	public void setDocumentProvider(DocumentProvider<T> documentProvider) {
		this.documentProvider = documentProvider;
	}

	public void setPersistenceListenerManager(PersistenceListenerManager<T> persistenceListenerManager) {
		this.persistenceListenerManager = persistenceListenerManager;
	}

}
