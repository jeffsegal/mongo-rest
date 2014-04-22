package com.segal.mongorest.web.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/15/14
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnknownResourceException extends RuntimeException {

	private static final long serialVersionUID = -6784100527964021242L;

	public UnknownResourceException(String message) {
		super(message);
	}

}
