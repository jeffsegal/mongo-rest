package com.segal.mongorest.core.support;

import com.segal.mongorest.core.pojo.BaseDocument;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/16/14
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DocumentBuilder<T extends BaseDocument> {

	Collection<DocumentTestResult<T>> createDocuments();

}