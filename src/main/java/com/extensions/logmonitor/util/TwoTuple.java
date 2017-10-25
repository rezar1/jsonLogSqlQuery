package com.extensions.logmonitor.util;

/**
 * @author Rezar
 * @createDate :Dec 24, 2015 8:53:06 PM
 * @desc :
 */
public class TwoTuple<A, B> {

	public final A first;
	public final B second;

	public TwoTuple(A a, B b) {
		this.first = a;
		this.second = b;
	}

	@Override
	public String toString() {
		return "TwoTuple [first=" + first + ", second=" + second + "]";
	}

}
