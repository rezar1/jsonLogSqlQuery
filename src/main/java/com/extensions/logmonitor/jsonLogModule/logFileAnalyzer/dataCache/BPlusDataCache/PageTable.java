package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月30日
 * @Desc 页表,指定某个叶节点对应的数据存储范围
 *
 */
public class PageTable {

	private final static String cache_info_format = "%s:%s"; // offset:length

	private Map<Integer, String> pageTableCache = new ConcurrentHashMap<>();
	private volatile long lastAllocateOffset = 0;
	private TreeMap<Integer, List<Long>> freePageIndexMap = new TreeMap<>();

	public TwoTuple<Long, Integer> getNodeCacheInfo(int nodeId) {
		return this.pageInfos(nodeId);
	}

	public long getNodeCacheInfo(int nodeId, int totalSize) {
		TwoTuple<Long, Integer> pageInfos = this.pageInfos(nodeId);
		if (pageInfos == null || pageInfos.second < totalSize) {
			TwoTuple<Long, Integer> searchFreeIndex = this.searchFreeIndexAndTake(totalSize);
			if (searchFreeIndex != null) {
				System.out.println(
						String.format("ready to reuse \t offset:%s blockSize:%s for nodeId:%s , except totalSize:%s",
								searchFreeIndex.first, searchFreeIndex.second, nodeId, totalSize));
				this.pageTableCache.put(nodeId, createCacheInfoStr(searchFreeIndex.first, searchFreeIndex.second));
				return searchFreeIndex.first;
			} else {
				return allocateNewCacheBlock(nodeId, totalSize);
			}
		} else {
			return pageInfos.first;
		}
	}

	/**
	 * @param nodeId
	 * @param totalSize
	 * @return
	 */
	private long allocateNewCacheBlock(int nodeId, int totalSize) {
		long firstLastAllocateOffset = this.lastAllocateOffset;
		this.lastAllocateOffset += totalSize;
		this.pageTableCache.put(nodeId, createCacheInfoStr(firstLastAllocateOffset, totalSize));
		return this.lastAllocateOffset;
	}

	/**
	 * @param cacheOffset
	 * @param totalSize
	 * @return
	 */
	private String createCacheInfoStr(long cacheOffset, int totalSize) {
		return String.format(cache_info_format, cacheOffset, totalSize);
	}

	private TwoTuple<Long, Integer> pageInfos(int nodeId) {
		String cacheInfo = this.pageTableCache.get(nodeId);
		if (cacheInfo != null && cacheInfo.length() != 0) {
			String[] cacheInfos = cacheInfo.split(":");
			String offsetStr = cacheInfos[0];
			String length = cacheInfos[1];
			return TupleUtil.tuple(Long.parseLong(offsetStr), Integer.parseInt(length));
		} else {
			return null;
		}
	}

	public TwoTuple<Long, Integer> freePage(int nodeId) {
		TwoTuple<Long, Integer> pageInfos = this.pageInfos(nodeId);
		if (pageInfos != null) {
			List<Long> list = this.freePageIndexMap.get(pageInfos.second);
			if (list == null) {
				list = new ArrayList<>();
				this.freePageIndexMap.put(pageInfos.second, list);
			}
			if (!list.contains(pageInfos.first)) {
				list.add(pageInfos.first);
			}
			this.pageTableCache.remove(nodeId);
		}
		return pageInfos;
	}

	private TwoTuple<Long, Integer> searchFreeIndexAndTake(int totalSize) {
		Entry<Integer, List<Long>> ceilingEntry = freePageIndexMap.ceilingEntry(totalSize);
		if (ceilingEntry != null) {
			List<Long> value = ceilingEntry.getValue();
			if (value.isEmpty()) {
				this.freePageIndexMap.remove(totalSize);
			}
			return TupleUtil.tuple(value.get(0), ceilingEntry.getKey());
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "PageTable [pageTableCache=" + pageTableCache + ", lastAllocateOffset=" + lastAllocateOffset
				+ ", freePageIndexMap=" + freePageIndexMap + "]";
	}

	public static void main(String[] args) {
		PageTable pageTable = new PageTable();
		int i = 0;
		// Test reuse
		long offset = pageTable.getNodeCacheInfo(0, 1000);
		System.out.println(
				String.format("ready getNode:%s and totalSize:%s , and allocate offset is:%s", i, 1000, offset));
		pageTable.freePage(i);
		offset = pageTable.getNodeCacheInfo(++i, 1000);
		System.out.println(
				String.format("ready getNode:%s and totalSize:%s , and allocate offset is:%s", i, 1000, offset));
		pageTable.freePage(i);
		for (; i < 5000; i++) {
			int totalSize = Math.abs(1 + (int) (Math.random() * 4096 * 2));
			long nodeCacheInfo = pageTable.getNodeCacheInfo(i, totalSize);
			System.out.println(String.format("ready getNode:%s and totalSize:%s , and allocate offset is:%s", i,
					totalSize, nodeCacheInfo));
			if ((Math.random() * 10) > 6.0) {
				TwoTuple<Long, Integer> freePage = pageTable.freePage(i);
				System.out.println(String.format("ready to free nodeId:%s , and offset:%s , blockSize:%s", i,
						freePage.first, freePage.second));
			}
		}
		System.out.println(pageTable);
	}

}
