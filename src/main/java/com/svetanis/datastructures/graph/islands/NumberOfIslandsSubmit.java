package com.svetanis.datastructures.graph.islands;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

// 200. Number of Islands
//
// Input: grid -- an m x n map held as a List of Lists of Integers, 1 for land and 0 for water. An
// island is a group of land cells joined by sides (up, down, left or right), and everything off
// the edge of the grid is water. Returns the number of islands. LeetCode gives the grid as a
// char[][] of '1' and '0'; see NumberOfIslandsBfsSubmit for that form. On LeetCode:
// 1 <= m, n <= 300.
//
// The one idea: sweep the grid cell by cell. A land cell that no earlier walk has marked is the
// first cell of an island nobody has counted, so the count goes up by one and a breadth-first
// walk marks every land cell joined to it. One queue entry is one land cell whose neighbours have
// not been looked at yet, and visited[r][c] says whether cell (r, c) is already spoken for. A cell
// is marked at the moment it goes into the queue, so no cell is queued twice. Marking after the
// take-out would leave a window in which a cell is queued and unmarked, and every neighbour taken
// out during that window would queue it again: the same answer with several times the queue
// traffic.
//
// Time: O(r * c) -- every land cell is queued at most once and looks at four sides.
// Space: O(r * c) -- the boolean[r][c] visited array. The queue itself is only O(r + c): it holds
// one breadth-first band, which on an all-land square peaks at about one side's worth of cells.
// There is no deep stack here, so the array is the whole cost.
//
// Siblings: NumberOfIslandsBfs is the same walk with a Set of Cells in place of the array;
// NumberOfIslandsDfs is the recursive walk on the same List grid, which overflows a default Java
// stack on a very large island.

public final class NumberOfIslandsSubmit {

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
		boolean[][] visited = new boolean[n][m];
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				int val = grid.get(r).get(c);
				if (val != 0 && !visited[r][c]) {
					visited[r][c] = true; // MARK: the first cell, before the walk starts
					Cell src = new Cell(r, c);
					bfs(src, grid, visited); // WALK: mark the rest of the island
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	private static void bfs(Cell src, List<List<Integer>> grid, boolean[][] visited) {
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			List<Cell> neighbors = neighbors(curr, grid);
			for (Cell neighbor : neighbors) {
				int r = neighbor.getRow();
				int c = neighbor.getCol();
				if (!visited[r][c]) {
					queue.add(neighbor);
				}
				// MARK: by the line that ADDS, not after the poll, so no cell is queued twice
				visited[r][c] = true;
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
