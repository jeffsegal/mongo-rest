package com.segal.mongorest.example.builder;

import com.segal.mongorest.core.support.DocumentBuilder;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.support.ValidDocumentTestResult;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;

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
public class BookDocumentBuilder implements DocumentBuilder<Book> {

	@Override
	public Collection<DocumentTestResult<Book>> createDocuments() {
		Book book = new Book("title", "isbn", "publisher", new Date());
		DocumentTestResult<Book> result = new ValidDocumentTestResult<>(book, false);
		return Arrays.asList(result);
	}

}
