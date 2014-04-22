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

	public ValidDocumentTestResult() {
	}

	public ValidDocumentTestResult(T document, boolean isUpdate) {
		super(document, isUpdate);
	}

}
