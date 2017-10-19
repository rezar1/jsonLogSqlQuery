package com.extensions.logmonitor;

import java.util.regex.Pattern;

/**
 * @author Satish Muddam
 */
public class SearchPattern {

    private String displayName;
    private Pattern pattern;
    private Boolean caseSensitive;


    public SearchPattern(String displayName, Pattern pattern, Boolean caseSensitive) {
        this.displayName = displayName;
        this.pattern = pattern;
        this.caseSensitive = caseSensitive;

    }

    public String getDisplayName() {
        return displayName;
    }
    public Boolean getCaseSensitive() {
        return caseSensitive;
    }
    public Pattern getPattern() {
        return pattern;
    }
}
