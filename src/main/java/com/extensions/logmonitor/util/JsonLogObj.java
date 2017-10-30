package com.extensions.logmonitor.util;

import java.util.List;

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
	private String logEventType;
	private Address address;
	private UserInfo userInfo;
	private List<UserInfo> subUserInfos;
	private String time;
	private List<String> arrayInfos;

	@Data
	public static class UserInfo {
		private String appId;
		private Long adslotId;
		private List<String> comments;
	}

	@Data
	public static class Address {
		private String stree;
		private String city;
		private String prov;
	}

}
