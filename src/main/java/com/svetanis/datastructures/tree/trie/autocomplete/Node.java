package com.svetanis.datastructures.tree.trie.autocomplete;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class Node {

	protected int freq;
	protected char key;
	protected Map<Character, Node> children;

	public Node() {
		this('$');
	}

	public Node(char letter) {
		this.freq = 0;
		this.key = letter;
		this.children = newHashMap();
	}

	@Override
	public String toString() {
		return Character.toString(key);
	}
}