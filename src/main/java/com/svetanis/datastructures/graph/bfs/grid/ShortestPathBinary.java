package com.svetanis.datastructures.graph.bfs.grid;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// Shortest Path in a Binary Maze (not a LeetCode problem; LC 1091 is the 8-direction variant)
//
// Input: a matrix of 0 and 1, a source cell and a destination cell. A path may only step (up,
// down, left or right) onto cells holding 1; the source and the destination must hold 1 too.
// Returns the number of steps of the shortest path from the source to the destination as a
// present Optional (0 when they are the same cell); an empty Optional if either end holds 0 or
// the destination cannot be reached.
//
// The one idea: one queue entry is a cell that has just been reached. All cells that are the same
// number of steps from the source wait in the queue together, so taking the queue out one batch
// at a time counts the steps: batch 0 is the source, batch 1 is everything one step away, and so
// on. The first batch that holds the destination gives the answer. A cell is put in the set the
// moment it is queued, so it is queued once and the search ends even when the destination cannot
// be reached.
//
// Time: O(n * m) -- each cell is queued at most once, with 4 neighbours looked at.
// Space: O(n * m) -- the set and the queue.
//
// Siblings: ShortestPathBinaryMatrixSubmit is LC 1091, the same level-by-level search with 8
// directions, a corner-to-corner path, and a grid cell marked in place instead of a set.

public final class ShortestPathBinary {

	private static final int[] dx = { -1, 0, 0, 1 };
	private static final int[] dy = { 0, -1, 1, 0 };

	public static Optional<Integer> shortestPath(int[][] grid, Cell src, Cell dst) {
		if (grid[src.getX()][src.getY()] != 1 || grid[dst.getX()][dst.getY()] != 1) {
			return Optional.empty(); // RETURN: an end that is not open
		}
		Set<Cell> set = new HashSet<>();
		Queue<Cell> queue = new LinkedList<>();
		Cell start = new Cell(src.getX(), src.getY());
		queue.add(start);
		set.add(start); // RECORD: mark when queued
		// one drain of the queue is one level, so every square dequeued
		// inside the inner loop is exactly `dist` steps from the source
		int dist = 0;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) { // LEVEL: only the cells of this batch
				Cell node = queue.poll(); // TAKE
				if (node.getX() == dst.getX() && node.getY() == dst.getY()) {
					return Optional.of(dist); // RETURN: the first batch holding dst
				}
				// check all 4 moves and enqueue
				// each valid movement in the queue
				for (int i = 0; i < dx.length; i++) {
					int r = node.getX() + dx[i];
					int c = node.getY() + dy[i];
					Cell next = new Cell(r, c);
					// RECORD at ENQUEUE: set.add is false for a cell queued before
					if (valid(grid, r, c) && set.add(next)) {
						queue.add(next);
					}
				}
			}
			dist++; // COUNT: the next batch is one step further
		}
		return Optional.empty(); // RETURN: dst was never reached
	}

	private static boolean valid(int[][] grid, int row, int col) {
		boolean isRow = row >= 0 && row < grid.length;
		boolean isCol = col >= 0 && col < grid[0].length;
		return isRow && isCol && grid[row][col] != 0; // SKIP: off the grid or a 0 cell
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
		System.out.println(shortestPath(matrix, src, dst)); // Optional[11]

		int[][] blocked = { { 1, 0 }, { 0, 1 } };
		System.out.println(shortestPath(blocked, new Cell(0, 0), new Cell(1, 1))); // Optional.empty
	}
}
