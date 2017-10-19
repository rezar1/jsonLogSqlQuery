package com.extensions.logmonitor.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年7月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MultiKeyMap<K, V> extends HashMap<Integer, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<K, Integer> keyMapper = new HashMap<>();

	private AtomicInteger valueIndex = new AtomicInteger(0);

	public MultiKeyMap() {
		super();
	}

	public void putWithMayExistsKey(K mayExistsKey, K newKey, V value) {
		if (this.keyMapper.containsKey(mayExistsKey)) {
			this.keyMapper.put(newKey, this.keyMapper.get(mayExistsKey));
		} else {
			this.putKeyValue(newKey, value);
		}
	}

	@SuppressWarnings("unchecked")
	public void putKeyValue(K key, V value) {
		this.put(value, key);
	}

	@SuppressWarnings("unchecked")
	public void put(V value, K... keys) {
		if (!(keys == null || keys.length == 0)) {
			Integer mapKey = null;
			for (K keyIn : keys) {
				if (keyMapper.containsKey(keyIn)) {
					mapKey = keyMapper.get(keyIn);
					break;
				}
			}
			if (mapKey == null) {
				mapKey = valueIndex.incrementAndGet();
				super.put(mapKey, value);
			}
			for (K key : keys) {
				this.keyMapper.put(key, mapKey);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		try {
			K keyIn = (K) key;
			Integer valueIndex = this.keyMapper.get(keyIn);
			if (valueIndex != null) {
				return super.get(valueIndex.intValue());
			}
		} catch (Exception e) {
			System.out.println("can not cast key:{} with type:{} " + key);
		}
		return null;
	}

}
