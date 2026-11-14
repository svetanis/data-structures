package com.svetanis.datastructures.graph.bfs.multisource;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

// Zombie in a dungeon (not a LeetCode problem; it is 286 Walls and Gates over a
// List<List<Integer>> instead of an int[][]).
//
// Input: an m by n grid of integers, a map of a dungeon: -1 is a wall or obstacle, 0 is a gate
// out of the dungeon, INF (2^31 - 1) is empty space. For each empty space, find the number of
// steps to the closest exit; each step is horizontal or vertical and cannot pass a wall. Returns
// the same map, filled in place; a space that cannot reach an exit stays INF.
//
// The one idea: the grid itself is the answer, and INF means "not reached yet". Every gate is
// put in the queue at the start, so the search spreads from all the gates at once, one ring of
// steps at a time. A queue entry is one cell whose number is final; each neighbour that is still
// INF gets that number + 1 and joins the queue. Cells leave the queue in order of their
// distance, so the first time a space is reached is by its nearest exit and the number is never
// changed. A wall is never queued because it is not INF. The lists must allow set(): the cells
// are written with grid.get(row).set(col, ...).
//
// Time: O(n * m) -- each cell enters the queue once and looks at its four neighbours.
// Space: O(n * m) -- the queue.
//
// Siblings: WallsAndGates and WallsAndGatesSubmit are the same search over an int[][].

public final class ZombieInDungeon {

	private static int INF = MAX_VALUE;
	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	public static List<List<Integer>> gateDists(List<List<Integer>> grid) {
		Queue<Cell> queue = init(grid);
		bfs(queue, grid);
		return grid;
	}

	private static Queue<Cell> init(List<List<Integer>> grid) {
		int n = grid.size();
		int m = grid.get(0).size();
		Queue<Cell> queue = new ArrayDeque<>();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (grid.get(r).get(c) == 0) { // SEED: every gate starts the search
					queue.add(new Cell(r, c));
				}
			}
		}
		return queue;
	}

	private static void bfs(Queue<Cell> queue, List<List<Integer>> grid) {
		while (!queue.isEmpty()) {
			Cell node = queue.poll();
			int val = grid.get(node.getRow()).get(node.getCol());
			List<Cell> neighbors = neighbors(node, grid);
			for (Cell neighbor : neighbors) {
				int row = neighbor.getRow();
				int col = neighbor.getCol();
				if (grid.get(row).get(col) == INF) { // SKIP: a gate, or a space already reached
					grid.get(row).set(col, 1 + val); // RECORD: one step farther than node
					queue.add(neighbor);
				}
			}
		}
	}

	private static List<Cell> neighbors(Cell node, List<List<Integer>> grid) {
		List<Cell> list = new ArrayList<>();
		for (int dist = 0; dist < dx.length; dist++) { // WALK: the four neighbours
			int x = node.getRow() + dx[dist];
			int y = node.getCol() + dy[dist];
			if (valid(grid, x, y)) {
				list.add(new Cell(x, y));
			}
		}
		return list;
	}

	private static boolean valid(List<List<Integer>> grid, int row, int col) {
		boolean isRow = row >= 0 && row < grid.size();
		boolean isCol = col >= 0 && col < grid.get(0).size();
		return isRow && isCol && grid.get(row).get(col) != -1; // SKIP: off the grid or a wall
	}

	public static void main(String[] args) {
		List<List<Integer>> grid = new ArrayList<>();
		grid.add(asList(INF, -1, 0, INF));
		grid.add(asList(INF, INF, INF, -1));
		grid.add(asList(INF, -1, INF, -1));
		grid.add(asList(0, -1, INF, INF));
		System.out.println(gateDists(grid)); // [[3, -1, 0, 1], [2, 2, 1, -1], [1, -1, 2, -1], [0, -1, 3, 4]]
	}
}
