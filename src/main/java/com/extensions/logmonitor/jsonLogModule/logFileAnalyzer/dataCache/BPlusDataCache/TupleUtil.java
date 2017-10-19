package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

public class TupleUtil {

	public static <S1, S2> TwoTuple<S1, S2> tuple(S1 first, S2 second) {
		return new TwoTuple<S1, S2>(first, second);
	}

}
