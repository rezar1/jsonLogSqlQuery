package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.nio.ByteBuffer;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月29日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface SeriAndDeser<V> {

	public void serialize(V obj, ByteBuffer buff);

	public V deserialize(ByteBuffer buf);

}
