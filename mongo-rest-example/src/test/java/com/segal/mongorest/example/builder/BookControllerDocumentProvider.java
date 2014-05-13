package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.web.RestServiceResult;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@DocumentType("book")
public class BookControllerDocumentProvider implements DocumentProvider<Book> {

	@Override
	public Collection<DocumentTestResult<Book>> createDocuments() {
		Book validBook = new Book("title", "isbn", "publisher", new Date());
		DocumentTestResult<Book> validResult = new RestServiceResult<>(validBook, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().isOk());

		Book invalidBook = new Book(null, "isbn", "publisher", null);
		DocumentTestResult<Book> invalidResult = new RestServiceResult<>(invalidBook, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().is4xxClientError());

		return Arrays.asList(validResult, invalidResult);

	}
}
