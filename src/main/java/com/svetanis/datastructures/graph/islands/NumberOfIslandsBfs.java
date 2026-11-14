package com.svetanis.datastructures.graph.islands;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// 200. Number of Islands
//
// Input: grid -- an m x n map held as a List of Lists of Integers, 1 for land and 0 for water. An
// island is a group of land cells joined by sides (up, down, left or right), and everything off
// the edge of the grid is water. Returns the number of islands. LeetCode gives the grid as a
// char[][] of '1' and '0'; see NumberOfIslandsBfsSubmit for that form. On LeetCode:
// 1 <= m, n <= 300.
//
// The one idea: sweep the grid cell by cell. A land cell that no earlier walk has seen is the
// first cell of an island nobody has counted, so the count goes up by one and a breadth-first
// walk marks every land cell joined to it. One queue entry is one land cell whose neighbours have
// not been looked at yet, and the set holds every cell already seen. A cell goes into the set at
// the moment it goes into the queue, so no cell is queued twice.
//
// The set does not need the grid's dimensions up front, so this walk also works where the cells
// are not laid out in a rectangle. Here they are, and NumberOfIslandsSubmit's boolean[][] does the
// same job with an array lookup in place of hashing a Cell.
//
// Time: O(r * c) -- every land cell is queued at most once and looks at four sides.
// Space: O(r * c) -- the Set of seen coordinates, which ends up holding every land cell. The queue
// itself is only O(r + c): it holds one breadth-first band, which on an all-land square peaks at
// about one side's worth of cells. There is no deep stack here, so the set is the whole cost.
//
// Siblings: NumberOfIslandsSubmit is the same walk on the same List grid with a boolean[][] in
// place of the Set; NumberOfIslandsDfs is the recursive walk, which overflows a default Java
// stack on a very large island.

public final class NumberOfIslandsBfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };
	// horizontal + vertical + diagonal moves
	// private static int[] ddx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	// private static int[] ddy = { -1, 0, 1, -1, 1, -1, 0, 1 };

	public static int count(List<List<Integer>> grid) {
		int count = 0;
		int n = grid.size();
		int m = grid.get(0).size();
		Set<Cell> set = new HashSet<>();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				int val = grid.get(r).get(c);
				Cell src = new Cell(r, c);
				if (val != 0 && !set.contains(src)) {
					set.add(src); // MARK: the first cell, before the walk starts
					bfs(src, grid, set); // WALK: mark the rest of the island
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	private static void bfs(Cell src, List<List<Integer>> grid, Set<Cell> set) {
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			List<Cell> neighbors = neighbors(curr, grid);
			for (Cell neighbor : neighbors) {
				if (!set.contains(neighbor)) {
					queue.add(neighbor);
				}
				// MARK: by the line that ADDS, not after the poll -- see
				// NumberOfIslandsBfsSubmit for what the other placement costs
				set.add(neighbor);
			}
		}
	}

	private static List<Cell> neighbors(Cell src, List<List<Integer>> grid) {
		List<Cell> list = new ArrayList<>();
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int dist = 0; dist < dx.length; ++dist) {
			int x = src.getRow() + dx[dist];
			int y = src.getCol() + dy[dist];
			if (inBounds(grid, x, y) && grid.get(x).get(y) != 0) {
				list.add(new Cell(x, y)); // BUILD: the land cells beside src
			}
		}
		return list;
	}

	private static boolean inBounds(List<List<Integer>> grid, int row, int col) {
		boolean one = row >= 0 && row < grid.size(); // row number is in range
		boolean two = col >= 0 && col < grid.get(0).size(); // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		List<List<Integer>> grid1 = new ArrayList<>();
		grid1.add(asList(1, 1, 0, 0, 0));
		grid1.add(asList(0, 1, 0, 0, 1));
		grid1.add(asList(1, 0, 0, 1, 1));
		grid1.add(asList(0, 0, 0, 0, 0));
		grid1.add(asList(1, 0, 1, 0, 1));
		System.out.println(count(grid1)); // 6

		List<List<Integer>> grid2 = new ArrayList<>();
		grid2.add(asList(1, 1, 1, 1, 0));
		grid2.add(asList(1, 1, 0, 1, 0));
		grid2.add(asList(1, 1, 0, 0, 0));
		grid2.add(asList(0, 0, 0, 0, 0));
		System.out.println(count(grid2)); // 1

		List<List<Integer>> grid3 = new ArrayList<>();
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(1, 1, 0, 0, 0));
		grid3.add(asList(0, 0, 1, 0, 0));
		grid3.add(asList(0, 0, 0, 1, 1));
		System.out.println(count(grid3)); // 3

		List<List<Integer>> grid4 = new ArrayList<>();
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(1, 1, 1, 1, 0, 0));
		grid4.add(asList(1, 1, 1, 0, 0, 0));
		grid4.add(asList(0, 1, 0, 0, 0, 0));
		grid4.add(asList(0, 0, 0, 0, 1, 0));
		grid4.add(asList(0, 0, 0, 0, 0, 0));
		System.out.println(count(grid4)); // 2
	}
}
