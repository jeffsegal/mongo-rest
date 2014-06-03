package com.segal.mongorest.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/17/14
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebMvc
@ComponentScan(
		basePackages = {"com.segal.mongorest.example.controller"})
public class ExampleWebConfig extends WebMvcConfigurerAdapter {

}
