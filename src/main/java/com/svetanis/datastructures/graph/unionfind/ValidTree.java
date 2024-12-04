package com.svetanis.datastructures.graph.unionfind;

// 261. Graph Valid Tree

public final class ValidTree {
	// Time Complexity: O(n)

	private int[] parent;

	public boolean validTree(int n, int[][] edges) {
		this.parent = init(n);
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			// if both nodes have the same root
			// then there is a cycle, not a tree
			int pFrom = find(from);
			int pTo = find(to);
			if (pFrom == pTo) {
				return false;
			}
			parent[pFrom] = pTo;
			n--;
		}
		return n == 1;
	}

	public int find(int x) {
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
		ValidTree vt = new ValidTree();
		int[][] g = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, g)); // true
	}
}
