package com.svetanis.datastructures.tree.trie.autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// 642. Design Search Autocomplete System

public final class AutocompleteSystem {

	private Trie root = new Trie();
	private StringBuilder sb = new StringBuilder();

	public AutocompleteSystem(String[] sentences, int[] times) {
		for (int i = 0; i < sentences.length; i++) {
			root.insert(sentences[i], times[i]);
		}
	}

	public List<String> autocomplete(char inputChar) {
		List<String> list = new ArrayList<>();
		if (inputChar == '#') {
			root.insert(sb.toString(), 1);
			sb = new StringBuilder();
			return list;
		}
		sb.append(inputChar);
		Trie node = root.search(sb.toString());
		if (node == null) {
			return list;
		}
		PriorityQueue<Trie> pq = new PriorityQueue<>(
				(a, b) -> a.freq == b.freq ? 
						b.word.compareTo(a.word) : a.freq - b.freq);
		dfs(node, pq);
		while (!pq.isEmpty()) {
			list.add(0, pq.poll().word);
		}
		return list;
	}

	private void dfs(Trie node, PriorityQueue<Trie> pq) {
		if (node == null) {
			return;
		}
		if (node.freq > 0) {
			pq.offer(node);
			if (pq.size() > 3) {
				pq.poll();
			}
		}
		for (Trie child : node.children) {
			dfs(child, pq);
		}
	}

	public static void main(String[] args) {
		int[] times = { 5, 3, 2, 1 };
		String[] words = { "i love you", "island", "i love leetcode", "ironman" };
		AutocompleteSystem acs = new AutocompleteSystem(words, times);
		System.out.println(acs.autocomplete('i')); // i love you, island, i love leetcode
	}

	public static final class Trie {

		private int freq;
		private String word;
		private Trie[] children;

		public Trie() {
			this.freq = 0;
			this.word = "";
			this.children = new Trie[27];
		}

		public void insert(String s, int frequency) {
			Trie node = this;
			for (char c : s.toCharArray()) {
				int index = c == ' ' ? 26 : c - 'a';
				if (node.children[index] == null) {
					node.children[index] = new Trie();
				}
				node = node.children[index];
			}
			node.freq += frequency;
			node.word = s;
		}

		public Trie search(String prefix) {
			Trie node = this;
			for (char c : prefix.toCharArray()) {
				int index = c == ' ' ? 26 : c - 'a';
				if (node.children[index] == null) {
					return null;
				}
				node = node.children[index];
			}
			return node;
		}
	}
}
