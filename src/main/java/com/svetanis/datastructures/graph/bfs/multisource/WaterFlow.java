package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// 417. Pacific Atlantic Water Flow
//
// Input: grid -- an m x n island where grid[r][c] is the height of cell (r, c). The Pacific
// Ocean touches the top and left edges, the Atlantic Ocean the bottom and right edges. Rain water
// flows from a cell to a neighbour (north, south, east, west) of the same height or lower, and
// from an edge cell into the ocean it borders. Returns every cell [r, c] from which water can
// reach both oceans, in row-major order. On LeetCode: 1 <= m, n <= 200, 0 <= grid[r][c] <= 10^5.
//
// The one idea: one queue entry holds one cell that can already be shown to drain into one ocean.
// Asking "can this cell reach the ocean by going downhill?" for every cell repeats work, so run
// it backwards: start from all the cells touching one ocean and climb UPHILL (to a neighbour of
// the same height or higher) -- every cell reached drains into that ocean, because the water can
// retrace the climb downhill. Do that once for each ocean; the cells in both sets are the answer.
//
// Time: O(m * n) -- each cell is accepted once per ocean, and each acceptance looks at 4 neighbours
// Space: O(m * n) -- the two visited sets and the two queues
//
// Siblings: WaterFlowBfs and WaterFlowDfs do the same with boolean grids (breadth-first and
// depth-first); WaterFlowSubmit is the LeetCode submission, keeping each cell as an index.
// This one keeps cells as Cell objects in sets.
public final class WaterFlow {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static List<List<Integer>> shortestPath(int[][] grid) {
		Set<Cell> pv = new HashSet<>(); // pacific visited set
		Set<Cell> av = new HashSet<>(); // atlantic visited set
		Queue<Cell> pq = new ArrayDeque<>(); // pacific queue
		Queue<Cell> aq = new ArrayDeque<>(); // atlantic queue
		init(grid, pq, aq);
		// do bfs for each ocean to
		// find all cells reachable
		// from each ocean
		bfs(grid, pq, pv);
		bfs(grid, aq, av);
		// cells reachable from both oceans
		Set<Cell> intersection = new HashSet<>(pv);
		intersection.retainAll(av); // keep only the cells both oceans reach
		// row-major order: the Cell used to carry this as a `dist` field,
		// but it is a function of the position and belongs here
		int width = grid[0].length;
		List<Cell> sorted = new ArrayList<>(intersection);
		sorted.sort(Comparator.comparingInt(n -> n.getX() * width + n.getY())); // SORT: row-major order
		List<List<Integer>> list = new ArrayList<>();
		for (Cell n : sorted) {
			list.add(Arrays.asList(n.getX(), n.getY())); // RECORD: one [row, col] pair per cell
		}
		return list;
	}

	private static void bfs(int[][] grid, Queue<Cell> queue, Set<Cell> set) {
		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			if (!set.contains(node)) { // SKIP: this ocean already reaches it
				set.add(node); // RECORD: this ocean's water can be traced back to it
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) { // WALK: 4 neighbours
					int r = node.getX() + dx[i];
					int c = node.getY() + dy[i];
					if (valid(grid, r, c) && grid[r][c] >= grid[node.getX()][node.getY()]) { // TAKE: uphill or level
						queue.add(new Cell(r, c));
					}
				}
			}
		}
	}

	private static void init(int[][] grid, Queue<Cell> pq, Queue<Cell> aq) {
		int height = grid.length;
		int width = grid[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell node = new Cell(i, j);
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
}
