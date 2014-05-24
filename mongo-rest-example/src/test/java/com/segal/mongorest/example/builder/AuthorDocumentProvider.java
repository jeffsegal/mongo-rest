package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.InvalidDocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import com.segal.mongorest.example.pojo.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@DocumentType("author")
public class AuthorDocumentProvider implements DocumentProvider<Author> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		Author validAuthor = new Author("first", "middle", "last", new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> validResult = new ValidDocumentTestResult<>(validAuthor, DocumentTestResult.Operation.create);

		Author invalidAuthor = new Author(null, "middle", null, null, new Date());
		InvalidDocumentTestResult<Author> invalidResult = new InvalidDocumentTestResult<>(invalidAuthor,
				DocumentTestResult.Operation.create, ConstraintViolationException.class);

		Author clonedAuthor = null;
		try {
			clonedAuthor = (Author) validAuthor.clone();
			clonedAuthor.setId("1234");
		} catch (CloneNotSupportedException e) {
			log.warn("Error while cloning author.", e);
		}

		DocumentTestResult<Author> anotherValidResult = new ValidDocumentTestResult<>(validAuthor,
				DocumentTestResult.Operation.create);

		ValidDocumentTestResult<Author> validFind = new ValidDocumentTestResult<>(clonedAuthor,
				DocumentTestResult.Operation.find);

		InvalidDocumentTestResult<Author> invalidFind = new InvalidDocumentTestResult<>(new Author(),
				DocumentTestResult.Operation.find, IllegalArgumentException.class);

		return Arrays.asList(validResult, invalidResult, anotherValidResult, validFind, invalidFind);
	}

}
