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
import com.svetanis.java.base.Pair;

// given a binary matrix, where each 
// element can either be 0 or 1
// find the shortest path between a
// given source cell to a destination.
// the path can only be created out of 
// a cell if its value is 1

public final class WaterFlow {
	// Time Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static ImmutableList<Pair<Integer, Integer>> shortestPath(int[][] grid) {
		Set<Node> pv = newHashSet(); // pacific visited set
		Set<Node> av = newHashSet(); // atlantic visited set
		Queue<Node> pq = newLinkedList(); // pacific queue
		Queue<Node> aq = newLinkedList(); // atlantic queue
		init(grid, pq, aq);
		// do bfs for each ocean to
		// find all cells reachable
		// from each ocean
		bfs(grid, pq, pv);
		bfs(grid, aq, av);
		// cells reachable from both oceans
		Set<Node> intersection = intersection(pv, av);
		List<Node> sorted = sort(intersection, n -> n.dist);
		return transform(sorted, n -> Pair.build(n.x, n.y));
	}

	private static void bfs(int[][] grid, Queue<Node> queue, Set<Node> set) {
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (!set.contains(node)) {
				set.add(node);
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.x + dx[i];
					int c = node.y + dy[i];
					if (valid(grid, r, c) && grid[r][c] >= grid[node.x][node.y]) {
						int index = r * grid[0].length + c;
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
		System.out.println(shortestPath(grid));
	}
}
