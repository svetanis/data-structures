package com.svetanis.datastructures.graph.unionfind;

// 765. Couples Holding Hands

public final class CouplesHoldingHands {

	private int[] parent;

	public int minSwapsCouples(int[] row) {
		int couples = row.length / 2;
		this.parent = init(couples);
		for (int i = 0; i < row.length - 1; i += 2) {
			union(row[i] / 2, row[i + 1] / 2);
		}
		return swaps(couples);
	}

	private int swaps(int couples) {
		int swaps = couples;
		for (int i = 0; i < couples; i++) {
			if (i == find(i)) {
				swaps--;
			}
		}
		return swaps;
	}

	private int[] init(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = i;
		}
		return a;
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y;
		}
		return y;
	}

	private void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		parent[rootP] = rootQ;
	}

	public static void main(String[] args) {
		CouplesHoldingHands chh = new CouplesHoldingHands();
		int[] row1 = { 0, 2, 1, 3 };
		System.out.println(chh.minSwapsCouples(row1)); // 1
		int[] row2 = { 3, 2, 0, 1 };
		System.out.println(chh.minSwapsCouples(row2)); // 0
	}
}
