package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;

import java.util.Iterator;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月19日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface GroupFilter {

	public void initGroupId(long groupId, GroupIdContact groupIdContact);

	public void havingResult(long groupId, boolean havingResult);

	public GroupIdContact findGroupIdContact(long groupId);

	public boolean filter(long groupId);

	public Iterator<GroupIdContact> iterator();

}
