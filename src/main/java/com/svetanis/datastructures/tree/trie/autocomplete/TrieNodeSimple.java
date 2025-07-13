package com.svetanis.datastructures.tree.trie.autocomplete;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class TrieNodeSimple {

	protected int freq;
	protected Map<Character, TrieNodeSimple> children;

	public TrieNodeSimple() {
		this.freq = 0;
		this.children = newHashMap();
	}

	public void insert(String s) {
		TrieNodeSimple node = this;
		for (char c : s.toCharArray()) {
			node.children.putIfAbsent(c, new TrieNodeSimple());
			node = node.children.get(c);
			node.freq++;
		}
	}

	public int query(String prefix) {
		TrieNodeSimple node = this;
		for (char c : prefix.toCharArray()) {
			if (!node.children.containsKey(c)) {
				return 0;
			}
		}
		return node.freq;
	}
}