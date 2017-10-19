package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class StringDescAndSer implements SeriAndDeser<String> {

	public static final StringDescAndSer INSTANCE = new StringDescAndSer();

	@Override
	public void serialize(String obj, ByteBuffer buff) {
		buff.putInt(obj.getBytes().length);
		buff.put(obj.getBytes());
	}

	@Override
	public String deserialize(ByteBuffer buf) {
		int bufSIze = buf.getInt();
		byte[] dst = new byte[bufSIze];
		buf.get(dst);
		try {
			return new String(dst, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return new String(dst);
		}
	}

}
