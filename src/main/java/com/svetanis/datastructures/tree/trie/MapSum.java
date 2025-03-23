package com.svetanis.datastructures.tree.trie;

import java.util.HashMap;
import java.util.Map;

// 677. Map Sum Pairs

public final class MapSum {

	private Node root;
	private Map<String, Integer> map;

	public MapSum() {
		this.root = new Node();
		this.map = new HashMap<>();
	}

	public void insert(String key, int val) {
		int delta = val - map.getOrDefault(key, 0);
		map.put(key, val);
		root.insert(key, delta);
	}

	public int sum(String prefix) {
		return root.search(prefix);
	}

	public static void main(String[] args) {
		MapSum ms = new MapSum();
		ms.insert("apple", 3);
		System.out.println(ms.sum("ap")); // 3
		ms.insert("app", 2);
		System.out.println(ms.sum("ap")); // 5
	}

	private static class Node {
		private int val;
		private Node[] children;

		public Node() {
			this.children = new Node[26];
		}

		public void insert(String word, int val) {
			Node node = this;
			for (int i = 0; i < word.length(); i++) {
				int index = word.charAt(i) - 'a';
				if (node.children[index] == null) {
					node.children[index] = new Node();
				}
				node = node.children[index];
				node.val += val;
			}
		}

		public int search(String word) {
			Node node = this;
			for (int i = 0; i < word.length(); i++) {
				int index = word.charAt(i) - 'a';
				if (node.children[index] == null) {
					return 0;
				}
				node = node.children[index];
			}
			return node.val;
		}
	}
}
