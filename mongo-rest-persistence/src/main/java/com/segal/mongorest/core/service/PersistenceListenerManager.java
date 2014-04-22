package com.segal.mongorest.core.service;

import com.segal.mongorest.core.pojo.BaseDocument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/17/14
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PersistenceListenerManager<T extends BaseDocument> {

	Collection<PersistenceListener<T>> persistenceListeners = new ArrayList<PersistenceListener<T>>();

	public void addPersistenceListener(PersistenceListener<T> persistenceListener) {
		persistenceListeners.add(persistenceListener);
	}

	public void removePersistenceListener(PersistenceListener<T> persistenceListener) {
		persistenceListeners.remove(persistenceListener);
	}

	public void notifyDocumentAdded(T pojo) {
		for (PersistenceListener<T> persistenceListener : persistenceListeners) {
			persistenceListener.documentAdded(pojo);
		}
	}

	public void notifyDocumentUpdated(T pojo) {
		for (PersistenceListener<T> persistenceListener : persistenceListeners) {
			persistenceListener.documentUpdated(pojo);
		}
	}

	public void notifyDocumentDeleted(String id) {
		for (PersistenceListener<T> persistenceListener : persistenceListeners) {
			persistenceListener.documentDeleted(id);
		}
	}

}
