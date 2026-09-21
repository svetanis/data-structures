package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 827. Making A Large Island
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

public final class MakingLargeIslandSubmit {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n^2)

	private int[] parent;
	private int[] sizes;
	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public int largestIsland(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		init(rows * cols);
		int max = union(grid, 1);
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
				int size = 1;
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
						size += sizes[root];
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
						parent[np] = cp;
						sizes[cp] += sizes[np];
						max = Math.max(max, sizes[cp]);
					}
				}
			}
		}
		return max;
	}

	private boolean valid(int[][] grid, int x, int y) {
		// the row count bounds x and the COLUMN count bounds y. one
		// length used for both is right only while the grid is square,
		// which is what LC 827 promises and nothing else does
		boolean one = x >= 0 && x < grid.length;
		boolean two = y >= 0 && y < grid[0].length;
		return one && two && grid[x][y] == 1;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
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

		// wider than it is tall. with grid.length standing in for the
		// column count this printed 4, and a taller-than-wide grid threw
		int[][] g4 = { { 1, 1, 1 }, { 1, 1, 1 } };
		System.out.println(mli.largestIsland(g4)); // 6

		int[][] g5 = { { 1, 1 }, { 1, 0 }, { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g5)); // 8
	}
}
