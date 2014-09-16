package com.segal.mongorest.web.auth.advice;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/1/14
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class MongoRestAuditAdvice extends MongoRestAdviceBase {

	@AfterReturning(pointcut = "mongoCrudSave()", returning = "retVal")
	public void logCrudSave(Object retVal) {
		doLog("save", retVal.getClass().getSimpleName(), retVal);
	}

	@After(value = "mongoCrudDelete(object)", argNames = "object")
	public void logCrudDelete(Object object) {
		doLog("delete", "", object);
	}

	@After("mongoCrudDeleteAll()")
	public void logCrudDeleteAll(JoinPoint joinPoint) {
		doLog("deleteAll", joinPoint.getTarget().getClass().getName(), "");
	}

	@After(value = "dbCollectionSave(object)", argNames = "joinPoint,object")
	public void logMongoOperationsSave(JoinPoint joinPoint, Object object) {
		DBCollection dbCollection = (DBCollection) joinPoint.getTarget();
		if (object instanceof BasicDBObject) doLog("save", dbCollection.getName(), object);
	}

	@After(value = "dbCollectionRemove(object)", argNames = "joinPoint,object")
	public void logMongoOperationsRemove(JoinPoint joinPoint, Object object) {
		DBCollection dbCollection = (DBCollection) joinPoint.getTarget();
		if (object instanceof BasicDBObject) doLog("remove", dbCollection.getName(), object);
	}

	/**
	 * Logs an operation with the following format: user; operation; collection; object
	 *
	 * @param operation
	 * @param collection
	 * @param object
	 */
	private void doLog(String operation, String collection, Object object) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.debug(auth.getName() + ";" + operation + ";" + collection + ";" + object);
	}

}
