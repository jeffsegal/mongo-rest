package com.segal.mongorest.web;

import com.segal.mongorest.core.DefaultMongoConfig;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/15/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(
		basePackages = {"com.segal"},
		excludeFilters = {
				@ComponentScan.Filter(pattern = {".*Mock.*"}, type = FilterType.REGEX),
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@EnableWebMvc
@Import(DefaultMongoConfig.class)
@PropertySource("classpath:profars-webapp.properties")
public class RestConfig extends WebMvcConfigurerAdapter {

}
