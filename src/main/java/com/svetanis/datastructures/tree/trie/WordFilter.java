package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 745. Prefix and Suffix Search

public final class WordFilter {

	private Node ptr = new Node();
	private Node str = new Node();

	public WordFilter(String[] words) {
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			ptr.insert(word, i);
			String reversed = new StringBuilder(word).reverse().toString();
			str.insert(reversed, i);
		}
	}

	public int f(String prefix, String suffix) {
		suffix = new StringBuilder(suffix).reverse().toString();
		List<Integer> pindexes = ptr.search(prefix);
		List<Integer> sindexes = str.search(suffix);
		if (pindexes.isEmpty() || sindexes.isEmpty()) {
			return -1;
		}
		int i = pindexes.size() - 1;
		int j = sindexes.size() - 1;
		while (i >= 0 && j >= 0) {
			int pindex = pindexes.get(i);
			int sindex = sindexes.get(j);
			if (pindex == sindex) {
				return pindex;
			}
			if (pindex > sindex) {
				i--;
			} else {
				j--;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		String[] w1 = { "apple" };
		WordFilter wf = new WordFilter(w1);
		System.out.println(wf.f("a", "e")); // 0
	}

	private static class Node {
		private Node[] children;
		private List<Integer> indexes;

		public Node() {
			this.children = new Node[26];
			this.indexes = new ArrayList<>();
		}

		public void insert(String word, int index) {
			Node node = this;
			for (char c : word.toCharArray()) {
				int i = c - 'a';
				if (node.children[i] == null) {
					node.children[i] = new Node();
				}
				node = node.children[i];
				node.indexes.add(index);
			}
		}

		public List<Integer> search(String prefix) {
			Node node = this;
			for (char c : prefix.toCharArray()) {
				int index = c - 'a';
				if (node.children[index] == null) {
					return Collections.emptyList();
				}
				node = node.children[index];
			}
			return node.indexes;
		}
	}
}
