package com.svetanis.datastructures.graph.unionfind;

// 1559. Detect Cycles in 2D Grid
//
// Input: char[][] grid of lowercase letters, `rows` by `cols` (the code calls them n and m). On
// LeetCode: 1 <= rows, cols <= 500.
// Returns true when some cycle exists: a walk of length 4 or more through cells that all hold the
// same letter, moving up, down, left or right, that comes back to its start cell without using a
// cell twice (apart from the start). On a grid no cycle is shorter than 4 anyway.
//
// The one idea: one cell is one node; two touching cells with the same letter get a link between
// them. Union-find keeps the groups of linked cells (one root per group). Look at each cell and
// only its right and lower neighbour, so every link is seen once. If the two cells of a link are
// ALREADY in the same group, a different route between them exists, and that route plus this
// link is a cycle. Otherwise the link joins two groups and we go on.
//
// Time: O(rows * cols * log(rows * cols)) -- at most two links per cell, each a pair of finds and
// at most one union; paths are compressed but trees are not balanced, so a find is O(log) amortised.
// Space: O(rows * cols) -- the parent array.

public final class DetectCyclesIn2D {

	private int[] parent; // CARDS: parent[i * cols + j] -- the cell above cell (i, j) in its group
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
					if (valid(n, m, x, y) && grid[i][j] == grid[x][y]) { // LINK: same letter, touching
						int pcurr = find(i * m + j);
						int pnext = find(x * m + y);
						if (pcurr == pnext) {
							return true; // CYCLE: already joined by another route
						}
						// union operation
						parent[pnext] = pcurr; // JOIN: the two groups become one
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
			parent[x] = find(parent[x]); // COMPRESS
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
