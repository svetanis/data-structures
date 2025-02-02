package com.svetanis.datastructures.tree.trie;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 140. Word Break II

public final class WordBreak140 {

	private TrieNode root = new TrieNode();

	public List<String> wordBreak(String s, List<String> dict) {
		for (String word : dict) {
			root.insert(word);
		}
		List<List<String>> lists = dfs(s);
		return lists.stream().map(words -> String.join(" ", words)).collect(Collectors.toList());
	}

	private List<List<String>> dfs(String s) {
		List<List<String>> lists = new ArrayList<>();
		if ("".equals(s)) {
			lists.add(new ArrayList<>());
			return lists;
		}
		for (int i = 1; i <= s.length(); i++) {
			String ss = s.substring(0, i);
			if (root.search(ss)) {
				for (List<String> list : dfs(s.substring(i))) {
					list.add(0, ss);
					lists.add(list);
				}
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		WordBreak140 wb1 = new WordBreak140();
		List<String> list = asList("cat", "cats", "and", "sand", "dog");
		System.out.println(wb1.wordBreak("catsanddog", list));

		WordBreak140 wb2 = new WordBreak140();
		List<String> list1 = asList("apple", "pen", "applepen", "pine", "pineapple");
		System.out.println(wb2.wordBreak("pineapplepenapple", list1));

		WordBreak140 wb3 = new WordBreak140();
		List<String> list2 = asList("cats", "dog", "sand", "and", "cat");
		System.out.println(wb3.wordBreak("catsandog", list2));
	}

	private static class TrieNode {
		private TrieNode[] children = new TrieNode[26];
		private boolean endOfWord;

		public void insert(String word) {
			TrieNode node = this;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					node.children[index] = new TrieNode();
				}
				node = node.children[index];
			}
			node.endOfWord = true;
		}

		public boolean search(String word) {
			TrieNode node = this;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					return false;
				}
				node = node.children[index];
			}
			return node.endOfWord;
		}
	}
}
