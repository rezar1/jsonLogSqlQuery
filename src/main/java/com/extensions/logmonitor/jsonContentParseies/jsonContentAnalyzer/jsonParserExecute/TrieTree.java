package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月24日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class TrieTree {

	final int MAX_SIZE = 26;

	public class TrieTreeNode {
		int nCount;// 记录该字符出现次数
		char ch; // 记录该字符
		TrieTreeNode[] child;

		public TrieTreeNode() {
			nCount = 1;
			child = new TrieTreeNode[MAX_SIZE];
		}

	}

	// 字典树的插入和构建
	public void createTrie(TrieTreeNode node, String str) {
		if (str == null || str.length() == 0) {
			return;
		}
		char[] letters = str.toCharArray();
		for (int i = 0; i < letters.length; i++) {
			int pos = letters[i] - 'a';
			if (node.child[pos] == null) {
				node.child[pos] = new TrieTreeNode();
			} else {
				node.child[pos].nCount++;
			}
			node.ch = letters[i];
			node = node.child[pos];
		}
	}

	// 字典树的查找
	public int findCount(TrieTreeNode node, String str) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		char[] letters = str.toCharArray();
		for (int i = 0; i < letters.length; i++) {
			int pos = letters[i] - 'a';
			if (node.child[pos] == null) {
				return 0;
			} else {
				node = node.child[pos];
			}
		}
		return node.nCount;
	}

}
