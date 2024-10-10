package com.svetanis.datastructures.graph.bfs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

import com.google.common.base.Optional;
import com.svetanis.datastructures.graph.Cell;

// given 2D grid and starting position (0,0)
// each cell contains a positive integer
// that defines the number of cells to jump
// either in the right or downward direction
// find the min number of cells that need to 
// be touched in order to reach bottom right corner

public final class MinJumpsReachCorner {
	// Time Complexity: O(n)

	public static Optional<Integer> minJumps(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		Queue<Cell> queue = newLinkedList();
		queue.add(new Cell(0, 0, 1));

		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			int x = node.getX();
			int y = node.getY();
			int count = node.getDist();

			if (x == n - 1 && y == m - 1) {
				return of(count);
			}

			int dir = grid[x][y];

			if (valid(x + dir, y, n, m)) {
				queue.add(new Cell(x + dir, y, count + 1));
			}

			if (valid(x, y + dir, n, m)) {
				queue.add(new Cell(x, y + dir, count + 1));
			}

		}
		return absent();
	}

	private static boolean valid(int x, int y, int n, int m) {
		return x < n && y < m && x >= 0 && y >= 0;
	}

	public static void main(String[] args) {
		int[][] grid = { { 2, 4, 2 }, //
				{ 5, 3, 8 }, //
				{ 1, 1, 1 } //
		};//
		System.out.println(minJumps(grid));
	}
}
