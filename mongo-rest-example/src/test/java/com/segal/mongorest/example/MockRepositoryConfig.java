package com.segal.mongorest.example;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.example.repository.AuthorRepository;
import com.segal.mongorest.example.repository.BookRepository;
import org.easymock.EasyMockSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/24/14
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class MockRepositoryConfig extends EasyMockSupport {

	@Bean
	@DocumentType("author")
	AuthorRepository authorRepository() {
		return createNiceMock(AuthorRepository.class);
	}

	@Bean
	@DocumentType("book")
	BookRepository bookRepository() {
		return createNiceMock(BookRepository.class);
	}

}
