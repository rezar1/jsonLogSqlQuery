package com.extensions.logmonitor.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.extensions.logmonitor.util.JsonLogObj.Address;
import com.extensions.logmonitor.util.JsonLogObj.UserInfo;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class LogDataCreator {

	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	static Date start = null;
	static Date end = null;
	static {
		try {
			start = format.parse("2015-12-12 12:12:12 001");
			end = format.parse("2045-12-12 12:12:12 001");
		} catch (Exception ex) {

		}
	}

	public static char[] chars = "abcdefghijkmnlopqrstuvwxyz1234567890*&^%$#@!_+".toCharArray();
	public static String[] citys = "beijing,shagnhai,shenzheng,dongjing,nanjing,jiujiang,nanchang,chengdou".split(",");
	public static String[] ps = "jiangxi,hainna,xizang,suzhou,guangdong,henan".split(",");
	public static String[] ss = "".split(",");
	public static String[] ip = "192,145,344,234,197,123,232,098,145,225,123,003,004,008,112,170".split(",");

	public static void main(String[] args) throws IOException {
		String fileName = "";
		fileName = "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles/test.log";
		createLogFiles(fileName);
	}

	public static Object createLogObj() {
		JsonLogObj jlo = new JsonLogObj();
		jlo.setLogEventType("testJson");
		jlo.setIp(ip[RandomUtils.nextInt(ip.length)] + "." + ip[RandomUtils.nextInt(ip.length)] + "."
				+ ip[RandomUtils.nextInt(ip.length)] + "." + ip[RandomUtils.nextInt(ip.length)]);
		UserInfo userInfo = new UserInfo();
		userInfo.setAdslotId(random(10000l, 99999999l));
		userInfo.setAppId(RandomStringUtils.random(23, chars));
		userInfo.setComments(Arrays.asList("hehe", "hahaha", "xixixi"));
		jlo.setUserInfo(userInfo);
		UserInfo subUserInfo = null;
		List<UserInfo> subUserInfos = new ArrayList<>();
		for (int i = 0; i < (Math.random() * 10); i++) {
			subUserInfo = new UserInfo();
			subUserInfo.setAdslotId(random(10000l, 99999999l));
			subUserInfo.setAppId(RandomStringUtils.random(23, chars));
			subUserInfo.setComments(Arrays.asList("hehe" + i, "hahaha" + i + 1, "xixixi" + (i + 5)));
			subUserInfos.add(subUserInfo);
		}
		jlo.setSubUserInfos(subUserInfos);
		Address address = new Address();
		address.setCity(citys[RandomUtils.nextInt(citys.length)]);
		address.setProv(ps[RandomUtils.nextInt(ps.length)]);
		address.setStree(RandomUtils.nextBoolean() ? "UnKnow" : null);
		jlo.setAddress(address);
		jlo.setAge(RandomUtils.nextInt(100));
		jlo.setName("Rezar" + RandomUtils.nextInt(Integer.MAX_VALUE));
		jlo.setTime(randomTimeStr());
		jlo.setArrayInfos(Arrays.asList("abc", "def", "ghi"));
		return jlo;
	}

	/**
	 * @return
	 */
	private static String randomTimeStr() {
		try {
			long date = random(start.getTime(), end.getTime());
			return format.format(new Date(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {

		long rtn = begin + (long) (Math.random() * (end - begin));

		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

		if (rtn == begin || rtn == end) {

			return random(begin, end);

		}

		return rtn;
	}

	public static String createLogLines() {
		Object outputObj = createLogObj();
		return JacksonUtil.obj2Str(outputObj) + "\n";
	}

	public static void createLogFiles(ByteArrayOutputStream out, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			Object outputObj = createLogObj();
			out.write((JacksonUtil.obj2Str(outputObj) + "\n").getBytes());
		}
		out.flush();
		out.close();
	}

	public static void createLogFiles(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		PrintWriter pw = new PrintWriter(new FileWriter(file), true);
		for (int i = 0; i < 100000; i++) {
			Object outputObj = createLogObj();
			pw.write(JacksonUtil.obj2Str(outputObj) + "\n");
			pw.flush();
		}
		pw.close();
	}

}
