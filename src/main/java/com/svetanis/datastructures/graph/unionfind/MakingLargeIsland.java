package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// 827. Making A Large Island

public final class MakingLargeIsland {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n^2)

	private int[] parent;
	private int[] sizes;
	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public int largestIsland(int[][] grid) {
		int n = grid.length;
		int size = n * n;
		init(size);
		int max = union(grid, 1);
		return maxSize(grid, max);
	}

	private int maxSize(int[][] grid, int max) {
		int n = grid.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					continue;
				}
				// process only water cells
				int size = 1;
				Set<Integer> visited = new HashSet<>();
				for (int k = 0; k < dx.length; k++) {
					int x = i + dx[k];
					int y = j + dy[k];
					if (!valid(grid, x, y)) {
						continue;
					}
					int root = find(x * n + y);
					if (!visited.contains(root)) {
						visited.add(root);
						size += sizes[root];
					}
				}
				max = Math.max(max, size);
			}
		}
		return max;
	}

	private int union(int[][] grid, int max) {
		int n = grid.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
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
					int cp = find(i * n + j);
					// root of neighbor cell
					int np = find(x * n + y);
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
		int n = grid.length;
		boolean one = x >= 0 && x < n;
		boolean two = y >= 0 && y < n;
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
		MakingLargeIsland mli = new MakingLargeIsland();
		int[][] g1 = { { 1, 0 }, { 0, 1 } };
		System.out.println(mli.largestIsland(g1)); // 3

		int[][] g2 = { { 1, 1 }, { 1, 0 } };
		System.out.println(mli.largestIsland(g2)); // 4

		int[][] g3 = { { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g3)); // 4
	}
}
