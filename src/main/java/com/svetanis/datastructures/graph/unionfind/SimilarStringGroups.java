package com.svetanis.datastructures.graph.unionfind;

// 839. Similar String Groups
//
// Input: strs -- words that are all anagrams of each other. Two words are SIMILAR when they are
// equal or when swapping two letters of one gives the other. Similar words, directly or through
// other words, form a group. Returns the number of groups. On LeetCode: 1 <= strs.length <= 300,
// 1 <= strs[i].length <= 300, lowercase letters, all words the same length and anagrams of each
// other.
//
// The one idea: the words are the nodes and "similar" is a wire between two words; the answer is the
// number of groups of wired words. One element is one word (its index). Compare every pair, join the
// similar ones, then count the roots: an index that is its own parent is the head of one group.
// Whether two words are similar is decided by the places where they differ: none, or exactly two,
// and in those two places each word must hold the other's letter.
//
// Time: O(n^2 * L) -- n^2 / 2 pairs, each compared letter by letter over L letters; the finds are
// near-constant after compression.
// Space: O(n) -- the parent array.

public final class SimilarStringGroups {

	private int[] parent;

	public int similarGroups(String[] a) {
		this.parent = init(a.length);
		merge(a); // JOIN: every similar pair
		return countGroups(); // RETURN: one per group
	}

	private int countGroups() {
		int count = 0;
		for (int i = 0; i < parent.length; i++) {
			if (i == find(i)) { // a root is the head of one group
				count++;
			}
		}
		return count;
	}

	private boolean isSimilar(String s1, String s2) {
		// counting the differences is not enough. one swap moves exactly
		// two positions AND crosses them, so the two places where the
		// strings disagree must hold each other's characters. "ab" and
		// "cd" differ in two places and no swap of "ab" produces "cd"
		int first = -1;
		int second = -1;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				continue; // SKIP: the letters agree here
			}
			if (first == -1) {
				first = i; // RECORD: the first place they differ
			} else if (second == -1) {
				second = i; // RECORD: the second place
			} else {
				return false; // a third difference: no single swap fixes it
			}
		}
		if (first == -1) {
			return true; // equal words
		}
		if (second == -1) {
			// one position apart. impossible for the anagrams LC 839 promises
			return false;
		}
		return s1.charAt(first) == s2.charAt(second) // CROSSED: each holds the other's letter
				&& s1.charAt(second) == s2.charAt(first);
	}

	private void merge(String[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (isSimilar(a[i], a[j])) {
					int p1 = find(i);
					int p2 = find(j);
					parent[p1] = p2; // JOIN the two groups
				}
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

	private int[] init(int n) {
		int[] parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
		return parent;
	}

	public static void main(String[] args) {
		SimilarStringGroups ssg = new SimilarStringGroups();
		String[] a1 = { "tars", "rats", "arts", "star" };
		System.out.println(ssg.similarGroups(a1)); // 2 -- {tars, rats, arts} and {star}
		SimilarStringGroups ssg2 = new SimilarStringGroups();
		String[] a2 = { "omv", "ovm" };
		System.out.println(ssg2.similarGroups(a2)); // 1 -- one swap

		// one position apart, so no single swap turns either into the
		// other. these are not anagrams, which is why LC 839 never shows them
		String[] a3 = { "bb", "ba" };
		System.out.println(new SimilarStringGroups().similarGroups(a3)); // 2 -- two groups

		// two positions apart but not crossed -- no swap of "ab" gives
		// "cd", so the letters at the two places must also be each other's
		String[] a4 = { "ab", "cd" };
		System.out.println(new SimilarStringGroups().similarGroups(a4)); // 2 -- two groups

		String[] a5 = { "ab", "ba" };
		System.out.println(new SimilarStringGroups().similarGroups(a5)); // 1 -- crossed, one swap
	}
}
