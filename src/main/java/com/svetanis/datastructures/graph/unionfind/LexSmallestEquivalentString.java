package com.svetanis.datastructures.graph.unionfind;

// 1061. Lexicographically Smallest Equivalent String
//
// Input: s1 and s2 -- two strings of the same length; s1[i] and s2[i] are equivalent letters, and
// equivalence is symmetric and passes along (a ~ b and b ~ c make a ~ c); baseStr -- a string.
// Returns baseStr with every letter replaced by the smallest letter equivalent to it. On
// LeetCode: 1 <= s1.length, s2.length, baseStr.length <= 1000, s1.length == s2.length, all
// lowercase English letters.
//
// The one idea: the 26 letters are the nodes and each pair s1[i], s2[i] is a wire between two
// letters; letters joined by wires, directly or through others, form one group, and the answer
// for a letter is the smallest letter of its group. Union-find keeps one root per group, so the
// merge is made to hang the LARGER root under the SMALLER one: then the root of every group is
// its smallest letter, and find(letter) is the answer, with no search for a minimum at the end.
// parent[x] holds the letter x currently hangs under; a letter that hangs under itself is a root.
//
// Time: O(n + m) -- n wires with two finds each, m letters of baseStr with one find each; a find
// walks at most 26 links, a constant.
// Space: O(1) -- the 26-slot parent array; the answer string is the output.

public final class LexSmallestEquivalentString {

	private int[] parent;

	public String smallestEquivalent(String s1, String s2, String base) {
		this.parent = init(); // RESET: every letter alone, so one instance can answer many cases
		merge(s1, s2);
		StringBuilder sb = new StringBuilder();
		for (char c : base.toCharArray()) {
			int index = find(c - 'a'); // ROOT: the smallest letter of c's group
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
			if (p1 < p2) { // JOIN: the smaller root stays the root
				parent[p2] = p1;
			} else {
				parent[p1] = p2;
			}
		}
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y); // WALK up to the root
			parent[x] = y; // COMPRESS: point straight at it
		}
		return y;
	}

	private int[] init() {
		int[] parent = new int[26];
		for (int i = 0; i < 26; i++) {
			parent[i] = i; // BASE: a root of its own
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

		// the same instance again: the second case must not see the first one's wires
		System.out.println(ses3.smallestEquivalent("hello", "world", "hold")); // hdld
	}
}
