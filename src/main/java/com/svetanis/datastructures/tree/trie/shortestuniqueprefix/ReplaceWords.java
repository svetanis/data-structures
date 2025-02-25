package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import java.util.Arrays;
import java.util.List;

// 648. Replace Words

public final class ReplaceWords {

	public static String replace(String sentence, List<String> list) {
		Trie trie = build(list);
		String[] words = sentence.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			int index = trie.search(word);
			String s = index == -1 ? word : list.get(index);
			sb.append(" " + s);
		}
		return sb.toString().trim();
	}

	private static Trie build(List<String> words) {
		Trie trie = new Trie();
		for (int i = 0; i < words.size(); i++) {
			trie.insert(words.get(i), i);
		}
		return trie;
	}

	public static void main(String[] args) {
		List<String> words1 = Arrays.asList("cat", "bat", "rat");
		String s1 = "the cattle was rattled by the battery";
		System.out.println(replace(s1, words1));

		List<String> words2 = Arrays.asList("a", "b", "c");
		String s2 = "aadsfasf absbs bbab cadsfafs";
		System.out.println(replace(s2, words2));
	}

	private static class Trie {
		private int index = -1;
		private Trie[] children = new Trie[26];

		public void insert(String word, int index) {
			Trie root = this;
			for (char c : word.toCharArray()) {
				int i = c - 'a';
				if (root.children[i] == null) {
					root.children[i] = new Trie();
				}
				root = root.children[i];
			}
			root.index = index;
		}

		public int search(String word) {
			Trie root = this;
			for (char c : word.toCharArray()) {
				int i = c - 'a';
				if (root.children[i] == null) {
					return -1;
				}
				root = root.children[i];
				if (root.index != -1) {
					return root.index;
				}
			}
			return -1;
		}
	}
}
