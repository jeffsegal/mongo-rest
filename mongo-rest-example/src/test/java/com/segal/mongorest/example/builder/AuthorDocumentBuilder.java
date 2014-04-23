package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.support.DocumentBuilder;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import com.segal.mongorest.example.pojo.Author;

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
public class AuthorDocumentBuilder implements DocumentBuilder<Author> {

	@Override
	public Collection<DocumentTestResult<Author>> createDocuments() {
		Author author = new Author("first", "middle", "last", new Date(
				new Date().getTime() - 1000L * 60 * 60 * 24 * 365 * 50), null);
		DocumentTestResult<Author> result = new ValidDocumentTestResult<>(author, false);
		return Arrays.asList(result);
	}

}
