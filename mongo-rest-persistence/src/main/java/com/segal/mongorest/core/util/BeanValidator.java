package com.segal.mongorest.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jeff
 * Date: 7/19/11
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BeanValidator {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    Validator validator;

    @PostConstruct
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups);
    }

    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups);
    }

    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
                                                  Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups);
    }


}
