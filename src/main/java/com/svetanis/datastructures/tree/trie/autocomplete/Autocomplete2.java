package com.svetanis.datastructures.tree.trie.autocomplete;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

// given a series of n words
// for every word, add it to
// the dictionary, and then 
// type out the word using the
// minimum number of strokes 
// to auto-complete the word.
// find the minimum total number
// of strokes needed to type
// out all the words.

public final class Autocomplete2 {
	// Time Complexity: O(c)
	// Space Complexity: O(c)
	// c - total number of chars in the input

	public static int autocomplete(List<String> words) {
		TrieNode trie = new TrieNode();
		int total = 0;
		for (String word : words) {
			trie.insert(word);
			total += trie.query(word);
		}
		return total + 1;
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

	public static final class TrieNode {

		protected int freq;
		protected char key;
		protected Map<Character, TrieNode> children;

		public TrieNode() {
			this('$');
		}

		public TrieNode(char letter) {
			this.freq = 0;
			this.key = letter;
			this.children = newHashMap();
		}

		public void insert(String str) {
			insert(str, 0);
		}

		private void insert(String str, int index) {
			freq += 1;
			if (index == str.length()) {
				return;
			}
			char c = str.charAt(index);
			if (!children.containsKey(c)) {
				children.put(c, new TrieNode(c));
			}
			TrieNode node = children.get(c);
			node.insert(str, index + 1);
		}

		public int query(String s) {
			return query(s, 0);
		}

		private int query(String s, int index) {
			if (index == s.length() || freq == 1) {
				return 0;
			}
			TrieNode node = children.get(s.charAt(index));
			return 1 + node.query(s, index + 1);
		}

		@Override
		public String toString() {
			return Character.toString(key);
		}
	}
}
