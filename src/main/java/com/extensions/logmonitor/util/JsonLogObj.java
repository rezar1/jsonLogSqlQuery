package com.extensions.logmonitor.util;

import lombok.Data;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public class JsonLogObj {

	private String ip;
	private String name;
	private long age;
	private String LogEventType;
	private Address address;
	private UserInfo userInfo;
	private String time;

	@Data
	public static class UserInfo {
		private String appId;
		private Long adslotId;
	}

	@Data
	public static class Address {
		private String stree;
		private String city;
		private String prov;
	}

}
