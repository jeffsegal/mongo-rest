package com.segal.mongorest.core.support;

import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.util.ClasspathAndBeanScanner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TestRegistry extends ClasspathAndBeanScanner {

	Map<String, DocumentProvider<? extends BaseDocument>> documentBuilderRegistry = new ConcurrentHashMap<>();

	public TestRegistry() {
	}

	public TestRegistry(List<String> packages) {
		super(packages);
	}

	@Override
	public void handleClasspathEntry(Class clazz, String documentType) {
		// no-op
	}

	@Override
	public void handleBeanEntry(Object bean, String documentType) {
		if (bean instanceof DocumentProvider) {
			documentBuilderRegistry.put(documentType, (DocumentProvider<? extends BaseDocument>) bean);
		}
	}

	public DocumentProvider<? extends BaseDocument> getDocumentBuilder(String documentType) {
		return documentBuilderRegistry.get(documentType);
	}

}
