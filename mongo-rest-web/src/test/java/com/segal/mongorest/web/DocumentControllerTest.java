package com.segal.mongorest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.support.DocumentProvider;
import com.segal.mongorest.core.support.DocumentTestResult;
import com.segal.mongorest.core.util.ApplicationRegistry;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/16/14
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentControllerTest<T extends BaseDocument> extends EasyMockSupport {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected ApplicationRegistry applicationRegistry;

	@Autowired
	protected WebApplicationContext wac;

	protected CrudService<T> crudService;
	protected DocumentProvider<T> documentProvider;
	protected MockMvc mockMvc;
	protected Principal principal = new UsernamePasswordAuthenticationToken("joeUser", "secret");
	protected String baseUrl;
	protected boolean enforceAssertions = true;

	final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
	};
	final Type type = typeToken.getType();

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		if (baseUrl == null && type instanceof Class) {
			Class clazz = (Class) type;
			baseUrl = "/" + clazz.getSimpleName().toLowerCase();
		}

		// Set up TimeProvider mock
		EasyMock.resetToNice(crudService.getTimeProvider());
		EasyMock.expect(crudService.getTimeProvider().getSystemTimeMillis()).andStubReturn(1000L);
	}

	@Test
	public void test() throws Exception {
		String url = baseUrl + "/test";
		log.info("Executing request against URL '" + url + "'");
		ResultActions resultActions = this.mockMvc.perform(get(url)
				.principal(principal));
		if (enforceAssertions) resultActions = resultActions.andExpect(status().isOk());
		MvcResult result = resultActions.andReturn();
		log.debug(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveDocuments() throws Exception {
		Collection<DocumentTestResult<T>> results = documentProvider.createDocuments();
		for (DocumentTestResult result : results) {
			log.info("Validating: " + result);
			if (result instanceof RestServiceResult) {
				RestServiceResult restServiceResult = (RestServiceResult) result;
				String documentType = applicationRegistry.getDocumentType(restServiceResult.getDocument().getClass());
				// Assume that /documentType == base URL for controller
				String url = "/" + documentType;
				if (DocumentTestResult.Operation.create.equals(restServiceResult.getOperation())) {
					doCreate(url, (T) restServiceResult.getDocument(), restServiceResult.getExpectation());
				} else if (DocumentTestResult.Operation.update.equals(restServiceResult.getOperation())) {
					doUpdate(url + "/" + restServiceResult.getDocument().getId(), (T) restServiceResult.getDocument(),
							restServiceResult.getExpectation());
				} else if (DocumentTestResult.Operation.find.equals(restServiceResult.getOperation())) {
					doGet(url + "/" + restServiceResult.getDocument().getId(), restServiceResult.getDocument().getId(),
							(T) restServiceResult.getDocument(), restServiceResult.getExpectation());
				}
			}
		}
	}

	protected void doCreate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		log.info("Executing create against URL '" + url + "'");
		EasyMock.resetToNice(crudService.getCrudRepository(), crudService.getTimeProvider());
		EasyMock.expect(crudService.getCrudRepository().save((document))).andReturn(document).anyTimes();
		EasyMock.replay(crudService.getCrudRepository());
		doSave(post(url), document, resultMatcher);
		verifyAll();
	}

	protected void doUpdate(String url, T document, ResultMatcher resultMatcher) throws Exception {
		log.info("Executing update against URL '" + url + "'");
		doSave(put(url), document, resultMatcher);
	}

	protected void doSave(MockHttpServletRequestBuilder builder, T document, ResultMatcher resultMatcher) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(document);
		ResultActions resultActions = this.mockMvc.perform(builder
				.principal(principal)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
		);
		if (enforceAssertions) resultActions = resultActions.andExpect(resultMatcher);
		MvcResult result = resultActions.andReturn();
		log.info("Executed request against URL '" + result.getRequest().getPathInfo() + "'");
		log.debug("Response: " + result.getResponse().getContentAsString());
	}

	private void doGet(String url, String id, T document, ResultMatcher resultMatcher) throws Exception {
		log.info("Executing get against URL '" + url + "'");

		EasyMock.resetToNice(crudService.getCrudRepository());
		EasyMock.expect(crudService.getCrudRepository().findOne((id))).andReturn(document).anyTimes();
		EasyMock.replay(crudService.getCrudRepository());

		ResultActions resultActions = this.mockMvc.perform(get(url)
				.principal(principal)
				.accept(MediaType.APPLICATION_JSON)
		);
		if (enforceAssertions) resultActions = resultActions.andExpect(resultMatcher);
		MvcResult result = resultActions.andReturn();
		log.debug("Response: " + result.getResponse().getContentAsString());
		EasyMock.verify(crudService.getCrudRepository());
	}

	public void setDocumentProvider(DocumentProvider<T> documentProvider) {
		this.documentProvider = documentProvider;
	}

	public void setCrudService(CrudService<T> crudService) {
		this.crudService = crudService;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public void setEnforceAssertions(boolean enforceAssertions) {
		this.enforceAssertions = enforceAssertions;
	}
}
