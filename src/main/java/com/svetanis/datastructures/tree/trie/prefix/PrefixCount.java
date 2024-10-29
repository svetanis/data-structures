package com.svetanis.datastructures.tree.trie.prefix;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// Given a dictionary, containing a list of words, 
// and a list of queries, which consists of a list of prefixes, 
// compute the result of each query. Each query is simply a string 
// denoting a prefix. For each query, return the number of words in 
// the dictionary that contain that prefix.

// There may be duplicate words in the dictionary. 
// In a query, you should account for all the duplicate words.

public final class PrefixCount {
	// Time Complexity: O(c)
	// Space Complexity: O(c)
	// c - total number of chars in the input

	public static List<Integer> prefixCount(List<String> words, List<String> prefixes) {
		TrieNode trie = build(words);
		return count(trie, prefixes);
	}

	private static List<Integer> count(TrieNode trie, List<String> prefixes) {
		List<Integer> list = new ArrayList<>();
		for (String prefix : prefixes) {
			list.add(trie.prefixQuery(prefix));
		}
		return list;
	}

	private static TrieNode build(List<String> words) {
		TrieNode trie = new TrieNode();
		for (String word : words) {
			trie.insert(word);
		}
		return trie;
	}

	public static void main(String[] args) {
		List<String> words = asList("forgo", "for", "trie", "while", "loop", "stack", "deque", "kruskal", "deck", "decks",
				"logic", "math", "computer", "science", "compute", "scrap", "logos", "loom");
		List<String> prefixes = asList("l", "zeb", "for", "trie", "log", "logi", "sc", "st");
		System.out.println(prefixCount(words, prefixes)); // 4 0 2 1 2 1 2 1
	}
}
