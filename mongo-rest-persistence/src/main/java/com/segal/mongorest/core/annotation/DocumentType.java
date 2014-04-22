package com.segal.mongorest.core.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/22/14
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocumentType {

	/**
  * @return the String document type to map to this class
  */
 String value();


}
