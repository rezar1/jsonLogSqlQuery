package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月23日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class StringRegexConvert implements ValueConvert {

	private Pattern pattern;
	private int group;

	@Override
	public Object convert(Object value) {
		String ret = value.toString();
		Matcher matcher = pattern.matcher(ret);
		boolean matches = matcher.matches();
		if (matches) {
			return matcher.group(group);
		} else {
			return ret;
		}
	}

	public void setRegexStr(String regexStr) {
		pattern = Pattern.compile(regexStr);
	}

	public void setGroupId(int groupId) {
		this.group = groupId;
	}

	public static void main(String[] args) {
		StringRegexConvert convert = new StringRegexConvert();
		convert.setGroupId(1);
		convert.setRegexStr(".*市(.*区).*");
		String text = "北京市海淀区西二旗";
		Object convert2 = convert.convert(text);
		System.out.println("after convert:" + convert2);
	}

}
