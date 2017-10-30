package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月28日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class NeedParsePathMatcher {

	private Map<String, Boolean> matchResult = new HashMap<>();
	private Map<String, Boolean> fullMatchResult = new HashMap<>();
	private String path;
	private List<String> partOfPaths = new ArrayList<>();
	private String doMoreParsePath;
	private String regexPattern;
	private Pattern fullMatchPattern;

	public NeedParsePathMatcher(String path) {
		this.path = path;
		this.doMoreParsePath = processDoMorePath(path);
		this.partOfPaths = processPartOfPaths(this.doMoreParsePath);
		this.regexPattern = this.fullProcessPath(path);
		this.fullMatchPattern = Pattern.compile(regexPattern);
	}

	/**
	 * @param doMoreParsePath2
	 * @return
	 */
	private List<String> processPartOfPaths(String doMoreParsePath) {
		return Arrays.asList(doMoreParsePath.split("\\."));
	}

	/**
	 * @param path2
	 * @return
	 */
	private String processDoMorePath(String path) {
		return path.replaceAll("\\[(\\*|\\d+|#)\\]", ".$1");
	}

	/**
	 * @param path
	 * @return
	 * 
	 */
	private String fullProcessPath(String path) {
		path = "^" + path;
		path = path.replaceAll("\\[", "\\\\[");
		path = path.replaceAll("\\]", "\\\\]");
		path = path.replaceAll("\\.", "\\\\.");
		path = path.replaceAll("\\*", "(\\\\d+|.*)");
		return path;
	}

	public boolean fullMatch(String path) {
		boolean result = false;
		if (this.fullMatchResult.containsKey(path)) {
			result = this.fullMatchResult.get(path);
		} else {
			result = fullMatchPattern.matcher(path).matches();
			this.fullMatchResult.put(path, result);
		}
		return result;
	}

	public boolean match(String path) {
		boolean isMatchPrefix = true;
		if (this.matchResult.containsKey(path)) {
			isMatchPrefix = this.matchResult.get(path);
		} else {
			List<String> processPartOfPaths = this.processPartOfPaths(this.processDoMorePath(path));
			int index = 0;
			for (;index < processPartOfPaths.size() && index < this.partOfPaths.size() ;index ++) {
				String partOfPath = processPartOfPaths.get(index);
				if (!(partOfPath.equals(this.partOfPaths.get(index))
						|| (StringUtils.isNumeric(partOfPath) && this.partOfPaths.get(index).equals("*")))) {
					isMatchPrefix = false;
				}
			}
			this.matchResult.put(path, isMatchPrefix);
		}
		return isMatchPrefix;
	}

	public boolean needMoreParse(String path) {
		return this.doMoreParsePath.length() > path.length();
	}

	@Override
	public String toString() {
		return "NeedParsePathMatcher [path=" + path + ", doMoreParsePath=" + doMoreParsePath + ", regexPattern="
				+ regexPattern + "]";
	}

	public static void main(String[] args) {
		NeedParsePathMatcher matcher = new NeedParsePathMatcher("subUserInfos[1].adslotId");
		System.out.println(matcher);
		boolean match = matcher.match("subUserInfos[1]");
		System.out.println(match);
		boolean fullMatch = matcher.fullMatch("subUserInfos[1].adslotId");
		System.out.println(fullMatch);
		int times = 100000000;
		long time1 = 0, time2 = 0;
		time1 = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			matcher.match("app.info[*]");
		}
		time2 = System.currentTimeMillis();
		System.out.println("use time" + (time2 - time1));
	}

}
