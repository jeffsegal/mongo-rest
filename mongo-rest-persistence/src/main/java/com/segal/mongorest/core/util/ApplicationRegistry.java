package com.segal.mongorest.core.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
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

	public ApplicationRegistry(List<String> packages) {
		super(packages);
	}

	@PostConstruct
	public void init() {
		log.info("Initializing " + this.getClass() + "...");
		scanBeans();
		scanClassPath();
	}

	@Override
	public void handleClasspathEntry(Class clazz) {
		log.trace("Looking for '" + DocumentType.class + "' annotation on class '" +
				clazz.getCanonicalName() + "'");
		DocumentType annotation = (DocumentType) clazz.getAnnotation(DocumentType.class);
		if (annotation != null) {
			if (annotation.value() != null) {
				if (BaseDocument.class.isAssignableFrom(clazz)) {
					log.debug("Mapping '" + annotation.value() + "' to " + BaseDocument.class.getSimpleName() +
							" '" + clazz.getCanonicalName() + "'");
					documentRegistry.put(annotation.value(), clazz);
				}
			}
		}
	}

	@Override
	public void handleBeanEntry(Object bean) {
		DocumentType annotation = bean.getClass().getAnnotation(DocumentType.class);
		if (annotation != null) {
			log.debug("Mapping '" + annotation.value() + "' to bean '" + bean.getClass().getCanonicalName() + "'");
			if (bean instanceof CrudRepository) {
				crudRepositoryRegistry.put(annotation.value(), (CrudRepository<? extends BaseDocument, String>) bean);
			}
			else if (bean instanceof CrudService) {
				crudServiceRegistry.put(annotation.value(), (CrudService<? extends BaseDocument>) bean);
			}
			else if (bean instanceof PersistenceListenerManager) {
				persistenceListenerManagerRegistry.put(annotation.value(), (PersistenceListenerManager<? extends BaseDocument>) bean);
			}
		}
	}

	public Class getDocumentClass(String documentType) {
		return documentRegistry.get(documentType);
	}

	public String getDocumentType(Class clazz) {
		return documentRegistry.inverse().get(clazz);
	}

	public CrudRepository<? extends BaseDocument, String> getCrudRepository(String documentType) {
		return crudRepositoryRegistry.get(documentType);
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
