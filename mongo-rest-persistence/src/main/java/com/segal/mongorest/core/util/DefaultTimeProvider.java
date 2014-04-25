package com.segal.mongorest.core.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 4/25/14
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTimeProvider implements TimeProvider {

	@Override
	public long getSystemTimeMillis() {
		return System.currentTimeMillis();
	}

	@Override
	public long getSystemTimeNanos() {
		return System.nanoTime();
	}

}
