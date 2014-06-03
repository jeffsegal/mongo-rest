package com.segal.mongorest.core;

import com.segal.mongorest.core.service.DefaultMongoCrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 6/3/14
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseApplicationConfig {

	@Bean
	@Qualifier("classpathToScan")
	public List<String> packages() {
		return getPackagesList();
	}

	protected abstract List<String> getPackagesList();

	protected abstract DefaultMongoCrudService getMongoService(CrudRepository crudRepository);

}
