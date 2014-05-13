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
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 2:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@DocumentType("author")
public class AuthorControllerDocumentProvider implements DocumentProvider<Author> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		Author validAuthor = new Author("first", "middle", "last", new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> validCreate = new RestServiceResult<>(validAuthor, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().isOk());

		Author invalidAuthor = new Author(null, "middle", null, new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> invalidCreate = new RestServiceResult<>(invalidAuthor, DocumentTestResult.Operation.create,
				MockMvcResultMatchers.status().is4xxClientError());

		Author clonedAuthor = null;
		try {
			clonedAuthor = (Author) validAuthor.clone();
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
