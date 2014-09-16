package com.segal.mongorest.web.rest;

import com.segal.mongorest.core.pojo.BaseDocument;
import com.segal.mongorest.core.service.CrudService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/16/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentControllerFactory implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		BeanDefinitionBuilder datasourceDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(BasicDataSource.class).addPropertyValue("url", "jdbc..");
//		beanFactory.registerBeanDefinition(datasourceDefinitionBuilder.getBeanDefinition());
	}

	public DocumentController createDocumentController(Class<? extends BaseDocument> clazz) {
		return new DocumentController() {
			@Override
			public void setService(CrudService service) {
				super.setService(service);    //To change body of overridden methods use File | Settings | File Templates.
			}
		};
	}
}
