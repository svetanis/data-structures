package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 827. Making A Large Island

public final class MakingLargeIslandDfs {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n^2)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public int largestIsland(int[][] grid) {
		int n = grid.length;
		int max = 0;
		Map<Integer, Integer> map = sizes(grid);
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (grid[row][col] == 0) {
					Set<Integer> visited = new HashSet<>();
					int size = 1;
					for (int k = 0; k < 4; k++) {
						int x = row + dx[k];
						int y = col + dy[k];
						if (valid(grid, x, y) && grid[x][y] > 1) {
							int nid = grid[x][y];
							if (!visited.contains(nid)) {
								size += map.get(nid);
								visited.add(nid);
							}
						}
					}
					max = Math.max(max, size);
				}
			}
		}
		return max == 0 ? n * n : max;
	}

	private Map<Integer, Integer> sizes(int[][] grid) {
		int n = grid.length;
		Map<Integer, Integer> map = new HashMap<>();
		int index = 2;
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (grid[row][col] == 1) {
					int size = dfs(grid, row, col, index);
					map.put(index, size);
					index++;
				}
			}
		}
		return map;
	}

	private int dfs(int[][] grid, int row, int col, int index) {
		if (!valid(grid, row, col) || grid[row][col] != 1) {
			return 0;
		}
		grid[row][col] = index;
		int size = 1;
		size += dfs(grid, row + 1, col, index);
		size += dfs(grid, row - 1, col, index);
		size += dfs(grid, row, col + 1, index);
		size += dfs(grid, row, col - 1, index);
		return size;
	}

	private int dfs2(int[][] grid, int row, int col, int index) {
		if (!valid(grid, row, col) || grid[row][col] != 1) {
			return 0;
		}
		grid[row][col] = index;
		int size = 0;
		for (int k = 0; k < 4; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			size += dfs2(grid, x, y, index);
		}
		return size + 1;
	}

	private boolean valid(int[][] grid, int x, int y) {
		int n = grid.length;
		boolean one = x >= 0 && x < n;
		boolean two = y >= 0 && y < n;
		return one && two;
	}

	public static void main(String[] args) {
		MakingLargeIslandDfs mli = new MakingLargeIslandDfs();
		int[][] g1 = { { 1, 0 }, { 0, 1 } };
		System.out.println(mli.largestIsland(g1)); // 3

		int[][] g2 = { { 1, 1 }, { 1, 0 } };
		System.out.println(mli.largestIsland(g2)); // 4

		int[][] g3 = { { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g3)); // 4
	}
}
