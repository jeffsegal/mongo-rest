package com.segal.mongorest.example.runtime;

import com.google.common.collect.Lists;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.example.ExampleApplicationConfig;
import com.segal.mongorest.example.builder.ExampleDocumentProviderSupport;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 6/3/14
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ExampleApplicationConfig.class})
public class DataPopulator extends ExampleDocumentProviderSupport {

	@Autowired
	CrudService<Author> authorService;

	@Autowired
	CrudService<Book> bookService;

	boolean emptyCollections = true;

	@Test
	public void populate() {
		if (emptyCollections) {
			log.info("Deleting all POJOs from MongoDB...");
			authorService.deleteAll();
			bookService.deleteAll();
		}

		createAuthors();
		createBooks();
	}

	public void createAuthors() {
		List<Author> authors = Lists.newArrayList(kurtVonnegut);
		log.info("Creating " + authors.size() + " authors.");
		for (Author author : authors) {
			authorService.create(author);
		}
	}

	public void createBooks() {
		List<Book> books = Lists.newArrayList(catsCradle);
		log.info("Creating " + books.size() + " authors.");
		for (Book book : books) {
			bookService.create(book);
		}
	}

}
