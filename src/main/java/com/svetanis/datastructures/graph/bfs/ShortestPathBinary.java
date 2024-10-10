package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;
import com.svetanis.datastructures.graph.Cell;

// given a binary matrix, where each 
// element can either be 0 or 1
// find the shortest path between a
// given source cell to a destination.
// the path can only be created out of 
// a cell if its value is 1

public final class ShortestPathBinary {
	// Time Complexity: O(n * m)

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static Optional<Integer> shortestPath(int[][] grid, Cell src, Cell dst) {
		if (grid[src.getX()][src.getY()] != 1 || grid[dst.getX()][dst.getY()] != 1) {
			return absent();
		}
		Set<Cell> set = newHashSet();
		Queue<Cell> queue = newLinkedList();
		queue.add(new Cell(src.getX(), src.getY()));
		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			if (node.getX() == dst.getX() && node.getY() == dst.getY()) {
				return of(node.getDist());
			}
			if (!set.contains(node)) {
				set.add(node);
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.getX() + dx[i];
					int c = node.getY() + dy[i];
					if (valid(grid, r, c)) {
						queue.add(new Cell(r, c, node.getDist() + 1));
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

		Cell src = new Cell(0, 0);
		Cell dst = new Cell(3, 4);
		System.out.println(shortestPath(matrix, src, dst));
	}
}
