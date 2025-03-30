package com.svetanis.datastructures.graph.unionfind;

// 1559. Detect Cycles in 2D Grid

public final class DetectCyclesIn2D {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private int[] parent;
	// horizontal + vertical moves
	private static int[] dx = { 0, 1 };
	private static int[] dy = { 1, 0 };

	public boolean containsCycles(char[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		init(n * m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// only check right and down directions
				for (int k = 0; k < 2; k++) {
					int x = i + dx[k];
					int y = j + dy[k];
					if (valid(n, m, x, y) && grid[i][j] == grid[x][y]) {
						int pcurr = find(i * m + j);
						int pnext = find(x * m + y);
						if (pcurr == pnext) {
							return true;
						}
						// union operation
						parent[pnext] = pcurr;
					}
				}
			}
		}
		return false;
	}

	private boolean valid(int n, int m, int x, int y) {
		boolean one = x >= 0 && x < n;
		boolean two = y >= 0 && y < m;
		return one && two;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	private void init(int n) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	public static void main(String[] args) {
		DetectCyclesIn2D mli = new DetectCyclesIn2D();
		char[][] g1 = { { 'a', 'a', 'a', 'a' }, { 'a', 'b', 'b', 'a' }, { 'a', 'b', 'b', 'a' }, { 'a', 'a', 'a', 'a' } };
		System.out.println(mli.containsCycles(g1)); // true

		char[][] g2 = { { 'c', 'c', 'c', 'a' }, { 'c', 'd', 'c', 'c' }, { 'c', 'c', 'e', 'c' }, { 'f', 'c', 'c', 'c' } };
		System.out.println(mli.containsCycles(g2)); // true

		char[][] g3 = { { 'a', 'b', 'b' }, { 'b', 'z', 'b' }, { 'b', 'b', 'a' } };
		System.out.println(mli.containsCycles(g3)); // false
	}
}
