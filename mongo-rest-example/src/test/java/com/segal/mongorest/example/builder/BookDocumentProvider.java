package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.InvalidDocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import com.segal.mongorest.example.pojo.Book;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@DocumentType("book")
public class BookDocumentProvider extends ExampleDocumentProviderSupport implements DocumentProvider<Book> {

	@Override
	public Collection<DocumentTestResult<Book>> createDocuments() {
		Book validBook = new Book("title", "isbn", "publisher", new Date());
		DocumentTestResult<Book> validResult = new ValidDocumentTestResult<>(validBook, DocumentTestResult.Operation.create);

		Book invalidBook = new Book(null, null, "stuff", null);
		InvalidDocumentTestResult<Book> invalidResult = new InvalidDocumentTestResult<>(invalidBook, DocumentTestResult.Operation.create,
				ConstraintViolationException.class);

		Book invalidUpdate = new Book("title", "isbn", "publisher", new Date());
		InvalidDocumentTestResult<Book> invalidUpdateResult = new InvalidDocumentTestResult<>(invalidUpdate, DocumentTestResult.Operation.update,
				ConstraintViolationException.class);

		return Arrays.asList(validResult, invalidResult, invalidUpdateResult);
	}

}
