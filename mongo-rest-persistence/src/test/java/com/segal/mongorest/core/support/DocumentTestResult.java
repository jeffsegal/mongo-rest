package com.segal.mongorest.core.support;

import com.segal.mongorest.core.pojo.BaseDocument;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentTestResult<T extends BaseDocument> implements Serializable {

	private static final long serialVersionUID = 6257906885431708055L;

	protected T document;
	protected Operation operation;

	public enum Operation {find, create, update}

	public DocumentTestResult() {
	}

	public DocumentTestResult(T document) {
		this.document = document;
	}

	public DocumentTestResult(T document, Operation operation) {
		this.document = document;
		this.operation = operation;
	}

	public T getDocument() {
		return document;
	}

	public void setDocument(T document) {
		this.document = document;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
