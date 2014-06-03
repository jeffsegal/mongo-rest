package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.web.RestServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@DocumentType("author")
public class AuthorControllerDocumentProvider extends ExampleDocumentProviderSupport implements DocumentProvider<Author> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		DocumentTestResult<Author> validCreate = new RestServiceResult<>(kurtVonnegut, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().isOk());
		DocumentTestResult<Author> invalidCreate = new RestServiceResult<>(invalidAuthor, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().is4xxClientError());

		Author clonedAuthor = null;
		try {
			clonedAuthor = (Author) kurtVonnegut.clone();
			clonedAuthor.setId("1234");
		} catch (CloneNotSupportedException e) {
			log.warn("Error while cloning author.", e);
		}
		DocumentTestResult<Author> validFind = new RestServiceResult<>(clonedAuthor, DocumentTestResult.Operation.find,
				MockMvcResultMatchers.status().isOk());

		DocumentTestResult<Author> invalidFind = new RestServiceResult<>(new Author(), DocumentTestResult.Operation.find,
				MockMvcResultMatchers.content().string(""));

		return Arrays.asList(validCreate, invalidCreate, validFind, invalidFind);

	}
}
