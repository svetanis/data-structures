package com.svetanis.datastructures.tree.trie.count;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class TrieNode {

	protected int freq;
	protected char key;
	protected Map<Character, TrieNode> children;

	public TrieNode() {
		this('$');
	}

	public TrieNode(char letter) {
		this.freq = 0;
		this.key = letter;
		this.children = newHashMap();
	}

	public void insert(String str) {
		insert(str, 0);
	}

	private void insert(String str, int index) {
		freq += 1;
		if (index == str.length()) {
			return;
		}
		char c = str.charAt(index);
		if (!children.containsKey(c)) {
			children.put(c, new TrieNode(c));
		}
		TrieNode node = children.get(c);
		node.insert(str, index + 1);
	}

	public int query(String prefix) {
		return query(prefix, 0);
	}

	private int query(String s, int index) {
		if (index == s.length() || freq == 1) {
			return 0;
		}
		TrieNode node = children.get(s.charAt(index));
		return 1 + node.query(s, index + 1);
	}

	@Override
	public String toString() {
		return Character.toString(key);
	}
}