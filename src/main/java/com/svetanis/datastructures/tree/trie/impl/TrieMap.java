package com.svetanis.datastructures.tree.trie.impl;

import java.util.HashMap;
import java.util.Map;

// 208. Implement Trie (Prefix Tree)

public final class TrieMap {

	private Node root;

	public TrieMap() {
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
		Node node = searchPrefix(word);
		return node != null && node.isEndOfWord;
	}

	public boolean startsWith(String prefix) {
		return searchPrefix(prefix) != null;
	}

	private Node searchPrefix(String s) {
		Node node = root;
		for (char letter : s.toCharArray()) {
			node = node.children.get(letter);
			if (node == null) {
				return null;
			}
		}
		return node;
	}

	public void delete(String word) {
		dfs(root, word, 0);
	}

	private boolean dfs(Node node, String word, int index) {
		if (index == word.length()) {
			node.isEndOfWord = false;
			return node.children.isEmpty();
		}

		char c = word.charAt(index);
		Node child = node.children.get(c);
		if (child == null) {
			return false;
		}
		boolean shouldDeleteChild = dfs(child, word, index + 1);
		if (shouldDeleteChild) {
			node.children.remove(c);
		}
		return !node.isEndOfWord && node.children.isEmpty();
	}

	public static void main(String[] args) {
		searchDemo();
		deleteDemo();
	}

	private static void searchDemo() {
		TrieMap trie = new TrieMap();
		trie.insert("apple");
		System.out.println(trie.search("apple")); // true
		System.out.println(trie.search("app")); // false
		System.out.println(trie.startsWith("app")); // true
		trie.insert("app");
		System.out.println(trie.search("app")); // true
	}

	private static void deleteDemo() {
		TrieMap trie = new TrieMap();
		trie.insert("apple");
		trie.insert("app");
		trie.insert("banana");

		// "app" ends inside "apple", so the prune must stop at the shared prefix
		trie.delete("app");
		System.out.println(trie.search("app")); // false
		System.out.println(trie.search("apple")); // true
		System.out.println(trie.startsWith("app")); // true

		// nothing shares banana's nodes, so every one of them goes
		trie.delete("banana");
		System.out.println(trie.startsWith("ban")); // false

		// a word that was never inserted must leave the trie untouched
		trie.delete("apples");
		System.out.println(trie.search("apple")); // true
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