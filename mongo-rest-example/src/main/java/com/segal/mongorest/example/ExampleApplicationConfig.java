package com.segal.mongorest.example;

import com.segal.mongorest.core.service.DefaultMongoCrudService;
import org.springframework.context.annotation.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/28/14
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.segal.mongorest"},
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class),
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class),
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
				@ComponentScan.Filter(pattern = {"com.segal.mongorest.example.repository.*"}, type = FilterType.REGEX)
		})
@Import({ExampleMongoConfig.class})
@PropertySource("classpath:application.properties")
public class ExampleApplicationConfig extends ExampleBaseApplicationConfig {

	@Override
	public DefaultMongoCrudService getMongoService(CrudRepository crudRepository) {
		return new DefaultMongoCrudService(crudRepository);
	}

}
