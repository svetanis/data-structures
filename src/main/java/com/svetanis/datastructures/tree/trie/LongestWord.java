package com.svetanis.datastructures.tree.trie;

// 720. Longest Word in Dictionary

public final class LongestWord {

	private Node root;

	public LongestWord() {
		this.root = new Node();
	}

	public String longestWord(String[] words) {
		for (String word : words) {
			insert(word);
		}
		return dfs(root, "");
	}

	private String dfs(Node node, String path) {
		String s = path;
		for (Node child : node.children) {
			if (child != null && child.endOfWord) {
				String word = dfs(child, path + child.c);
				boolean one = word.length() > s.length();
				boolean two = word.length() == s.length();
				boolean cmp = word.compareTo(s) < 0;
				if (one || (two && cmp)) {
					s = word;
				}
			}
		}
		return s;
	}

	private void insert(String word) {
		Node node = root;
		for (char c : word.toCharArray()) {
			int index = c - 'a';
			if (node.children[index] == null) {
				node.children[index] = new Node();
				node.children[index].c = c;
			}
			node = node.children[index];
		}
		node.endOfWord = true;
	}

	public static void main(String[] args) {
		String[] w1 = { "w", "wo", "wor", "worl", "world" };
		LongestWord lw = new LongestWord();
		System.out.println(lw.longestWord(w1)); // world

		String[] w2 = { "a", "banana", "app", "appl", "ap", "apply", "apple" };
		LongestWord lw2 = new LongestWord();
		System.out.println(lw2.longestWord(w2)); // apple
	}

	private static class Node {
		private char c;
		private boolean endOfWord;
		private Node[] children;

		public Node() {
			this.children = new Node[26];
		}
	}
}
