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
		int diff = 0;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				diff++;
			}
		}
		return diff <= 2;
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
	}
}
