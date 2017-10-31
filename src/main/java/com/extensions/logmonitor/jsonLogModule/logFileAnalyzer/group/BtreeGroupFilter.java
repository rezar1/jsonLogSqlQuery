package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import org.javastack.kvstore.KVStoreFactory;
import org.javastack.kvstore.Options;
import org.javastack.kvstore.holders.LongHolder;
import org.javastack.kvstore.structures.btree.BplusTree.TreeEntry;
import org.javastack.kvstore.structures.btree.BplusTreeFile;

import com.extensions.logmonitor.config.CommonConfig;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月19日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BtreeGroupFilter implements GroupFilter {

	KVStoreFactory<LongHolder, GroupIdContact> factory = new KVStoreFactory<>(LongHolder.class, GroupIdContact.class);
	BplusTreeFile<LongHolder, GroupIdContact> tree = null; //

	public BtreeGroupFilter() {
		try {
			String templateFileName = UUID.randomUUID().toString().replaceAll("_", "") + ".data";
			File file = new File(CommonConfig.tempFilePath, templateFileName);
			file.deleteOnExit();
			Options opts = factory.createTreeOptionsDefault().set(KVStoreFactory.FILENAME, file.getAbsolutePath());
			this.tree = factory.createTreeFile(opts);
			this.tree.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initGroupId(long groupId, GroupIdContact groupIdContact) {
		this.tree.put(LongHolder.valueOf(groupId), groupIdContact);
	}

	@Override
	public void havingResult(long groupId, boolean havingResult) {
		GroupIdContact groupIdContact = this.tree.get(LongHolder.valueOf(groupId));
		if (groupIdContact != null) {
			groupIdContact.setNeedRemove(havingResult);
		}
	}

	@Override
	public GroupIdContact findGroupIdContact(long groupId) {
		return this.tree.get(LongHolder.valueOf(groupId));
	}

	@Override
	public boolean filter(long groupId) {
		GroupIdContact groupIdContact = this.tree.get(LongHolder.valueOf(groupId));
		return groupIdContact == null || groupIdContact.isNeedRemove();
	}

	public Iterator<GroupIdContact> iterator() {
		return new FilterIterator();
	}

	private class FilterIterator implements Iterator<GroupIdContact> {
		private Iterator<TreeEntry<LongHolder, GroupIdContact>> treeIterator;

		public FilterIterator() {
			this.treeIterator = tree.iterator();
		}

		@Override
		public boolean hasNext() {
			return treeIterator.hasNext();
		}

		@Override
		public GroupIdContact next() {
			TreeEntry<LongHolder, GroupIdContact> next = treeIterator.next();
			GroupIdContact value = next.getValue();
			return value.setGroupId(next.getKey().longValue());
		}
	}

}
