package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.sortExecute;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderTypeComparator;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月12日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MinHeap<T> {

	private T[] data;
	private int size;

	private Comparator<T> comparator;

	public MinHeap(T[] data, Comparator<T> comparator) {
		this.data = data;
		this.size = data.length;
		this.comparator = comparator;
	}

	public T takeMin() {
		return data[0];
	}

	public void createHeap() {
		for (int i = (size) / 2 - 1; i >= 0; i--) {
			heapIfy(i);
		}
	}

	public void heapIfy(int value) {
		int lchild = left(value);
		int rchild = right(value);
		int smallest = value;
		if (lchild < size && comparator.compare(data[lchild], data[value]) < 0)
			smallest = lchild;
		if (rchild < size && comparator.compare(data[rchild], data[smallest]) < 0)
			smallest = rchild;
		if (value == smallest)
			return;
		swap(value, smallest);
		heapIfy(smallest);
	}

	public int left(int value) {
		return ((value + 1) << 1) - 1;
	}

	public int right(int value) {
		return (value + 1) << 1;
	}

	public void swap(int i, int j) {
		T tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	public void reHeap() {
		if (size >= 0) {
			this.swap(0, --size);
			this.heapIfy(0);
		}
	}

	public void setRoot(T value) {
		data[0] = value;
		heapIfy(0);
	}

	public static void main(String[] args) {
		List<OrderType> orderTypes = Arrays.asList(OrderType.DESC, OrderType.DESC, OrderType.ASC);
		OrderTypeComparator orderTypeComparator = new OrderTypeComparator(orderTypes);
		OrderByDataItemWithObj[] datas = new OrderByDataItemWithObj[100];
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			OrderByDataItemWithObj item = new OrderByDataItemWithObj();
			item.initValueSize(3);
			item.setRecordId(i);
			item.addCacheData(0, random.nextInt(1000) + "_index:" + i);
			item.addCacheData(1, random.nextInt(1000) * 1000);
			item.addCacheData(2, random.nextInt(100000) % 2 == 0 ? true : false);
			datas[i] = item;
		}
		MinHeap<OrderByDataItemWithObj> heap = new MinHeap<OrderByDataItemWithObj>(datas, orderTypeComparator);
		heap.createHeap();
		for (int i = 0; i < datas.length; i++) {
			System.out.println(heap.data[i] + " ");
		}
		System.out.println();
		heap.setRoot(datas[88]);
		for (int i = 0; i < datas.length; i++) {
			System.out.println(heap.data[i] + " ");
		}
		System.out.println();
	}

}
