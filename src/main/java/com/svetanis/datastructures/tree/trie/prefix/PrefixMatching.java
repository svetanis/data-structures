package com.svetanis.datastructures.tree.trie.prefix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PrefixMatching {

	private TrieNode root;

	public void createTrie(String[] words) {
		this.root = new TrieNode();
		for (String word : words) {
			insert(word);
		}
	}

	public void insert(String word) {
		TrieNode node = root;
		for (char c : word.toCharArray()) {
			if (!node.children.containsKey(c)) {
				node.children.put(c, new TrieNode());
			}
			node = node.children.get(c);
		}
		node.isEndOfWord = true;
	}

	public List<String> prefix(String word) {
		TrieNode node = root;
		for (char c : word.toCharArray()) {
			if (!node.children.containsKey(c)) {
				return new ArrayList<>();
			}
			node = node.children.get(c);
		}
		List<String> list = new ArrayList<>();
		dfs(node, word, list);
		return list;
	}

	private void dfs(TrieNode node, String word, List<String> list) {
		if (node.isEndOfWord) {
			list.add(word);
		}
		for (char c : node.children.keySet()) {
			TrieNode child = node.children.get(c);
			dfs(child, word + c, list);
		}
	}

	public List<String> trie(String[] words, String prefix) {
		createTrie(words);
		return this.prefix(prefix);
	}

	public static void main(String[] args) {
		PrefixMatching pm = new PrefixMatching();
		String[] words1 = { "apple", "app", "apartment", "ap", "apricot" };
		System.out.println(pm.trie(words1, "app"));
	}

	private static class TrieNode {
		private boolean isEndOfWord;
		private Map<Character, TrieNode> children;

		public TrieNode() {
			this.children = new HashMap<>();
		}
	}
}
