package com.segal.mongorest.web.rest;

import com.google.common.collect.Lists;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/11/14
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentController<T extends BaseDocument> {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected CrudService<T> service;

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "text/plain")
	public String test(@AuthenticationPrincipal Principal user) {
		log.debug("Received /test request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		return "TEST";
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<T> get(@AuthenticationPrincipal Principal user) {
		log.debug("Received document list request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		return Lists.newArrayList(service.findAll());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public T get(@PathVariable String id, @AuthenticationPrincipal Principal user) {
		log.debug("Received document get request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		return service.findOne(id);
	}

	@RequestMapping(value = "/**", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public T create(@RequestBody T document, @AuthenticationPrincipal Principal user) {
		log.debug("Received document create request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		return service.create(document);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public T update(@PathVariable String id, @RequestBody T document, @AuthenticationPrincipal Principal user) {
		log.debug("Received document update request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		return service.update(document);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable String id, @AuthenticationPrincipal Principal user) {
		log.debug("Received document delete request from user '" + (user == null ? "unknown user" : user.getName()) + "'");
		service.delete(id);
	}

	public void setService(CrudService<T> service) {
		this.service = service;
	}

}
