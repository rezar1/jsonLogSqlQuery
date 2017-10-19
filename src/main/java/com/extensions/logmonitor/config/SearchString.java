package com.extensions.logmonitor.config;

/**
 * @author Satish Muddam
 */
public class SearchString {

	private String displayName;
	private String pattern;
	private Boolean matchExactString;
	private Boolean caseSensitive;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Boolean getMatchExactString() {
		return matchExactString;
	}

	public void setMatchExactString(Boolean matchExactString) {
		this.matchExactString = matchExactString;
	}

	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
}
