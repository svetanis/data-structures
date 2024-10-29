package com.svetanis.datastructures.tree.trie.prefix;

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

	public int prefixQuery(String prefix) {
		return prefixQuery(prefix, 0);
	}

	private int prefixQuery(String s, int index) {
		if (index == s.length()) {
			return freq;
		}
		char c = s.charAt(index);
		if (children.containsKey(c)) {
			TrieNode node = children.get(c);
			return node.prefixQuery(s, index + 1);
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return Character.toString(key);
	}
}