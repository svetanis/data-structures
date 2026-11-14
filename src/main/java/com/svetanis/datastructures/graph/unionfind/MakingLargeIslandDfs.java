package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 827. Making A Large Island
//
// Input: grid -- an n x n grid of 0 (water) and 1 (land); an island is a group of land cells
// joined up, down, left or right. Returns the size of the largest island there can be after
// changing at most one 0 into a 1. On LeetCode: n == grid.length == grid[i].length,
// 1 <= n <= 500, grid[i][j] is 0 or 1. The grid is overwritten with island numbers.
//
// The one idea: walk each island once with a depth-first search, writing the island's number
// (2, 3, 4, ...) into every cell of it and remembering its size in a map number -> size. A
// number bigger than 1 in a cell now says which island the cell belongs to. Then each water cell
// is tried as land: itself (1 cell) plus the size of every DIFFERENT island number among its
// four neighbours -- a set of numbers stops one island being counted twice. If there is no
// water cell at all, the whole grid is one island.
//
// The three 827 files: MakingLargeIsland and MakingLargeIslandSubmit keep the islands in a
// union-find; this one labels the grid itself and needs no union-find.
//
// dfs2 is dfs written as a loop over the direction arrays; nothing calls it.
//
// Time: O(n^2) -- each cell is walked once by a search and looked at four times by a water cell.
// Space: O(n^2) -- the recursion is as deep as the biggest island is large, up to n^2 frames:
// a 500 x 500 grid of all land overflows the default Java stack. The map holds one entry per
// island.

public final class MakingLargeIslandDfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public int largestIsland(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		int max = 0;
		Map<Integer, Integer> map = sizes(grid); // LABEL: every island gets a number and a size
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (grid[row][col] == 0) {
					Set<Integer> visited = new HashSet<>(); // numbers already added for this cell
					int size = 1; // the flipped cell itself
					for (int k = 0; k < 4; k++) {
						int x = row + dx[k];
						int y = col + dy[k];
						if (valid(grid, x, y) && grid[x][y] > 1) {
							int nid = grid[x][y];
							if (!visited.contains(nid)) {
								size += map.get(nid); // ADD: this island, once
								visited.add(nid);
							}
						}
					}
					max = Math.max(max, size);
				}
			}
		}
		// max stays 0 only when there is no water cell at all, and then
		// the whole grid is one island
		return max == 0 ? rows * cols : max;
	}

	private Map<Integer, Integer> sizes(int[][] grid) {
		int rows = grid.length;
		int cols = grid[0].length;
		Map<Integer, Integer> map = new HashMap<>();
		int index = 2; // 0 and 1 already mean water and unvisited land
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (grid[row][col] == 1) {
					int size = dfs(grid, row, col, index);
					map.put(index, size); // RECORD: island number -> its size
					index++;
				}
			}
		}
		return map;
	}

	private int dfs(int[][] grid, int row, int col, int index) {
		if (!valid(grid, row, col) || grid[row][col] != 1) {
			return 0; // BASE: off the grid, water, or already numbered
		}
		grid[row][col] = index; // MARK: this cell belongs to island `index`
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
		// the row count bounds x and the COLUMN count bounds y; the two are
		// equal only while the grid is square, which is what LC 827 promises
		boolean one = x >= 0 && x < grid.length;
		boolean two = y >= 0 && y < grid[0].length;
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

		// wider than it is tall: the column count must bound y
		int[][] g4 = { { 1, 1, 1 }, { 1, 1, 1 } };
		System.out.println(mli.largestIsland(g4)); // 6

		int[][] g5 = { { 1, 1 }, { 1, 0 }, { 1, 1 }, { 1, 1 } };
		System.out.println(mli.largestIsland(g5)); // 8
	}
}
