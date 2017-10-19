package com.extensions.logmonitor.util.sortUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月13日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ParallelMergeFileSort {

	/** Method call */
	public static <T> void parallelSort(T[] list, int realSize, Comparator<T> comparator) {
		RecursiveAction mainTask = new SortTask<T>(list, realSize, comparator);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}

	/** Sorting method, called recursively */
	private static class SortTask<T> extends RecursiveAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int THRESHOLD = 5000;
		private T[] list;
		private int realSize;
		private Comparator<T> comparator;

		/**
		 * @param realSize
		 * @param list2
		 * @param comparator
		 */
		public SortTask(T[] list, int realSize, Comparator<T> comparator) {
			this.list = list;
			this.realSize = realSize;
			this.comparator = comparator;
		}

		/** compute defines how the SortTask is runned */
		@SuppressWarnings("unchecked")
		@Override
		protected void compute() {
			if (realSize <= THRESHOLD) {
				Arrays.sort(list, 0, realSize, comparator);
			} else {
				Class<T> componentType = (Class<T>) list.getClass().getComponentType();
				// Get first half
				T[] firstHalf = GenericsUtils.createArray(componentType, realSize / 2);
				System.arraycopy(list, 0, firstHalf, 0, realSize / 2);
				// Get second half
				Integer secondHalflength = realSize - realSize / 2;
				T[] secondHalf = GenericsUtils.createArray(componentType, secondHalflength);
				System.arraycopy(list, realSize / 2, secondHalf, 0, secondHalflength);
				// Recursively sort the two halves
				invokeAll(new SortTask<T>(firstHalf, realSize / 2, this.comparator),
						new SortTask<T>(secondHalf, secondHalflength, this.comparator));
				// Merge firstHalf and secondHalf into one list
				SequentialMergeSort.merge(firstHalf, secondHalf, list, this.comparator);

			}
		}
	}

}
