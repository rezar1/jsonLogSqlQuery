package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache;

import java.nio.ByteBuffer;
import java.util.List;

import com.extensions.logmonitor.config.CommonConfig;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderTypeComparator;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.sortExecute.ObjBinaryCacheWithBlockSize;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OutterFileOrderByDataCache implements SingleOrderByDataCache {

	private ObjBinaryCacheWithBlockSize<OrderByDataItemWithObj> objBinaryCacheWithBlockSize = null;
	private OrderTypeComparator orderTypeComparator = null;

	public OutterFileOrderByDataCache() {
		SeriAndDeser<OrderByDataItemWithObj> keySeriAndDeser = new SeriAndDeser<OrderByDataItemWithObj>() {
			@Override
			public void serialize(OrderByDataItemWithObj obj, ByteBuffer buff) {
				obj.serialize(buff);
			}

			@Override
			public OrderByDataItemWithObj deserialize(ByteBuffer buf) {
				return OrderByDataItemWithObj.deserialize(buf);
			}
		};
		this.orderTypeComparator = new OrderTypeComparator();
		this.objBinaryCacheWithBlockSize = new ObjBinaryCacheWithBlockSize<>(
				CommonConfig.tempFilePath.getAbsolutePath(), keySeriAndDeser, orderTypeComparator,
				OrderByDataItemWithObj.class);
	}

	@Override
	public void cacheRecord(OrderByDataItemWithObj cacheData) {
		this.objBinaryCacheWithBlockSize.cacheData(cacheData);
	}

	@Override
	public void executeSort() {
		// do noting
	}

	@Override
	public List<OrderByDataItemWithObj> getCacheRecord(int offset, int batchSize) {
		this.objBinaryCacheWithBlockSize.triggerOver();
		return this.objBinaryCacheWithBlockSize.doSort(offset, batchSize);
	}

	@Override
	public void setOrderTypes(List<OrderType> orderTypes) {
		if (this.orderTypeComparator != null) {
			this.orderTypeComparator.setOrderTypes(orderTypes);
		}
	}

}
