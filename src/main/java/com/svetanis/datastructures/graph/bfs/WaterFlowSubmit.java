package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// 417. Pacific Atlantic Water Flow

// there is a rectangular island that borders
// both the Pacific and Atlantic oceans.
// the Pacific Ocean touches the islands's
// left and top edges, and the Atlantic Ocean
// touches the islands's right and bottom edges.

// the island receives a lot of rain, and the
// rain water can flow to neighboring cells
// directly north, south, east, and west if
// the neighboring cell's height is less than
// or equal to the current cell's height. 
// Water can flow from any cell adjacent to an
// ocean into the ocean.

// return a list of grid coordinates where
// each pair denotes that rain water can 
// flow from that cell to both oceans

public final class WaterFlowSubmit {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static List<List<Integer>> shortestPath(int[][] grid) {
		Set<Integer> pv = new HashSet<>(); // pacific visited set
		Set<Integer> av = new HashSet<>(); // atlantic visited set
		Queue<Node> pq = new LinkedList<>(); // pacific queue
		Queue<Node> aq = new LinkedList<>(); // atlantic queue
		init(grid, pq, aq);
		// do bfs for each ocean to
		// find all cells reachable
		// from each ocean
		bfs(grid, pq, pv);
		bfs(grid, aq, av);
		// cells reachable from both oceans
		Set<Integer> intersection = new HashSet<>(pv);
		intersection.retainAll(av);
		return path(grid, intersection);
	}

	private static List<List<Integer>> path(int[][] grid, Set<Integer> intersection) {
		int height = grid.length;
		int width = grid[0].length;
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int index = i * width + j;
				if (intersection.contains(index)) {
					list.add(Arrays.asList(i, j));
				}
			}
		}
		return list;

	}

	private static void bfs(int[][] grid, Queue<Node> queue, Set<Integer> set) {
		int width = grid[0].length;
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			int dist = node.dist;
			if (!set.contains(dist)) {
				set.add(dist);
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.x + dx[i];
					int c = node.y + dy[i];
					if (valid(grid, r, c) && grid[r][c] >= grid[node.x][node.y]) {
						int index = r * width + c;
						queue.add(new Node(r, c, index));
					}
				}
			}
		}
	}

	private static void init(int[][] grid, Queue<Node> pq, Queue<Node> aq) {
		int height = grid.length;
		int width = grid[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int index = i * width + j;
				Node node = new Node(i, j, index);
				// Pacific Ocean's edge
				if (i == 0 || j == 0) {
					pq.add(node);
				}
				// Atlantic Ocean's edge
				if (i == height - 1 || j == width - 1) {
					aq.add(node);
				}
			}
		}
	}

	private static boolean valid(int[][] grid, int row, int col) {
		boolean isRow = row >= 0 && row < grid.length;
		boolean isCol = col >= 0 && col < grid[0].length;
		return isRow && isCol;
	}

	public static void main(String[] args) {
		int[][] grid = { //
				{ 1, 2, 2, 3, 5 }, //
				{ 3, 2, 3, 4, 4 }, //
				{ 2, 4, 5, 3, 1 }, //
				{ 6, 7, 1, 4, 5 }, //
				{ 5, 1, 1, 2, 4 } };//
		// [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
		System.out.println(shortestPath(grid));
	}

	private static class Node {
		private int x;
		private int y;
		private int dist;

		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
	}
}
