package com.svetanis.datastructures.tree.trie.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 472. Concatenated Words

public final class ConcatenatedWordsSubmit {

	public static List<String> concat(String[] words) {
		Arrays.sort(words, (a, b) -> a.length() - b.length());
		List<String> list = new ArrayList<>();
		Trie root = new Trie();
		for (String word : words) {
			if (dfs(root, word)) {
				list.add(word);
			} else {
				root.insert(word);
			}
		}
		return list;
	}

	private static boolean dfs(Trie root, String word) {
		if (word.isEmpty()) {
			return true;
		}
		Trie node = root;
		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - 'a';
			if (node.children[index] == null) {
				return false;
			}
			node = node.children[index];
			String substr = word.substring(i + 1);
			if (node.isLeaf() && dfs(root, substr)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String[] a = { "cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat" };
		String[] a2 = { "cat", "dog", "catdog" };
		System.out.println(concat(a)); // catsdogcats, dogcatsdog, ratcatdogcat
		System.out.println(concat(a2)); // catdog
	}

	public static final class Trie {

		private boolean leaf;
		private Trie[] children = new Trie[26];

		public void insert(String str) {
			Trie node = this;
			for (char c : str.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					node.children[index] = new Trie();
				}
				node = node.children[index];
			}
			node.leaf = true;
		}

		public boolean isLeaf() {
			return leaf;
		}
	}
}
