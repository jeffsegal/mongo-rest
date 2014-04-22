package com.segal.mongorest.core.util;

import com.segal.mongorest.core.annotation.DocumentType;
import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import com.segal.mongorest.core.service.PersistenceListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ClasspathAndBeanScanner {

	Logger log = LoggerFactory.getLogger(this.getClass());

	List<String> packages = Arrays.asList("com.segal.mongorest");

	@Autowired
	ApplicationContext applicationContext;

	public abstract void handleClasspathEntry(Class clazz);

	public abstract void handleBeanEntry(Object bean);

	protected ClasspathAndBeanScanner() {
	}

	protected ClasspathAndBeanScanner(List<String> packages) {
		this.packages = packages;
	}

	protected void scanClassPath() {
		log.info("Scanning classpath to build registries...");
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(DocumentType.class));
		for (String basePackage : packages) {
			for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
				try {
					Class clazz = Class.forName(bd.getBeanClassName());
					handleClasspathEntry(clazz);
				} catch (ClassNotFoundException e) {
					log.error("Could not load class with name '" + bd.getBeanClassName() + "'", e);
				}
			}
		}
	}

	protected void scanBeans() {
		log.info("Scanning bean instances to build registries...");
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DocumentType.class);
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			String name = entry.getKey();
			Object bean = entry.getValue();
			log.trace("Looking for '" + DocumentType.class + "' annotation on class '" + bean.getClass() + "'");
			handleBeanEntry(bean);
		}
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}


}
