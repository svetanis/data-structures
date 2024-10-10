package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.collect.Lists.transform;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.Cell;
import com.svetanis.java.base.Pair;

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

public final class WaterFlow {
	// Time Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static ImmutableList<Pair<Integer, Integer>> shortestPath(int[][] grid) {
		Set<Cell> pv = newHashSet(); // pacific visited set
		Set<Cell> av = newHashSet(); // atlantic visited set
		Queue<Cell> pq = newLinkedList(); // pacific queue
		Queue<Cell> aq = newLinkedList(); // atlantic queue
		init(grid, pq, aq);
		// do bfs for each ocean to
		// find all cells reachable
		// from each ocean
		bfs(grid, pq, pv);
		bfs(grid, aq, av);
		// cells reachable from both oceans
		Set<Cell> intersection = intersection(pv, av);
		List<Cell> sorted = sort(intersection, n -> n.getDist());
		return transform(sorted, n -> Pair.build(n.getX(), n.getY()));
	}

	private static void bfs(int[][] grid, Queue<Cell> queue, Set<Cell> set) {
		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			if (!set.contains(node)) {
				set.add(node);
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.getX() + dx[i];
					int c = node.getY() + dy[i];
					if (valid(grid, r, c) && grid[r][c] >= grid[node.getX()][node.getY()]) {
						int index = r * grid[0].length + c;
						queue.add(new Cell(r, c, index));
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
				int index = i * width + j;
				Cell node = new Cell(i, j, index);
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
		System.out.println(shortestPath(grid));
	}
}
