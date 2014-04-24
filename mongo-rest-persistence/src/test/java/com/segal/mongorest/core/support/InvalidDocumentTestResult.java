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

	private static final long serialVersionUID = -5722911050556692121L;

	Class<? extends Exception> exceptionClass;

	public InvalidDocumentTestResult() {
	}

	public InvalidDocumentTestResult(T document, boolean isUpdate, Class<? extends Exception> exceptionClass) {
		super(document, isUpdate);
		this.exceptionClass = exceptionClass;
	}

	public Class<? extends Exception> getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class<? extends Exception> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

}
