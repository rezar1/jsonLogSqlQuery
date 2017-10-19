package com.extensions.logmonitor.util;

import java.util.regex.Pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@Slf4j
public class SqlLikePattern {

	private String matchPattern;
	private Pattern pattern;

	public SqlLikePattern(String originPatternStr) {
		this.matchPattern = originPatternStr.replaceAll("([^\\\\]*)%(.*)", "$1.*$2")
				.replaceAll("([^\\\\]+)_(.*)", "$1.?$2").replace("\\%", "%").replace("\\_", "_");
		log.debug("originPatternStr:{} and regexPattern:{}", originPatternStr, this.matchPattern);
		this.pattern = Pattern.compile(this.matchPattern);
	}

	public boolean match(String value) {
		return this.pattern.matcher(value).matches();
	}

	public static void main(String[] args) {
		String ts = "H%_H";
		System.out.println(ts.replaceAll("(.*?)((?:[^\\\\]?)[%_])(?:(?:[^\\\\%_])[%_])+(.*)", "$1$3"));
		String str = "%\\__Rezar\\%";
		System.out.println("" + str.replaceAll("([^\\\\]*)((?:[^\\\\]?)[%_])(?:(?:[^\\\\]?)[%_])+(.*)", "$1$2$3"));
		boolean result = new SqlLikePattern(str).match("f_Rezar%");
		System.out.println(result);
	}

}
