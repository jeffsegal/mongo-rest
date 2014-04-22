package com.segal.mongorest.core.service;

import com.segal.mongorest.core.pojo.BaseDocument;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/17/14
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersistenceListener<T extends BaseDocument> {

	void documentAdded(T pojo);

	void documentUpdated(T pojo);

	void documentDeleted(String id);

}
