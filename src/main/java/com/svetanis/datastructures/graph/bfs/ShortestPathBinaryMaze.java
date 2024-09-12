package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.awt.Point;
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

	public static Optional<Integer> shortestPath(int[][] grid, 
			Point src, Point dst) {
		if (grid[src.x][src.y] != 1 || grid[dst.x][dst.y] != 1) {
			return absent();
		}

		Set<Point> set = newHashSet();
		Queue<Node> queue = newLinkedList();
		set.add(src);
		queue.offer(new Node(src.x, src.y, 0));
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.x == dst.x && node.y == dst.y) {
				return of(node.dist);
			}
			for (int i = 0; i < dx.length; i++) {
				int r = node.x + dx[i];
				int c = node.y + dy[i];
				Point p = new Point(r, c);
				if (isSafe(grid, r, c) && !set.contains(p)) {
					Node next = new Node(r, c, node.dist + 1);
					queue.offer(next);
					set.add(p);
				}
			}
		}
		return absent();
	}

	private static boolean isSafe(int[][] grid, int row, int col) {
		boolean isRow = row >= 0 && row <= grid.length;
		boolean isCol = col >= 0 && col <= grid[0].length;
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

		Point src = new Point(0, 0);
		Point dst = new Point(3, 4);
		System.out.println(shortestPath(matrix, src, dst));
	}
}
