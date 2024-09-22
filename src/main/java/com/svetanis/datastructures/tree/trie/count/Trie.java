package com.svetanis.datastructures.tree.trie.count;

import java.util.Map;

public final class Trie {

	private Node root;

	public Trie() {
		this.root = new Node();
		root.freq += 1;
	}

	public void insert(String str) {
		insert(root, str, 0);
	}

	private void insert(Node node, String str, int index) {
		node.freq += 1;
		if (index == str.length()) {
			return;
		}
		char c = str.charAt(index);
		Map<Character, Node> children = node.children;
		if (!children.containsKey(c)) {
			children.put(c, new Node(c));
		}
		node = children.get(c);
		insert(node, str, index + 1);
	}

	public int query(String prefix) {
		return query(root, prefix, 0);
	}

	private int query(Node node, String s, int index) {
		if (node == null) {
			return 0;
		}
		if (index == s.length() || node.freq == 1) {
			return 0;
		}
		Map<Character, Node> children = node.children;
		node = children.get(s.charAt(index));
		return 1 + query(node, s, index + 1);
	}

	public Node getRoot() {
		return root;
	}
}
