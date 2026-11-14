package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.List;

// 305. Number of Islands II
//
// Input: m and n -- the rows and columns of a grid that starts as all water; positions -- cells
// [row, col] turned into land one after another. An island is a group of land cells joined up, down,
// left or right. Returns a list with the number of islands after each position is added. On
// LeetCode: 1 <= m, n, positions.length <= 10^4, 1 <= m * n <= 10^4, 0 <= row < m, 0 <= col < n;
// the same cell may appear more than once.
//
// The one idea: a union-find keeps the islands between additions, so nothing is re-walked. One
// element is one cell, numbered row * n + col. The count is maintained, never recomputed: +1 for
// the new cell, then -1 for every union that actually merged two islands.
//
// Time: O(m * n + k * alpha(m * n)) -- m * n is the one-time array init; each of the k positions
// does at most 4 unions. O(k * m * n) is the flood-fill cost this file beats.
// Space: O(m * n) -- parent, size and the grid.
//
// Siblings: NumberOfIslandsII is the same algorithm on the shared DisjointSet.

public final class NumberOfIslandsIISubmit {

	private int[] size;
	private int[] parent;

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public List<Integer> numOfIslands(int m, int n, int[][] positions) {
		int len = m * n;
		this.parent = new int[len];
		this.size = new int[len];
		for (int i = 0; i < len; i++) {
			parent[i] = i;
			size[i] = 1;
		}
		return merge(m, n, positions);
	}

	private List<Integer> merge(int m, int n, int[][] positions) {
		int count = 0; // islands right now
		int[][] grid = new int[m][n]; // 1 where a position has been added
		List<Integer> list = new ArrayList<>();
		for (int[] position : positions) {
			int row = position[0];
			int col = position[1];
			int index = row * n + col; // flatten 2D position to 1D
			if (grid[row][col] == 1) {
				list.add(count);
				continue; // SKIP: a repeated cell changes nothing
			}
			grid[row][col] = 1; // TAKE: the cell becomes land
			count++; // COUNT: the new cell, as an island of its own
			for (int k = 0; k < dx.length; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				int next = x * n + y;
				// find(index) - root of current cell
				// find(adjacent) - root of neighbor cell
				if (valid(grid, x, y)) {
					if (union(index, next)) { // JOIN with a land neighbour
						count--; // COUNT: two islands became one
					}
				}
			}
			list.add(count); // RECORD: the count after this position
		}
		return list;
	}

	private boolean valid(int[][] grid, int x, int y) {
		int m = grid.length;
		int n = grid[0].length;
		boolean one = x >= 0 && x < m;
		boolean two = y >= 0 && y < n;
		return one && two && grid[x][y] == 1;
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	private boolean union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		if (rootX == rootY) {
			return false;
		}
		if (size[rootX] > size[rootY]) { // the larger root stays on top
			parent[rootY] = rootX;
			size[rootX] += size[rootY];
		} else {
			parent[rootX] = rootY;
			size[rootY] += size[rootX];
		}
		return true;
	}

	public static void main(String[] args) {
		NumberOfIslandsIISubmit mli = new NumberOfIslandsIISubmit();
		int[][] g0 = { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 2 } };
		System.out.println(mli.numOfIslands(3, 3, g0)); // [1, 1, 1, 2]

		int[][] g1 = { { 0, 0 }, { 0, 1 }, { 1, 2 }, { 2, 1 } };
		System.out.println(mli.numOfIslands(3, 3, g1)); // [1, 1, 2, 3]

		int[][] g2 = { { 0, 0 } };
		System.out.println(mli.numOfIslands(1, 1, g2)); // [1]
	}
}
