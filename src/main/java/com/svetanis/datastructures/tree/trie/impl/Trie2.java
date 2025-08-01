package com.svetanis.datastructures.tree.trie.impl;

// 208. Implement Trie (Prefix Tree)

public final class Trie2 {

	private Node root;

	public Trie2() {
		this.root = new Node();
	}

	public void insert(String word) {
		Node node = root;
		for (char letter : word.toCharArray()) {
			int index = letter - 'a';
			if (node.children[index] == null) {
				node.children[index] = new Node();
			}
			node = node.children[index];
		}
		node.isEndOfWord = true;
	}

	public boolean search(String word) {
		Node node = searchPrefix(word);
		return node != null && node.isEndOfWord;
	}

	public boolean startsWith(String prefix) {
		Node node = searchPrefix(prefix);
		return node != null;
	}

	private Node searchPrefix(String s) {
		Node node = root;
		for (char letter : s.toCharArray()) {
			int index = letter - 'a';
			if (node.children[index] == null) {
				return null;
			}
			node = node.children[index];
		}
		return node;
	}

	public static void main(String[] args) {
		Trie2 trie = new Trie2();
		trie.insert("apple");
		System.out.println(trie.search("apple")); // true
		System.out.println(trie.search("app")); // false
		System.out.println(trie.startsWith("app")); // true
		trie.insert("app");
		System.out.println(trie.search("app")); // true
	}

	private static class Node {
		private Node[] children;
		private boolean isEndOfWord;

		public Node() {
			this.isEndOfWord = false;
			this.children = new Node[26];
		}
	}
}