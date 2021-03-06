package com.segal.mongorest.core.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ApplicationRegistry extends ClasspathAndBeanScanner {

	BiMap<String, Class<? extends BaseDocument>> documentRegistry = HashBiMap.create();
	Map<String, CrudRepository<? extends BaseDocument, String>> crudRepositoryRegistry = new ConcurrentHashMap<>();
	Map<String, CrudService<? extends BaseDocument>> crudServiceRegistry = new ConcurrentHashMap<>();
	Map<String, PersistenceListenerManager<? extends BaseDocument>> persistenceListenerManagerRegistry = new
			ConcurrentHashMap<>();

	public ApplicationRegistry() {
	}

	public ApplicationRegistry(String... packages) {
		super(Arrays.asList(packages));
	}

	public ApplicationRegistry(List<String> packages) {
		super(packages);
	}

	@Override
	public void handleClasspathEntry(Class clazz, String documentType) {
		log.trace("Looking for '" + DocumentType.class + "' annotation on class '" +
				clazz.getCanonicalName() + "'");
		if (BaseDocument.class.isAssignableFrom(clazz)) {
			log.info("Mapping '" + documentType + "' to " + BaseDocument.class.getSimpleName() +
					" '" + clazz.getCanonicalName() + "'");
			documentRegistry.put(documentType, clazz);
		}
	}

	@Override
	public void handleBeanEntry(Object bean, String documentType) {
		log.info("Mapping '" + documentType + "' to bean '" + bean.getClass().getCanonicalName() + "'");
		if (bean instanceof CrudRepository) {
			crudRepositoryRegistry.put(documentType, (CrudRepository<? extends BaseDocument, String>) bean);
		} else if (bean instanceof CrudService) {
			crudServiceRegistry.put(documentType, (CrudService<? extends BaseDocument>) bean);
		} else if (bean instanceof PersistenceListenerManager) {
			persistenceListenerManagerRegistry.put(documentType, (PersistenceListenerManager<? extends BaseDocument>) bean);
		}
	}

	public Class getDocumentClass(String documentType) {
		return documentRegistry.get(documentType);
	}

	public String getDocumentType(Class clazz) {
		return documentRegistry.inverse().get(clazz);
	}

	public CrudRepository<? extends BaseDocument, String> getCrudRepository(Class clazz) {
		String documentType = getDocumentType(clazz);
		if (clazz != null) {
			return getCrudRepository(documentType);
		}
		else return null;
	}

	public CrudRepository<? extends BaseDocument, String> getCrudRepository(String documentType) {
		return crudRepositoryRegistry.get(documentType);
	}

	public CrudService<? extends BaseDocument> getCrudService(Class clazz) {
		String documentType = getDocumentType(clazz);
		if (clazz != null) {
			return getCrudService(documentType);
		}
		else return null;
	}

	public CrudService<? extends BaseDocument> getCrudService(String documentType) {
		return crudServiceRegistry.get(documentType);
	}

	public PersistenceListenerManager<? extends BaseDocument> getPersistenceListenerManager(String documentType) {
		return persistenceListenerManagerRegistry.get(documentType);
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

}
