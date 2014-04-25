package com.segal.mongorest.example.controller;

import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.example.pojo.Book;
import com.segal.mongorest.web.rest.DocumentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/book")
public class BookController extends DocumentController<Book> {

	@Override
	@Autowired
	@Qualifier("bookService")
	public void setService(CrudService<Book> service) {
		super.setService(service);
	}

}