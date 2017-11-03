package com.extensions.logmonitor.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年7月14日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class LoadCache<K, V> {

	private InitValue<K, V> initValue;

	private final ConcurrentHashMap<K, Future<V>> cacheMap = new ConcurrentHashMap<K, Future<V>>();

	public LoadCache() {

	}

	public LoadCache(InitValue<K, V> initValue) {
		this.initValue = initValue;
	}

	public V getCache(K keyValue) {
		Future<V> value = null;
		try {
			// 从缓存获取数据
			value = cacheMap.get(keyValue);
			// 如果没有的话，把数据放到缓存
			if (value == null) {
				value = putCache(keyValue, null);
				return value.get();
			}
			return value.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public V getOrCache(K keyValue, V valueObj) {
		Future<V> value = null;
		try {
			// 从缓存获取数据
			value = cacheMap.get(keyValue);
			// 如果没有的话，把数据放到缓存
			if (value == null) {
				value = putCache(keyValue, valueObj);
				return value.get();
			}
			return value.get();
		} catch (Exception e) {
		}
		return null;
	}

	public Future<V> putCache(final K keyValue, final V value) {
		// //把数据放到缓存
		Future<V> futureValue = null;
		Callable<V> callable = new Callable<V>() {
			@Override
			public V call() throws Exception {
				if (value == null && initValue != null) {
					return initValue.initValue(keyValue);
				}
				return value;
			}
		};
		FutureTask<V> futureTask = new FutureTask<V>(callable);
		futureValue = cacheMap.putIfAbsent(keyValue, futureTask);
		if (futureValue == null) {
			futureValue = futureTask;
			futureTask.run();
		}
		return futureValue;
	}

	public static interface InitValue<K, V> {
		public V initValue(K key);
	}

}
