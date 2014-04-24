package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.support.DocumentBuilder;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.web.RestErrorResult;
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
public class AuthorControllerDocumentBuilder implements DocumentBuilder<Author> {

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		Author validAuthor = new Author("first", "middle", "last", new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> validResult = new RestErrorResult<>(validAuthor, false,
				MockMvcResultMatchers.status().isOk());

		Author invalidAuthor = new Author(null, "middle", null, new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> invalidResult = new RestErrorResult<>(invalidAuthor, false,
				MockMvcResultMatchers.status().is4xxClientError());

		return Arrays.asList(validResult, invalidResult);

	}
}
