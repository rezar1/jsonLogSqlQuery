package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;

import java.nio.ByteBuffer;

import org.javastack.kvstore.holders.DataHolder;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月19日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class GroupIdContact extends DataHolder<GroupIdContact> implements Comparable<GroupIdContact> {

	private long recordId;
	private boolean needRemove;

	private long groupId;

	public GroupIdContact(long recordId) {
		this.recordId = recordId;
	}

	public GroupIdContact() {
	}

	/**
	 * @return the groupId
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 * @return
	 */
	public GroupIdContact setGroupId(long groupId) {
		this.groupId = groupId;
		return this;
	}

	/**
	 * @return the recordId
	 */
	public long getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the needRemove
	 */
	public boolean isNeedRemove() {
		return needRemove;
	}

	/**
	 * @param needRemove
	 *            the needRemove to set
	 */
	public void setNeedRemove(boolean needRemove) {
		this.needRemove = needRemove;
	}

	@Override
	public int compareTo(GroupIdContact o) {
		return (int) (this.recordId - o.getRecordId());
	}

	@Override
	public String toString() {
		return String.format("recordId:%s\tneedRemove:%s", this.recordId, this.needRemove);
	}

	@Override
	public int byteLength() {
		return 9;
	}

	@Override
	public void serialize(ByteBuffer buf) {
		buf.putLong(recordId);
		buf.put(this.needRemove ? (byte) 1 : (byte) -1);
	}

	@Override
	public GroupIdContact deserialize(ByteBuffer buf) {
		Long recordId = buf.getLong();
		boolean needRemove = buf.get() == 1 ? true : false;
		GroupIdContact ret = new GroupIdContact(recordId);
		ret.setNeedRemove(needRemove);
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (needRemove ? 1231 : 1237);
		result = prime * result + (int) (recordId ^ (recordId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupIdContact other = (GroupIdContact) obj;
		if (needRemove != other.needRemove)
			return false;
		if (recordId != other.recordId)
			return false;
		return true;
	}

}
