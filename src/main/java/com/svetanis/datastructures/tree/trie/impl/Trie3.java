package com.svetanis.datastructures.tree.trie.impl;

import java.util.HashMap;
import java.util.Map;

// 208. Implement Trie (Prefix Tree)

public final class Trie3 {

	private Node root;

	public Trie3() {
		this.root = new Node();
	}

	public void insert(String word) {
		Node node = root;
		for (char letter : word.toCharArray()) {
			node.children.putIfAbsent(letter, new Node());
			node = node.children.get(letter);
		}
		node.isEndOfWord = true;
	}

	public boolean search(String word) {
		Node node = root;
		for (char letter : word.toCharArray()) {
			node = node.children.get(letter);
			if (node == null) {
				return false;
			}
		}
		return node.isEndOfWord;
	}

	public boolean startsWith(String prefix) {
		Node node = root;
		for (char letter : prefix.toCharArray()) {
			node = node.children.get(letter);
			if (node == null) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Trie3 trie = new Trie3();
		trie.insert("apple");
		System.out.println(trie.search("apple")); // true
		System.out.println(trie.search("app")); // false
		System.out.println(trie.startsWith("app")); // true
		trie.insert("app");
		System.out.println(trie.search("app")); // true
	}

	private static class Node {
		private Map<Character, Node> children;
		private boolean isEndOfWord;

		public Node() {
			this.isEndOfWord = false;
			this.children = new HashMap<>();
		}
	}
}