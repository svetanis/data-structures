package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;

// given a binary matrix, where each 
// element can either be 0 or 1
// find the shortest path between a
// given source cell to a destination.
// the path can only be created out of 
// a cell if its value is 1

public final class ShortestPathBinaryMaze {
	// Time Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static Optional<Integer> shortestPath(int[][] grid, Node src, Node dst) {
		if (grid[src.x][src.y] != 1 || grid[dst.x][dst.y] != 1) {
			return absent();
		}
		Set<Node> set = newHashSet();
		Queue<Node> queue = newLinkedList();
		queue.add(new Node(src.x, src.y));
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.x == dst.x && node.y == dst.y) {
				return of(node.dist);
			}
			if (!set.contains(node)) {
				set.add(node);
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.x + dx[i];
					int c = node.y + dy[i];
					if (valid(grid, r, c)) {
						queue.add(new Node(r, c, node.dist + 1));
					}
				}
			}
		}
		return absent();
	}

	private static boolean valid(int[][] grid, int row, int col) {
		boolean isRow = row >= 0 && row < grid.length;
		boolean isCol = col >= 0 && col < grid[0].length;
		return isRow && isCol && grid[row][col] != 0;
	}

	public static void main(String[] args) {
		int[][] matrix = { //
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 }, //
				{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 }, //
				{ 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 }, //
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, //
				{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 }, //
				{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 }, //
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, //
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 }, //
				{ 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 } };//

		Node src = new Node(0, 0);
		Node dst = new Node(3, 4);
		System.out.println(shortestPath(matrix, src, dst));
	}
}
