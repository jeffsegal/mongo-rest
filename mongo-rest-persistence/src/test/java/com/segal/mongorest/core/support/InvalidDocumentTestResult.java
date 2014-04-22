package com.segal.mongorest.core.support;

import com.segal.mongorest.core.pojo.BaseDocument;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidDocumentTestResult<T extends BaseDocument> extends DocumentTestResult<T> {

	Exception exception;

	public InvalidDocumentTestResult() {
	}

	public InvalidDocumentTestResult(T document, boolean isUpdate, Exception exception) {
		super(document, isUpdate);
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
