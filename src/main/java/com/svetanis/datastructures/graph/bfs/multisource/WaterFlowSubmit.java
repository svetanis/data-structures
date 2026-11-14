package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// 417. Pacific Atlantic Water Flow
//
// Input: grid -- an m x n island where grid[r][c] is the height of cell (r, c). The Pacific
// Ocean touches the top and left edges, the Atlantic Ocean the bottom and right edges. Rain water
// flows from a cell to a neighbour (north, south, east, west) of the same height or lower, and
// from an edge cell into the ocean it borders. Returns every cell [r, c] from which water can
// reach both oceans, in row-major order. On LeetCode: 1 <= m, n <= 200, 0 <= grid[r][c] <= 10^5.
//
// The one idea: one queue entry (Node) holds one cell -- its row, its column and its row-major
// index r * width + c, which the code calls dist but which is a name for the cell, not a
// distance. A visited set of those indexes holds the cells an ocean's water can be traced back
// to. Testing every cell on its own repeats work, so run it backwards: start from all the cells
// touching one ocean and climb UPHILL (to a neighbour of the same height or higher); every cell
// reached can pour back down the same steps into that ocean. One climb per ocean; the indexes
// in both sets are the answer, and walking the grid in row-major order lists them sorted.
//
// Time: O(m * n) -- each cell is accepted once per ocean, and each acceptance looks at 4 neighbours
// Space: O(m * n) -- the two visited sets and the two queues
//
// Siblings: WaterFlowBfs and WaterFlowDfs do the same with boolean grids; WaterFlow keeps the
// visited cells as Cell objects in sets.
public final class WaterFlowSubmit {

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
		intersection.retainAll(av); // keep only the cells both oceans reach
		return path(grid, intersection);
	}

	private static List<List<Integer>> path(int[][] grid, Set<Integer> intersection) {
		int height = grid.length;
		int width = grid[0].length;
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < height; i++) { // WALK: row-major order, so the answer comes out sorted
			for (int j = 0; j < width; j++) {
				int index = i * width + j;
				if (intersection.contains(index)) { // TAKE: both oceans reach it
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
			if (!set.contains(dist)) { // SKIP: this ocean already reaches it
				set.add(dist); // RECORD: this ocean's water can be traced back to it
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) { // WALK: 4 neighbours
					int r = node.x + dx[i];
					int c = node.y + dy[i];
					if (valid(grid, r, c) && grid[r][c] >= grid[node.x][node.y]) { // TAKE: uphill or level
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
					pq.add(node); // BASE: top and left cells touch the Pacific
				}
				// Atlantic Ocean's edge
				if (i == height - 1 || j == width - 1) {
					aq.add(node); // BASE: bottom and right cells touch the Atlantic
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
		System.out.println(shortestPath(grid)); // [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]

		int[][] one = { { 1 } };
		System.out.println(shortestPath(one)); // [[0, 0]]
	}

	private static class Node {
		private int x;
		private int y;
		private int dist; // the cell's row-major index, not a distance

		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
	}
}
