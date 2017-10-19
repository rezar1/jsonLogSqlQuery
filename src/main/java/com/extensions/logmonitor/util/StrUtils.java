package com.extensions.logmonitor.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class StrUtils {

	public static String removeCommon(String text) {
		if (text.indexOf("\"") == 0 || text.indexOf("'") == 0) {
			text = text.substring(1, text.length()); // 去掉第一个 "
		}
		if (text.lastIndexOf("\"") == (text.length() - 1) || text.lastIndexOf("'") == (text.length() - 1)) {
			text = text.substring(0, text.length() - 1); // 去掉最后一个 "
		}
		return text;
	}

	public static String convert(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	public static String makeChecksum(String data) {
		if (data == null || data.equals("")) {
			return "";
		}
		int total = 0;
		int len = data.length();
		int num = 0;
		while (num < len) {
			String s = data.substring(num, num + 2);
			total += Integer.parseInt(s, 16);
			num = num + 2;
		}
		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		int mod = total % 256;
		String hex = Integer.toHexString(mod);
		len = hex.length();
		// 如果不够校验位的长度，补0,这里用的是两位校验
		if (len < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	@SuppressWarnings("unchecked")
	public static List<String> sumObjHexValues(List<Object> values) {
		if (GenericsUtils.isNullOrEmpty(values)) {
			return Collections.EMPTY_LIST;
		}
		List<String> hexValues = new ArrayList<>(values.size());
		for (Object value : values) {
			if (value instanceof Integer) {
				hexValues.add(Integer.toHexString((int) value));
			} else if (value instanceof Double) {
				hexValues.add(Double.toHexString((double) value));
			} else if (value instanceof String) {
				hexValues.add(makeChecksum(convert((String) value)));
			} else if (value == null) {
				hexValues.add("FFFF");
			} else {
				System.out.println("not support!!!");
			}
		}
		return hexValues;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// 测试数据占用字节数大小
		String str1 = "哈哈2";
		String str2 = "哈哈3";
		byte[] bytes = str1.getBytes("GBK");
		System.out.println(bytes.length);
		String str3 = new String(bytes, "GBK");
		System.out.println("str3:" + str3);
		String convert = convert(str1);
		System.out.println("makeChecksum:" + convert + " ---- >" + convert.getBytes().length);
		String makeChecksum = makeChecksum(convert);
		System.out.println("makeChecksum:" + makeChecksum);
		String convert2 = convert(str2);
		System.out.println("makeChecksum:" + convert2 + " ---- >" + convert2.getBytes().length);
		String makeChecksum2 = makeChecksum(convert2);
		System.out.println("makeChecksum:" + makeChecksum2);

		// List<Object> values = new ArrayList<>();
		// values.add("abcfdsafdsfds");
		// values.add(123);
		// values.add("g");
		// System.out.println(sumObjHexValues(values));
		// List<Object> values2 = new ArrayList<>();
		// values2.add("bbcfdsafdsfds");
		// values2.add(null);
		// values2.add("g");
		// System.out.println(sumObjHexValues(values2));
	}

}
