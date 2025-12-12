package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 305. Number of Islands II

public final class NumberOfIslandsII {
	// Time Complexity: O(k * n * m)
	// Space Complexity: O(n * m)

	private int[] parent;

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public List<Integer> numOfIslands(int m, int n, int[][] positions) {
		int size = m * n;
		parent = new int[size];
		Arrays.fill(parent, -1);
		return merge(m, n, positions);
	}

	private List<Integer> merge(int m, int n, int[][] positions) {
		int count = 0;
		int[][] grid = new int[m][n];
		List<Integer> list = new ArrayList<>();
		for (int[] position : positions) {
			int i = position[0];
			int j = position[1];
			int index = i * n + j; // flatten 2D position to 1D
			if (grid[i][j] == 1) {
				list.add(count);
				continue;
			}
			grid[i][j] = 1;
			parent[index] = index;
			count++;
			for (int k = 0; k < dx.length; k++) {
				int x = i + dx[k];
				int y = j + dy[k];
				int adjacent = x * n + y;
				// find(index) - root of current cell
				// find(adjacent) - root of neighbor cell
				if (valid(grid, x, y) && find(index) != find(adjacent)) {
					union(adjacent, index);
					count--;
				}
			}
			list.add(count);
		}
		return list;
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

	private void union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		if (rootX != rootY) {
			parent[rootY] = rootX;
		}
	}

	public static void main(String[] args) {
		NumberOfIslandsII mli = new NumberOfIslandsII();
		int[][] g0 = { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 2 } };
		System.out.println(mli.numOfIslands(3, 3, g0)); // 1,1,1,2

		int[][] g1 = { { 0, 0 }, { 0, 1 }, { 1, 2 }, { 2, 1 } };
		System.out.println(mli.numOfIslands(3, 3, g1)); // 1,1,2,3

		int[][] g2 = { { 0, 0 } };
		System.out.println(mli.numOfIslands(1, 1, g2)); // 1
	}
}
