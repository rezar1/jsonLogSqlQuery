package com.extensions.logmonitor.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ArrayGetter<T> implements Iterable<T> {

	private Collection<T> collectionGetter;

	@SuppressWarnings("unchecked")
	public ArrayGetter(Object value) {
		if (value instanceof Collection<?>) {
			this.collectionGetter = (Collection<T>) value;
		}
	}

	@Override
	public Iterator<T> iterator() {
		return collectionGetter.iterator();
	}

}
