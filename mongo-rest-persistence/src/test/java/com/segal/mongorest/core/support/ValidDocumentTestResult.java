package com.segal.mongorest.core.support;

import com.segal.mongorest.core.pojo.BaseDocument;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidDocumentTestResult<T extends BaseDocument> extends DocumentTestResult<T> {

	private static final long serialVersionUID = -1312286096241020417L;

	public ValidDocumentTestResult() {
	}

	public ValidDocumentTestResult(T document, Operation operation) {
		super(document, operation);
	}

}
