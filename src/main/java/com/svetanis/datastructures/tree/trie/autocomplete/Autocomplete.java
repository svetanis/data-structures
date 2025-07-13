package com.svetanis.datastructures.tree.trie.autocomplete;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

// given a series of n words
// for every word, add it to
// the dictionary, and then 
// type out the word using the
// minimum number of strokes 
// to auto-complete the word.
// find the minimum total number
// of strokes needed to type
// out all the words.

public final class Autocomplete {
	// Time Complexity: O(c)
	// Space Complexity: O(c)
	// c - total number of chars in the input

	public static int autocomplete(List<String> words) {
		Trie trie = new Trie();
		int total = 0;
		for (String word : words) {
			trie.insert(word);
			total += trie.query(word);
		}
		return total;
	}

	public static void main(String[] args) {
		List<String> list = newArrayList("hi", "hello", "bojack", "hills", "hill");
		System.out.println(autocomplete(list)); // 11

		List<String> list2 = newArrayList("a", "aa", "aaa", "aaaa", "aaaaa");
		System.out.println(autocomplete(list2)); // 15

		List<String> list3 = newArrayList("to", "be", "or", "not", "two", "bee");
		System.out.println(autocomplete(list3)); // 9

		List<String> list4 = newArrayList("aaaaa", "aaaa", "aaa", "aa", "a");
		System.out.println(autocomplete(list4)); // 11
	}
}
