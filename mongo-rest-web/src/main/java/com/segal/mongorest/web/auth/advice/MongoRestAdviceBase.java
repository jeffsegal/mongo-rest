package com.segal.mongorest.web.auth.advice;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/1/14
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MongoRestAdviceBase {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository.find*(..)) && args(object)")
	protected void mongoCrudFind() {
	}

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository.save(..))")
	protected void mongoCrudSave() {
	}

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository.delete(..)) && args(object)")
	protected void mongoCrudDelete(Object object) {
	}

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository.deleteAll(..))")
	protected void mongoCrudDeleteAll() {
	}

	@Pointcut("execution(* com.mongodb.DBCollection.find*(..)) && args(object)")
	protected void dbCollectionFind(Object object) {
	}

	@Pointcut("execution(* com.mongodb.DBCollection.save(..)) && args(object)")
	protected void dbCollectionSave(Object object) {
	}

	@Pointcut("execution(* com.mongodb.DBCollection.remove(..)) && args(object)")
	protected void dbCollectionRemove(Object object) {
	}

}
