package com.extensions.logmonitor.util;

/**
 * @author Rezar
 * @createDate :Dec 24, 2015 8:53:52 PM
 * @desc :
 */
public class TupleUtil {

	public static <A, B> TwoTuple<A, B> tuple(A a, B b) {
		return new TwoTuple<A, B>(a, b);
	}

}
