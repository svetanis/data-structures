package com.svetanis.datastructures.tree.trie.suggestedsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1268. Search Suggested System

// THE INCREMENTAL CURSOR.
// SuggestedSystemTrieSimple.java re-descends from the root
// for every prefix; this file is that file with the cursor
// held between prefixes, and nothing else changed.
// prefixes of length 1..L cost L node steps here and
// 1+2+...+L there. L=50 -> 50 steps vs 1,275.

// this is the ONLY thing that makes a trie beat
// sort-plus-binary-search: consecutive prefixes share a
// path. anywhere queries arrive as a stream of growing
// prefixes -- autocomplete, a typed query -- hold the node.

public final class SuggestedSystemTrieSimpleOptimized {
	// n products, m = longest product, L = word length
	// Time Complexity: O(n * m * log n) sort + O(n * m) build + O(L) query
	// Space Complexity: O(n * m)

	public List<List<String>> suggestedProducts(String[] products, String word) {
		Arrays.sort(products);
		Trie trie = init(products);
		return trie.search(word);
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
		SuggestedSystemTrieSimpleOptimized wd = new SuggestedSystemTrieSimpleOptimized();
		Print.print(wd.suggestedProducts(products1, "mouse"));

		String[] products2 = { "havana" };
		SuggestedSystemTrieSimpleOptimized wd2 = new SuggestedSystemTrieSimpleOptimized();
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

		public List<List<String>> search(String word) {
			List<List<String>> result = new ArrayList<>();
			Trie node = this;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				// the cursor stays where the previous prefix left it,
				// so each extra character costs ONE step rather than
				// a fresh descent from the root
				if (node != null) {
					node = node.children[index];
				}
				// once a prefix misses, every longer prefix misses too
				result.add(node == null ? new ArrayList<>() : node.suggestions);
			}
			return result;
		}
	}
}
