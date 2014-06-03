package com.segal.mongorest.example;

import com.segal.mongorest.core.service.DefaultMongoCrudService;
import com.segal.mongorest.core.util.TimeProvider;
import org.springframework.context.annotation.*;
import org.springframework.data.repository.CrudRepository;

import static org.easymock.EasyMock.createNiceMock;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 6/3/14
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.segal.mongorest"},
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import({ExampleMockMongoConfig.class})
@PropertySource("classpath:application.properties")
public class ExampleMockApplicationConfigExample extends ExampleBaseApplicationConfig {

	TimeProvider mockTimeProvider = createNiceMock(TimeProvider.class);

	@Override
	protected DefaultMongoCrudService getMongoService(CrudRepository crudRepository) {
		return new DefaultMongoCrudService(crudRepository, mockTimeProvider);
	}

}
