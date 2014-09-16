package com.segal.mongorest.web.auth.advice;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 5/1/14
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class MongoRestAuthorizationAdvice extends MongoRestAdviceBase {

//	@PreAuthorize("isAuthenticated() and hasPermission(#storyId, 'isDirector')")
//	@Around(value = "mongoCrudFind(object)", argNames = "pjp, object")
//	public Object authorizeCrudFind(ProceedingJoinPoint pjp, Object object) {
//		Authentication authn = SecurityContextHolder.getContext().getAuthentication();
//		if (authn != null) {
//			authn.getAuthorities().contains();
//		}
//		else throw new AuthorizationServiceException("No Authentication object bound to session.");
//	}
//
//	@Before("mongoCrudSave()")
//	public void authorizeCrudSave() {
//
//	}

//	@After(value = "dbCollectionSave(object)", argNames = "joinPoint, object")
//	public void logMongoOperationsSave(JoinPoint joinPoint, Object object) {
//		DBCollection dbCollection = (DBCollection) joinPoint.getTarget();
//		if (object instanceof BasicDBObject) doLog("save", dbCollection.getName(), object);
//	}

}
