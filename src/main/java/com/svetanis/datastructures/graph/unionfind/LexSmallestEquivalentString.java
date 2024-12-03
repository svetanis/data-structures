package com.svetanis.datastructures.graph.unionfind;

// 1061. Lexicographically Smallest Equivalent String

public final class LexSmallestEquivalentString {
	// Time Complexity: O(n)

	public LexSmallestEquivalentString() {
		this.parent = init();
	}

	private int[] parent;

	public String smallestEquivalent(String s1, String s2, String base) {
		merge(s1, s2);
		StringBuilder sb = new StringBuilder();
		for (char c : base.toCharArray()) {
			int index = find(c - 'a');
			char sec = (char) (index + 'a');
			sb.append(sec);
		}
		return sb.toString();
	}

	private void merge(String s1, String s2) {
		for (int i = 0; i < s1.length(); i++) {
			int index1 = s1.charAt(i) - 'a';
			int index2 = s2.charAt(i) - 'a';
			int p1 = find(index1);
			int p2 = find(index2);
			if (p1 < p2) {
				parent[p2] = p1;
			} else {
				parent[p1] = p2;
			}
		}
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y;
		}
		return y;
	}

	private int[] init() {
		int[] parent = new int[26];
		for (int i = 0; i < 26; i++) {
			parent[i] = i;
		}
		return parent;
	}

	public static void main(String[] args) {
		LexSmallestEquivalentString ses1 = new LexSmallestEquivalentString();
		System.out.println(ses1.smallestEquivalent("parker", "morris", "parser")); // makkek
		LexSmallestEquivalentString ses2 = new LexSmallestEquivalentString();
		System.out.println(ses2.smallestEquivalent("hello", "world", "hold")); // hdld
		LexSmallestEquivalentString ses3 = new LexSmallestEquivalentString();
		System.out.println(ses3.smallestEquivalent("leetcode", "programs", "sourcecode")); // aauaaaaada
	}
}
