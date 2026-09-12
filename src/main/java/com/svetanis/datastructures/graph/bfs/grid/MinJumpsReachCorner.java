package com.svetanis.datastructures.graph.bfs.grid;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Queue;
import java.util.Set;

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
		Set<Cell> seen = newHashSet();
		Cell start = new Cell(0, 0);
		queue.add(start);
		seen.add(start);

		// the count starts at 1 because the source itself counts as a jump
		// here. one drain of the queue is one level, so count is a loop
		// variable rather than a field on the node
		int count = 1;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) {
				Cell node = queue.poll();
				int x = node.getX();
				int y = node.getY();

				if (x == n - 1 && y == m - 1) {
					return of(count);
				}

				// a cell holding 0 jumps nowhere, so without `seen` it
				// would enqueue itself and the search would never end
				int dir = grid[x][y];
				enqueue(queue, seen, x + dir, y, n, m);
				enqueue(queue, seen, x, y + dir, n, m);
			}
			count++;
		}
		return absent();
	}

	private static void enqueue(Queue<Cell> queue, Set<Cell> seen, int x, int y, int n, int m) {
		Cell next = new Cell(x, y);
		if (valid(x, y, n, m) && seen.add(next)) {
			queue.add(next);
		}
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
