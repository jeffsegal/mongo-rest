package com.segal.mongorest.example.builder;

import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 6/3/14
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExampleDocumentProviderSupport {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public Author kurtVonnegut = new Author("Kurt", null, "Vonnegut", new Date(-1487617200000L),
			new Date(1176264000000L));
	public Author invalidAuthor = new Author(null, "middle", null, null, new Date());

	public Book catsCradle = new Book("Cat's Cradle", "ISBN 0-385-33348-X", "Holt, Rinehart and Winston",
			new Date(-220924800000L));
	public Book invalidBook = new Book(null, "isbn", "publisher", null);

}
