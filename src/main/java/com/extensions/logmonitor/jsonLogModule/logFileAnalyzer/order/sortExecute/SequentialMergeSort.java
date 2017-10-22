package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.sortExecute;

import java.util.Comparator;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月13日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class SequentialMergeSort {

	/** Merge method */
	public static <T> void merge(T[] first, T[] second, T[] temp, Comparator<T> comparator) {
		// index positions - staring with first elements
		int indexFirst = 0;
		int indexSecond = 0;
		// index positions - staring with first positions
		int indexMerged = 0;

		// compare elements at first and second index positions
		// move smaller element at indexMerged
		while (indexFirst < first.length && indexSecond < second.length) {
			if (comparator.compare(first[indexFirst], second[indexSecond]) < 0) {
				temp[indexMerged] = first[indexFirst];
				indexFirst++;
			} else {
				temp[indexMerged] = second[indexSecond];
				indexSecond++;
			}
			indexMerged++;
		}
		// 把左边剩余的数移入数组
		while (indexFirst < first.length) {
			temp[indexMerged++] = first[indexFirst++];
		}
		// 把右边边剩余的数移入数组
		while (indexSecond < first.length) {
			temp[indexMerged++] = second[indexSecond++];
		}
	}
}
