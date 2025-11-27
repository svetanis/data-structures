package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 3552. Grid Teleportation Traversal

public final class GridTeleportationTraversal {
	// Time Complexity: O(m * n)
	// Space Complexity: O(m * n)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };
	private static final int INF = Integer.MAX_VALUE / 2;

	private int rows;
	private int cols;
	private char[][] grid;
	private Map<Character, List<int[]>> map;

	public int minMoves(String[] a) {
		this.rows = a.length;
		this.cols = a[0].length();
		this.grid = new char[rows][cols];
		this.map = new HashMap<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char c = a[i].charAt(j);
				grid[i][j] = c;
				if (Character.isLetter(c)) {
					map.computeIfAbsent(c, k -> new ArrayList<>()).add(new int[] { i, j });
				}
			}
		}
		return minMoves();
	}

	public int minMoves() {
		Deque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { 0, 0 });
		int[][] dist = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			Arrays.fill(dist[i], INF);
		}
		dist[0][0] = 0;
		while (!dq.isEmpty()) {
			int[] point = dq.pollFirst();
			int row = point[0];
			int col = point[1];
			int currDist = dist[row][col];
			if (row == rows - 1 && col == cols - 1) {
				return currDist;
			}
			char ch = grid[row][col];
			// teleportation
			if (map.containsKey(ch)) {
				for (int[] cell : map.get(ch)) {
					int r = cell[0], c = cell[1];
					if (row == r && col == c) {
						continue;
					}
					if (currDist < dist[r][c]) {
						dist[r][c] = currDist;
						dq.addFirst(new int[] { r, c });
					}
				}
				map.remove(ch);
			}
			// regular moves
			for (int k = 0; k < 4; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				if (safe(x, y) && currDist + 1 < dist[x][y]) {
					dq.addLast(new int[] { x, y });
					dist[x][y] = currDist + 1;
				}
			}
		}

		return -1;
	}

	private boolean safe(int x, int y) {
		boolean row = x >= 0 && x < rows;
		boolean col = y >= 0 && y < cols;
		return row && col && grid[x][y] != '#';
	}

	public static void main(String[] args) {
		GridTeleportationTraversal gtt = new GridTeleportationTraversal();
		String[] grid1 = { "A..", ".A.", "..." };
		System.out.println(gtt.minMoves(grid1)); // 2

		String[] grid2 = { ".#...", ".#.#.", ".#.#.", "...#." };
		System.out.println(gtt.minMoves(grid2)); // 13

		String[] grid3 = { ".", "#" };
		System.out.println(gtt.minMoves(grid3)); // -1

		String[] grid4 = { ".", "A" };
		System.out.println(gtt.minMoves(grid4)); // 1

		String[] grid5 = { ".A", "CA" };
		System.out.println(gtt.minMoves(grid5)); // 1

		String[] grid6 = { ".#..#.", "CCEH.D", "..D.FB" };
		System.out.println(gtt.minMoves(grid6)); // 4

		String[] grid7 = { ".D", "EH" };
		System.out.println(gtt.minMoves(grid7)); // 2

	}
}
