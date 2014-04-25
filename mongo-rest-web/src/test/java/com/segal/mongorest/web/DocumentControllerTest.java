package com.segal.mongorest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.util.ApplicationRegistry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Collection;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	protected ApplicationRegistry applicationRegistry;

	@Autowired
	protected WebApplicationContext wac;

	protected CrudRepository<T, String> crudRepository;
	protected DocumentProvider<T> documentProvider;
	protected MockMvc mockMvc;
	protected Principal principal = new UsernamePasswordAuthenticationToken("joeUser", "secret");
	protected String baseUrl;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test() throws Exception {
		String url = baseUrl + "/test";
		log.info("Executing request against URL '" + url + "'");
		MvcResult result = this.mockMvc.perform(get(url)
				.principal(principal))
				.andExpect(status().isOk())
				.andReturn();
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveDocuments() throws Exception {
		Collection<DocumentTestResult<T>> results = documentProvider.createDocuments();
		for (DocumentTestResult result : results) {
			if (result instanceof RestErrorResult) {
				RestErrorResult restErrorResult = (RestErrorResult) result;
				String documentType = applicationRegistry.getDocumentType(restErrorResult.getDocument().getClass());
				// Assume that /documentType == base URL for controller
				String url = "/" + documentType;
				if (DocumentTestResult.Operation.create.equals(restErrorResult.getOperation())) {
					doCreate(url, (T) restErrorResult.getDocument(), restErrorResult.getExpectation());
				}
				else if (DocumentTestResult.Operation.update.equals(restErrorResult.getOperation())) {
					doUpdate(url + "/" + restErrorResult.getDocument().getId(), (T) restErrorResult.getDocument(),
							restErrorResult.getExpectation());
				}
				else if (DocumentTestResult.Operation.find.equals(restErrorResult.getOperation())) {
					doGet(url + "/" + restErrorResult.getDocument().getId(), restErrorResult.getDocument().getId(),
							(T) restErrorResult.getDocument(), restErrorResult.getExpectation());
				}
			}
		}
	}

	protected void doCreate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		resetToNice(crudRepository);
		expect(crudRepository.save((document))).andReturn(document).anyTimes();
		replay(crudRepository);
		doSave(post(url), document, resultMatcher);
		verify(crudRepository);
	}

	protected void doUpdate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		doSave(put(url), document, resultMatcher);
	}

	protected void doSave(MockHttpServletRequestBuilder builder, T document, ResultMatcher resultMatcher) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(document);
		MvcResult result = this.mockMvc.perform(builder
				.principal(principal)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(resultMatcher)
				.andReturn();
		log.info("Executed request against URL '" + result.getRequest().getPathInfo() + "'");
		log.debug("Response: " + result.getResponse().getContentAsString());
	}

	private void doGet(String url, String id, T document, ResultMatcher resultMatcher) throws Exception {
		resetToNice(crudRepository);
		expect(crudRepository.findOne((id))).andReturn(document).anyTimes();
		replay(crudRepository);

		MvcResult result = this.mockMvc.perform(get(url)
				.principal(principal)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(resultMatcher)
				.andReturn();
		log.debug("Response: " + result.getResponse().getContentAsString());
		verify(crudRepository);
	}

	public void setDocumentProvider(DocumentProvider<T> documentProvider) {
		this.documentProvider = documentProvider;
	}

	public void setCrudRepository(CrudRepository<T, String> crudRepository) {
		this.crudRepository = crudRepository;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}
}
