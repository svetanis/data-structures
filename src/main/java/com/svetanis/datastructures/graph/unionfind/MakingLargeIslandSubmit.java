package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 827. Making A Large Island
//
// Input: grid -- an n x n grid of 0 (water) and 1 (land); an island is a group of land cells
// joined up, down, left or right. Returns the size of the largest island there can be after
// changing at most one 0 into a 1. On LeetCode: n == grid.length == grid[i].length,
// 1 <= n <= 500, grid[i][j] is 0 or 1.
//
// The one idea: first join touching land cells into groups that remember their size, then try
// each WATER cell as land -- itself plus the size of every DIFFERENT group touching it. Nothing
// is flipped for real, so "at most one" needs no counter. One element is one cell, numbered
// row * cols + col; sizes[root] is the size of the group whose root it is.
//
// The three 827 files:
//   1. MakingLargeIsland       -- union-find on the shared DisjointSet, three methods
//   2. MakingLargeIslandSubmit -- the same, structure inlined, for pasting
//   3. MakingLargeIslandDfs    -- labels the islands in the grid itself, no union-find
// All three return the same answer. Rung 1 is the one to write.
//
// RUNG 2: the structure is inlined, so this pastes on its own.
// The set below holds island ROOTS, not visited cells. Union-find never walks, so
// nothing can be revisited; the set is there so that two neighbours sitting in the
// same island are not counted twice, and it is rebuilt for each water cell.
//
// Time: O(n^2 log n) -- each cell is joined with its neighbours once and looked up four times;
// find only compresses paths (the join does not pick the smaller tree), which is O(log n)
// amortised per find.
// Space: O(n^2) -- parent and sizes, one slot per cell.

public final class MakingLargeIslandSubmit {

	private int[] parent;
	private int[] sizes;
	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public int largestIsland(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		init(rows * cols);
		int max = union(grid, 1); // BUILD: join the land, keep the biggest island so far
		return maxSize(grid, max);
	}

	private int maxSize(int[][] grid, int max) {
		int rows = grid.length;
		int cols = grid[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] == 1) {
					continue;
				}
				// process only water cells
				int size = 1; // the flipped cell itself
				Set<Integer> islands = new HashSet<>();
				for (int k = 0; k < dx.length; k++) {
					int x = i + dx[k];
					int y = j + dy[k];
					if (!valid(grid, x, y)) {
						continue;
					}
					int root = find(x * cols + y);
					if (!islands.contains(root)) {
						islands.add(root);
						size += sizes[root]; // ADD: this island, once
					}
				}
				max = Math.max(max, size);
			}
		}
		return max;
	}

	private int union(int[][] grid, int max) {
		int rows = grid.length;
		int cols = grid[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] == 0) {
					continue;
				}
				// process only land cells
				for (int k = 0; k < dx.length; k++) {
					int x = i + dx[k];
					int y = j + dy[k];
					if (!valid(grid, x, y)) {
						continue;
					}
					// root of current cell
					int cp = find(i * cols + j);
					// root of neighbor cell
					int np = find(x * cols + y);
					// if neighbors belong to different
					// sets perform union operation
					if (cp != np) {
						parent[np] = cp; // JOIN: neighbour's island under this one
						sizes[cp] += sizes[np];
						max = Math.max(max, sizes[cp]);
					}
				}
			}
		}
		return max;
	}

	private boolean valid(int[][] grid, int x, int y) {
		// the row count bounds x and the COLUMN count bounds y; the two are
		// equal only while the grid is square, which is what LC 827 promises
		boolean one = x >= 0 && x < grid.length;
		boolean two = y >= 0 && y < grid[0].length;
		return one && two && grid[x][y] == 1;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, then COMPRESS on the way back
		}
		return parent[x];
	}

	private void init(int n) {
		sizes = new int[n];
		Arrays.fill(sizes, 1);
		parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}

	public static void main(String[] args) {
		MakingLargeIslandSubmit mli = new MakingLargeIslandSubmit();
		int[][] g1 = { { 1, 0 }, { 0, 1 } };
		System.out.println(mli.largestIsland(g1)); // 3

		int[][] g2 = { { 1, 1 }, { 1, 0 } };
		System.out.println(mli.largestIsland(g2)); // 4

		int[][] g3 = { { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g3)); // 4

		// wider than it is tall: the column count must bound y
		int[][] g4 = { { 1, 1, 1 }, { 1, 1, 1 } };
		System.out.println(mli.largestIsland(g4)); // 6

		int[][] g5 = { { 1, 1 }, { 1, 0 }, { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g5)); // 8
	}
}
