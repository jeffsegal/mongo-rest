package com.segal.mongorest.web;

import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.support.DocumentTestResult;
import org.springframework.test.web.servlet.ResultMatcher;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestErrorResult<T extends BaseDocument> extends DocumentTestResult<T> {

	private static final long serialVersionUID = -8290138532696665485L;

	ResultMatcher expectation;

	public RestErrorResult() {
	}

	public RestErrorResult(ResultMatcher expectation) {
		this.expectation = expectation;
	}

	public RestErrorResult(T document, ResultMatcher expectation) {
		super(document);
		this.expectation = expectation;
	}

	public RestErrorResult(T document, Operation operation, ResultMatcher expectation) {
		super(document, operation);
		this.expectation = expectation;
	}

	public ResultMatcher getExpectation() {
		return expectation;
	}

	public void setExpectation(ResultMatcher expectation) {
		this.expectation = expectation;
	}
}
