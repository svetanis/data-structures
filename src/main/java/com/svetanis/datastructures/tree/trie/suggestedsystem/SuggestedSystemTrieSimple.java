package com.svetanis.datastructures.tree.trie.suggestedsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggested System

public final class SuggestedSystemTrieSimple {

	public List<List<String>> suggestedProducts(String[] products, String word) {
		Arrays.sort(products);
		Trie trie = init(products);
		List<List<String>> suggestions = new ArrayList<>();
		for (int i = 0; i < word.length(); i++) {
			String prefix = word.substring(0, i + 1);
			suggestions.add(trie.search(prefix));
		}
		return suggestions;
	}

	private Trie init(String[] products) {
		Trie trie = new Trie();
		for (String product : products) {
			trie.insert(product);
		}
		return trie;
	}

	public static void main(String[] args) {
		String[] products1 = { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
		SuggestedSystemTrieSimple wd = new SuggestedSystemTrieSimple();
		Print.print(wd.suggestedProducts(products1, "mouse"));

		String[] products2 = { "havana" };
		SuggestedSystemTrieSimple wd2 = new SuggestedSystemTrieSimple();
		Print.print(wd2.suggestedProducts(products2, "havana"));
	}

	private static class Trie {
		private Trie[] children = new Trie[26];
		private List<String> suggestions = new ArrayList<>();

		public void insert(String word) {
			Trie node = this;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					node.children[index] = new Trie();
				}
				node = node.children[index];
				if (node.suggestions.size() < 3) {
					node.suggestions.add(word);
				}
			}
		}

		public List<String> search(String word) {
			Trie node = this;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					return new ArrayList<>();
				}
				node = node.children[index];
			}
			return node.suggestions;
		}
	}
}