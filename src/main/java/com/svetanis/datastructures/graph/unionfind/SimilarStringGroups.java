package com.svetanis.datastructures.graph.unionfind;

// 839. Similar String Groups

public final class SimilarStringGroups {
	// Time Complexity: O(n^2 * length)
	// Space Complexity: O(n)

	private int[] parent;

	public int similarGroups(String[] a) {
		this.parent = init(a.length);
		merge(a);
		return countGroups();
	}

	private int countGroups() {
		int count = 0;
		for (int i = 0; i < parent.length; i++) {
			if (i == find(i)) {
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
				continue;
			}
			if (first == -1) {
				first = i;
			} else if (second == -1) {
				second = i;
			} else {
				return false;
			}
		}
		if (first == -1) {
			return true;
		}
		if (second == -1) {
			// one position apart. impossible for the anagrams LC 839
			// promises, which is why the count-only test survived there
			return false;
		}
		return s1.charAt(first) == s2.charAt(second) //
				&& s1.charAt(second) == s2.charAt(first);
	}

	private void merge(String[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (isSimilar(a[i], a[j])) {
					int p1 = find(i);
					int p2 = find(j);
					parent[p1] = p2;
				}
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
		System.out.println(ssg.similarGroups(a1)); // 2
		SimilarStringGroups ssg2 = new SimilarStringGroups();
		String[] a2 = { "omv", "ovm" };
		System.out.println(ssg2.similarGroups(a2)); // 1

		// one position apart, so no single swap turns either into the
		// other. these are not anagrams, which is why LC 839 never
		// shows them -- diff <= 2 called them similar and printed 1
		String[] a3 = { "bb", "ba" };
		System.out.println(new SimilarStringGroups().similarGroups(a3)); // 2

		// two positions apart but not crossed -- no swap of "ab" gives
		// "cd". counting the differing positions cannot tell this pair
		// from "ab" and "ba", and the count-only test printed 1
		String[] a4 = { "ab", "cd" };
		System.out.println(new SimilarStringGroups().similarGroups(a4)); // 2

		String[] a5 = { "ab", "ba" };
		System.out.println(new SimilarStringGroups().similarGroups(a5)); // 1
	}
}
