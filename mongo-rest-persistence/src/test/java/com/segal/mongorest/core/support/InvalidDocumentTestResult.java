package com.segal.mongorest.core.support;

import com.google.common.base.Objects;
import com.segal.mongorest.core.pojo.BaseDocument;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

	public InvalidDocumentTestResult(T document, Operation operation, Class<? extends Exception> exceptionClass) {
		super(document, operation);
		this.exceptionClass = exceptionClass;
	}

	public Class<? extends Exception> getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class<? extends Exception> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("exceptionClass", exceptionClass)
				.toString();
	}

}
