package com.segal.mongorest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.segal.mongorest.core.support.DocumentBuilder;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.util.ApplicationRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/16/14
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentControllerTest<T extends BaseDocument> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationRegistry applicationRegistry;

	DocumentBuilder<T> documentBuilder;
	WebApplicationContext wac;
	MockMvc mockMvc;
	Principal principal;
	String baseUrl;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test() throws Exception {
		MvcResult result = this.mockMvc.perform(get(baseUrl + "/test")
				.principal(principal))
				.andExpect(status().isOk())
				.andReturn();
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveDocuments() throws Exception {
		Collection<DocumentTestResult<T>> results = documentBuilder.createDocuments();
		for (DocumentTestResult result : results) {
			if (result instanceof RestErrorResult) {
				RestErrorResult restErrorResult = (RestErrorResult) result;
				String documentType = applicationRegistry.getDocumentType(restErrorResult.getClass());
				// Assume that documentType == base URL for controller
				if (restErrorResult.isUpdate()) {
					doUpdate(documentType, (T) restErrorResult.getDocument(), restErrorResult.getExpectation());
				}
				else {
					doCreate(documentType, (T) restErrorResult.getDocument(), restErrorResult.getExpectation());
				}
			}
		}
	}

	private void doCreate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		doSave(post(url), document, resultMatcher);
	}

	private void doUpdate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		doSave(put(url), document, resultMatcher);
	}

	private void doSave(MockHttpServletRequestBuilder builder, T document, ResultMatcher resultMatcher) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(document);
		MvcResult result = this.mockMvc.perform(builder
				.principal(principal)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(resultMatcher)
				.andReturn();
		log.debug("Response: " + result.getResponse().getContentAsString());
	}

//	private void doGet(String url) throws Exception {
//		this.mockMvc.perform(get(url)
//				.principal(principal)
//				.accept(MediaType.APPLICATION_JSON))
//		            .andExpect(status().isOk())
//		            .andExpect(content().contentType("application/json"))
//		            .andExpect(jsonPath("$.name").value("Lee"));
//
//		MvcResult result = this.mockMvc.perform(get(url)
//				.principal(principal)
//				.accept(MediaType.APPLICATION_JSON)
//				.andExpect(resultMatcher)
//				.andReturn());
//		log.debug("Response: " + result.getResponse().getContentAsString());
//	}

	public void setDocumentBuilder(DocumentBuilder<T> documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

}
