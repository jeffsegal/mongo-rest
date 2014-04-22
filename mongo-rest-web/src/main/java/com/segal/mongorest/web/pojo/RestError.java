package com.segal.mongorest.web.pojo;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/16/14
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestError implements Serializable {

	private static final long serialVersionUID = -6726752888051172232L;

	int code;
	HttpStatus httpStatus;
	String message;
	String developerMessage;
	String stackTrace;

	public RestError() {
	}

	public RestError(int code, HttpStatus httpStatus, String message, String developerMessage, String stackTrace) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
		this.developerMessage = developerMessage;
		this.stackTrace = stackTrace;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
}
