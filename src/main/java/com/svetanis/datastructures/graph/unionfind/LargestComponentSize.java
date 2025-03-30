package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 952. Largest Component Size by Common Factor

public final class LargestComponentSize {

	private int[] size;
	private int[] parent;

	public int lcs(int[] a) {
		int max = Arrays.stream(a).max().getAsInt();
		init(max + 1);
		merge(a);
		return count(a);
	}

	private int count(int[] a) {
		int max = 0;
		for (int num : a) {
			int root = find(num);
			size[root]++;
			max = Math.max(max, size[root]);
		}
		return max;
	}

	private void merge(int[] a) {
		for (int num : a) {
			for (int factor = 2; factor <= Math.sqrt(num); factor++) {
				if (num % factor == 0) {
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
			parent[px] = py;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
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
