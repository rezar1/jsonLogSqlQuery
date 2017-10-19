package com.extensions.logmonitor.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MultiThreadQuickSort {
	public static final File RANDOMFILE = new File("D:/", "randomFile.txt");
	public static final File SORTFILE = new File("D:/", "sortFile.txt");
	public static final String CHARSETNAME = "UTF-8";
	public static final int N = 2000_0000;
	public static final int S = 300_0000;
	public static final int[] A = new int[N];
	public static final AtomicInteger THREADCOUNT = new AtomicInteger(100);

	public static void main(String[] args) throws IOException, InterruptedException {
		// writeRrandomFile();
		readRrandomFile();
		// sort();
		forkJoinSort();
		checkSort();
		// writeSortFile();
	}

	public static void arraysSort() {
		long beginTime = System.currentTimeMillis();
		java.util.Arrays.sort(A);
		long endTime = System.currentTimeMillis();
		System.out.println("arrays sort file:" + (endTime - beginTime) + "ms");
	}

	public static void forkJoinSort() {
		long beginTime = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		forkJoinPool.submit(new SortTask(A, 0, A.length - 1));
		forkJoinPool.shutdown();
		try {
			forkJoinPool.awaitTermination(10_000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("sort file:" + (endTime - beginTime) + "ms");
	}

	public static void writeRrandomFileOnce() {
		if (!RANDOMFILE.exists()) {
			writeRrandomFile();
		}
		System.out.println("random file exists!");
	}

	public static void writeRrandomFile() {
		long beginTime = System.currentTimeMillis();
		Random rd = new Random();
		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(RANDOMFILE), CHARSETNAME))) {
			for (int i = 0; i < N; i++) {
				bw.write(String.valueOf(rd.nextInt()));
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("write random file:" + (endTime - beginTime) + "ms");
	}

	public static void readRrandomFile() {
		long beginTime = System.currentTimeMillis();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(RANDOMFILE), CHARSETNAME));) {
			String line;
			int index = 0;
			while ((line = br.readLine()) != null) {
				A[index++] = Integer.valueOf(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("read random file:" + (endTime - beginTime) + "ms");
	}

	public static void sort() {
		long beginTime = System.currentTimeMillis();
		try {
			sort(A, 0, A.length - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("sort file:" + (endTime - beginTime) + "ms");
	}

	public static void checkSort() {
		long beginTime = System.currentTimeMillis();
		boolean flag = true;
		try {
			int Nmm = N - 2;
			for (int i = 0; i < Nmm; i++) {
				if (A[i] > A[i + 1]) {
					System.out.println(A[i] + ">" + A[i + 1]);
					flag = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("check sort file " + flag + ":" + (endTime - beginTime) + "ms");
	}

	public static void writeSortFile() {
		long beginTime = System.currentTimeMillis();
		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(SORTFILE), CHARSETNAME))) {
			for (int i = 0; i < N; i++) {
				bw.write(String.valueOf(A[i]));
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("write sort file:" + (endTime - beginTime) + "ms");
	}

	public static void sort(final int a[], final int low, final int high) {
		if (high - low < S) {
			Arrays.sort(a, low, high + 1);
			return;
		}
		int i = low;
		int j = high;
		if (j - i > 2) {
			if ((a[i] < a[j - i] && a[j - i] < a[j]) || (a[j] < a[j - i] && a[j - i] < a[i])) {
				int t = a[i];
				a[i] = a[j - i];
				a[j - i] = t;
			} else {
				if ((a[i] < a[j] && a[j] < a[j - i]) || (a[j - i] < a[j] && a[j] < a[i])) {
					int t = a[i];
					a[i] = a[j];
					a[j] = t;
				}
			}
		}
		int pivot = a[low];
		while (i < j) {
			while (i < j && a[j] > pivot) {
				j--;
			}
			if (i < j) {
				a[i++] = a[j];
			}
			while (i < j && a[i] < pivot) {
				i++;
			}
			if (i < j) {
				a[j--] = a[i];
			}
		}
		a[i] = pivot;
		List<Thread> threads = new ArrayList<Thread>();
		final int imm = i - 1;
		if (low < imm) {
			if (MultiThreadQuickSort.THREADCOUNT.get() > 0 && imm - low > S) {
				Thread t = new Thread() {
					{
						MultiThreadQuickSort.THREADCOUNT.decrementAndGet();
					}

					@Override
					public void run() {
						sort(a, low, imm);
						MultiThreadQuickSort.THREADCOUNT.addAndGet(1);
					}
				};
				t.start();
				threads.add(t);
			} else {
				Arrays.sort(a, low, imm + 1);
			}
		}
		final int ipp = i + 1;
		if (high > ipp) {
			if (MultiThreadQuickSort.THREADCOUNT.get() > 0 && high - ipp > S) {
				Thread t = new Thread() {
					{
						MultiThreadQuickSort.THREADCOUNT.decrementAndGet();
					}

					@Override
					public void run() {
						sort(a, ipp, high);
						MultiThreadQuickSort.THREADCOUNT.addAndGet(1);
					}
				};
				t.start();
				threads.add(t);
			} else {
				Arrays.sort(a, ipp, high + 1);
			}
		}

		try {
			for (Thread t : threads)
				t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("serial")
class SortTask extends RecursiveAction {
	final int[] array;
	final int start;
	final int end;
	private int THRESHOLD = 300_0000;

	public SortTask(int[] array) {
		this.array = array;
		this.start = 0;
		this.end = array.length - 1;
	}

	public SortTask(int[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	@Override
	protected void compute() {
		if (end - start < THRESHOLD) {
			Arrays.sort(array, start, end + 1);
		} else {
			int pivot = partition(array, start, end);
			SortTask left = null;
			SortTask right = null;
			if (start < pivot - 1) {
				left = new SortTask(array, start, pivot - 1);
			}
			if (pivot + 1 < end) {
				right = new SortTask(array, pivot + 1, end);
			}
			if (left != null) {
				left.fork();
			}
			if (right != null) {
				right.fork();
			}
		}
	}

	private int partition(int[] array, int start, int end) {
		int i = start;
		int j = end;
		if (j - i > 2) {
			if ((array[i] < array[j - i] && array[j - i] < array[j])
					|| (array[j] < array[j - i] && array[j - i] < array[i])) {
				int t = array[i];
				array[i] = array[j - i];
				array[j - i] = t;
			} else {
				if ((array[i] < array[j] && array[j] < array[j - i])
						|| (array[j - i] < array[j] && array[j] < array[i])) {
					int t = array[i];
					array[i] = array[j];
					array[j] = t;
				}
			}
		}
		int pivot = array[i];
		while (i < j) {
			while (i < j && array[j] > pivot) {
				j--;
			}
			if (i < j) {
				array[i++] = array[j];
			}
			while (i < j && array[i] < pivot) {
				i++;
			}
			if (i < j) {
				array[j--] = array[i];
			}
		}
		array[i] = pivot;
		return i;
	}
}