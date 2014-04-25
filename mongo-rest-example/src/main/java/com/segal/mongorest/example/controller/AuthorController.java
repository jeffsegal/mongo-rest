package com.segal.mongorest.example.controller;

import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.example.pojo.Author;
import com.segal.mongorest.web.rest.DocumentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/23/14
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/author")
public class AuthorController extends DocumentController<Author> {

	@Override
	@Autowired
	@Qualifier("authorService")
	public void setService(CrudService<Author> service) {
		super.setService(service);
	}

}