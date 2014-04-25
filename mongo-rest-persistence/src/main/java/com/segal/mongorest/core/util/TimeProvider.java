package com.segal.mongorest.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/25/14
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TimeProvider {

	public long getSystemTimeMillis();

	public long getSystemTimeNanos();

}
