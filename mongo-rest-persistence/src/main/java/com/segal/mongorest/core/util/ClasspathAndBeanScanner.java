package com.segal.mongorest.core.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.segal.mongorest.core.annotation.DocumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
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

	public abstract void handleClasspathEntry(Class clazz, String documentType);

	public abstract void handleBeanEntry(Object bean, String documentType);

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
					handleClasspathEntry(clazz, getDocumentType(clazz));
				} catch (ClassNotFoundException e) {
					log.error("Could not load class with name '" + bd.getBeanClassName() + "'", e);
				}
			}
		}
	}

	protected void scanBeans() {
		log.info("Scanning bean instances to build registries...");
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DocumentType.class);

		// Look in declared Bean classes
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			String name = entry.getKey();
			Object bean = entry.getValue();
			DocumentType annotation = bean.getClass().getAnnotation(DocumentType.class);
			if (annotation != null) {
				handleBeanEntry(bean, getDocumentType(bean.getClass()));
			}
		}

		// Look in @Bean objects
		Map<String, Map<String, Object>> beanAnnotationAttributes = getBeansWithAnnotation(DocumentType.class, null);
		for (Map.Entry<String, Map<String, Object>> entry : beanAnnotationAttributes.entrySet()) {
			String name = entry.getKey();
			Map<String, Object> attributes = entry.getValue();
			Object bean = applicationContext.getBean(name);
			String documentType = (String) attributes.get("value");
			handleBeanEntry(bean, documentType);
		}
	}

	public Map<String, Map<String, Object>> getBeansWithAnnotation(Class<? extends Annotation> type,
	                                           Predicate<Map<String, Object>> attributeFilter) {
		Map<String, Map<String, Object>> result = Maps.newConcurrentMap();
		ConfigurableListableBeanFactory factory = (ConfigurableListableBeanFactory)
				applicationContext.getAutowireCapableBeanFactory();
		for (String name : factory.getBeanDefinitionNames()) {
			BeanDefinition bd = factory.getBeanDefinition(name);
			if (bd.getSource() instanceof StandardMethodMetadata) {
				StandardMethodMetadata metadata = (StandardMethodMetadata) bd.getSource();
				Map<String, Object> attributes = metadata.getAnnotationAttributes(type.getName());
				if (null == attributes) {
					continue;
				}
				if (attributeFilter == null || attributeFilter.apply(attributes)) {
					result.put(name, attributes);
				}
			}
		}
		return result;
	}

	private String getDocumentType(Class clazz) {
		log.trace("Looking for '" + DocumentType.class + "' annotation on class '" + clazz + "'");
		String documentType = null;
		DocumentType annotation = (DocumentType) clazz.getAnnotation(DocumentType.class);
		if (annotation != null) {
			if (DocumentType.DEFAULT_TYPE.equals(annotation.value())) {
				documentType = clazz.getSimpleName().toLowerCase();
			}
			else {
				documentType = annotation.value();
			}
		}
		return documentType;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}


}
