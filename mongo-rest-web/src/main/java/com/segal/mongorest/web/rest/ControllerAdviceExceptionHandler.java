package com.segal.mongorest.web.rest;

//import com.stormpath.spring.web.servlet.handler.RestError;
import com.segal.mongorest.web.exception.UnknownResourceException;
import com.segal.mongorest.web.pojo.RestError;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.net.ConnectException;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/16/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler({ConstraintViolationException.class})
	protected ResponseEntity<Object> handleConstraintViolation(RuntimeException e, WebRequest request) {
		return doHandleException(e, request, HttpStatus.BAD_REQUEST, e.getMessage(), "Constraint violation.");
	}

	@ExceptionHandler({ConnectException.class})
	protected ResponseEntity<Object> handleConnectException(RuntimeException e, WebRequest request) {
		return doHandleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "MongoDB may be down.");
	}

	@ExceptionHandler({UnknownResourceException.class})
	protected ResponseEntity<Object> handleUnknownResourceException(RuntimeException e, WebRequest request) {
		return doHandleException(e, request, HttpStatus.NOT_FOUND, e.getMessage(), e.getMessage());
	}

	@ExceptionHandler({Exception.class})
	protected ResponseEntity<Object> handleGeneralException(RuntimeException e, WebRequest request) {
		return doHandleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred.",
				e.getMessage());
	}

	private ResponseEntity<Object> doHandleException(RuntimeException e, WebRequest request, HttpStatus status,
	                                                 String message, String developerMessage) {
		if (log.isDebugEnabled()) log.debug("Handling exception: ", e);
		return handleExceptionInternal(
				e,
				new RestError(status.value(), status, message, developerMessage, ExceptionUtils.getStackTrace(e)),
				new HttpHeaders(),
				status,
				request);
	}

}
