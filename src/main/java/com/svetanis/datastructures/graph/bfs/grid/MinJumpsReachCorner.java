package com.svetanis.datastructures.graph.bfs.grid;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// Minimum Jumps to Reach the Corner (not a LeetCode problem)
//
// Input: a grid of n rows and m columns; every cell holds a number. Standing on a cell holding v,
// you may jump v cells to the right or v cells down (not both); a jump that leaves the grid is
// not allowed, and a cell holding 0 goes nowhere. You start on the top-left cell.
// Returns the number of cells touched on the shortest way to the bottom-right cell, counting the
// start and the end, as a present Optional; an empty Optional if the corner cannot be reached.
//
// The one idea: one queue entry is a cell you can stand on. All cells that need the same number
// of jumps sit in the queue together, so taking the queue out one batch at a time counts the
// jumps: batch 1 is the start, batch 2 is everything one jump away, and so on. The first batch
// that contains the bottom-right cell tells how many cells were touched. A cell is marked the
// moment it is queued, so it is queued once; without the mark a cell holding 0 would queue itself
// forever.
//
// Time: O(n * m) -- each cell is queued at most once and makes 2 jumps.
// Space: O(n * m) -- the seen set and the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class MinJumpsReachCorner {

	public static Optional<Integer> minJumps(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		Queue<Cell> queue = new LinkedList<>();
		Set<Cell> seen = new HashSet<>();
		Cell start = new Cell(0, 0);
		queue.add(start);
		seen.add(start); // RECORD: mark when queued

		// the count starts at 1 because the source itself counts as a jump
		// here. one drain of the queue is one level, so count is a loop
		// variable rather than a field on the node
		int count = 1;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) { // LEVEL: only the cells of this batch
				Cell node = queue.poll(); // TAKE
				int x = node.getX();
				int y = node.getY();

				if (x == n - 1 && y == m - 1) {
					return Optional.of(count); // RETURN: the first batch holding the corner
				}

				// a cell holding 0 jumps nowhere, so without `seen` it
				// would enqueue itself and the search would never end
				int dir = grid[x][y];
				enqueue(queue, seen, x + dir, y, n, m); // JUMP: down
				enqueue(queue, seen, x, y + dir, n, m); // JUMP: right
			}
			count++; // COUNT: the next batch is one jump further
		}
		return Optional.empty(); // RETURN: the corner was never reached
	}

	private static void enqueue(Queue<Cell> queue, Set<Cell> seen, int x, int y, int n, int m) {
		Cell next = new Cell(x, y);
		if (valid(x, y, n, m) && seen.add(next)) { // SKIP: off the grid, or queued before
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
		System.out.println(minJumps(grid)); // Optional[3]

		int[][] blocked = { { 0, 1 }, { 1, 1 } };
		System.out.println(minJumps(blocked)); // Optional.empty

		int[][] single = { { 5 } };
		System.out.println(minJumps(single)); // Optional[1]
	}
}
