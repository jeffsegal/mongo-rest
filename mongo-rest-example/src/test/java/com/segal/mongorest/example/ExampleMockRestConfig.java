package com.segal.mongorest.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/24/14
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@Import(ExampleMockMongoConfig.class)
@EnableWebMvc
public class ExampleMockRestConfig {
}
