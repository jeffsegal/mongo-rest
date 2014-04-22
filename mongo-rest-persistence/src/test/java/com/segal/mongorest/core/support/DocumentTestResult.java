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

	protected T document;
	protected boolean isUpdate = false;

	public DocumentTestResult() {
	}

	public DocumentTestResult(T document) {
		this.document = document;
	}

	public DocumentTestResult(T document, boolean isUpdate) {
		this.document = document;
		this.isUpdate = isUpdate;
	}

	public T getDocument() {
		return document;
	}

	public void setDocument(T document) {
		this.document = document;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean update) {
		isUpdate = update;
	}

}
