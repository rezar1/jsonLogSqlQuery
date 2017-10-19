package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
public enum OrderType {

	ASC(1), DESC(-1);

	public int orderType;

	private OrderType(int orderType) {
		this.orderType = orderType;
	}

	public int checkoutResult(int result, OrderType orderType) {
		if (orderType == OrderType.ASC) {
			if (result < 0) {
				// 当前值比待比较值小,skip
			} else if (result > 0) {
				// 当前值比待比较值大
				return -1;
			}
		} else if (orderType == OrderType.DESC) {
			if (result < 0) {
				return 1;
				// 当前值比待比较值小,skip
			} else if (result > 0) {
				// 当前值比待比较值大
				return -1;
			}
		}
		return 0;
	}

}
