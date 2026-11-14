package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 952. Largest Component Size by Common Factor
//
// Input: int[] a of distinct positive numbers. On LeetCode: 1 <= a.length <= 2 * 10^4,
// 1 <= a[i] <= 10^5. Two numbers are linked when they share a factor bigger than 1; a
// "component" is a group of numbers joined by chains of such links.
// Returns the size of the largest component.
//
// The one idea: do not compare numbers with each other (that is n^2 pairs). Give every whole
// number from 0 to the largest value its own node, and for each number a[i] link it to every
// factor of its own (each f from 2 up to sqrt(a[i]) that divides it, and also a[i] / f). Two
// numbers that share a factor now reach that factor's node, so they land in the same group
// without ever being compared. Then count, for each group, how many of the INPUT numbers it
// holds; the factor nodes themselves are not counted. The number 1 has no factor above 1, so it
// stays alone.
//
// Time: O(n * sqrt(M)) plus the union-find steps -- M is the largest value; each number tries
// factors up to its square root, and each divisor found costs two unions (paths are compressed
// but trees are not balanced, so a step is O(log M) amortised).
// Space: O(M) -- the parent and size arrays over 0..M.

public final class LargestComponentSize {

	private int[] size; // COUNT: size[root] -- how many input numbers the group holds
	private int[] parent; // CARDS: parent[v] -- the number above v in its group

	public int lcs(int[] a) {
		int max = Arrays.stream(a).max().getAsInt();
		init(max + 1); // one node for every value 0..max
		merge(a);
		return count(a);
	}

	private int count(int[] a) {
		int max = 0;
		for (int num : a) {
			int root = find(num); // GROUP of this number
			size[root]++; // COUNT: only input numbers are added
			max = Math.max(max, size[root]);
		}
		return max;
	}

	private void merge(int[] a) {
		for (int num : a) {
			for (int factor = 2; factor <= Math.sqrt(num); factor++) {
				if (num % factor == 0) { // FACTOR: link the number to it and to its partner
					union(num, factor);
					union(num, num / factor);
				}
			}
		}
	}

	private void init(int n) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
		this.size = new int[n];
	}

	private void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px != py) {
			parent[px] = py; // JOIN
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // COMPRESS
		}
		return parent[x];
	}

	public static void main(String[] args) {
		LargestComponentSize lcs = new LargestComponentSize();
		int[] a1 = { 4, 6, 15, 35 };
		System.out.println(lcs.lcs(a1)); // 4

		int[] a2 = { 20, 50, 9, 63 };
		System.out.println(lcs.lcs(a2)); // 2

		int[] a3 = { 2, 3, 6, 7, 4, 12, 21, 39 };
		System.out.println(lcs.lcs(a3)); // 8
	}
}
