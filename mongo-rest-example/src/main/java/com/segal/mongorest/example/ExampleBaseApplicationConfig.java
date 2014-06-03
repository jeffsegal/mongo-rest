package com.segal.mongorest.example;

import com.segal.mongorest.core.BaseApplicationConfig;
import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.service.DefaultMongoCrudService;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.example.repository.AuthorRepository;
import com.segal.mongorest.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/17/14
 * Time: 6:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ExampleBaseApplicationConfig extends BaseApplicationConfig {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	BookRepository bookRepository;

	@Override
	protected List<String> getPackagesList() {
		return Arrays.asList("com.segal.mongorest");
	}

	@Bean
	@Qualifier("authorService")
	@DocumentType("author")
	public DefaultMongoCrudService<Author> authorService() {
		return getMongoService(authorRepository);
	}

	@Bean
	@Qualifier("bookService")
	@DocumentType("book")
	protected DefaultMongoCrudService<Book> bookService() {
		return getMongoService(bookRepository);
	}

}
