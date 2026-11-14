package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 3552. Grid Teleportation Traversal
//
// Input: a grid of m rows and n columns, given as strings. '.' is an empty cell, '#' a wall, and
// an uppercase letter a portal. You start at the top-left cell (assumed not a wall) and want the
// bottom-right cell. A move goes to an adjacent cell (up, down, left, right) that is not a wall.
// Standing on a portal letter, you may jump for free to any other cell with the same letter, but
// each letter may be used for jumping only once. On LeetCode: 1 <= m, n <= 5 * 10^4,
// 2 <= m * n <= 5 * 10^4.
// Returns the fewest moves to reach the bottom-right cell, or -1 if it cannot be reached.
//
// The one idea: one queue entry is one cell, and dist[row][col] is the fewest moves found so far
// to stand on it. A step to a neighbour costs 1 and a portal jump costs 0, so the entries cannot
// be taken strictly in the order they were added. The queue is kept in order of moves instead:
// a cost-1 cell goes to the back, a cost-0 cell (a jump) goes to the front, so the front always
// holds the cell with the fewest moves. A letter is used for jumping the first time any of its
// cells is taken from the queue; that is the cheapest moment to use it, so later cells with the
// same letter can never do better, and the letter is removed from the map.
//
// Time: O(m * n) -- a cell is queued again only when its distance drops, and each letter's cells
// are scanned once.
// Space: O(m * n) -- the distance table, the queue, and the portal map.
//
// Siblings: none -- this is the only file for this problem.

public final class GridTeleportationTraversal {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, -1, 0, 1 };
	private static final int INF = Integer.MAX_VALUE / 2;

	private int rows;
	private int cols;
	private char[][] grid;
	private Map<Character, List<int[]>> map;

	public int minMoves(String[] a) {
		this.rows = a.length;
		this.cols = a[0].length();
		this.grid = new char[rows][cols];
		this.map = new HashMap<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char c = a[i].charAt(j);
				grid[i][j] = c;
				if (Character.isLetter(c)) { // RECORD: letter -> every cell that carries it
					map.computeIfAbsent(c, k -> new ArrayList<>()).add(new int[] { i, j });
				}
			}
		}
		return minMoves();
	}

	public int minMoves() {
		Deque<int[]> dq = new ArrayDeque<>(); // QUEUE: cells in order of moves, fewest at the front
		dq.offer(new int[] { 0, 0 });
		int[][] dist = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			Arrays.fill(dist[i], INF); // BASE: nothing reached yet
		}
		dist[0][0] = 0; // BASE: the start costs 0 moves
		while (!dq.isEmpty()) {
			int[] point = dq.pollFirst(); // TAKE: the cell with the fewest moves
			int row = point[0];
			int col = point[1];
			int currDist = dist[row][col];
			if (row == rows - 1 && col == cols - 1) {
				return currDist; // RETURN: the first time the goal is taken, its distance is final
			}
			char ch = grid[row][col];
			// JUMP: free move to every other cell with this letter
			if (map.containsKey(ch)) {
				for (int[] cell : map.get(ch)) {
					int r = cell[0], c = cell[1];
					if (row == r && col == c) {
						continue; // SKIP: the cell we stand on
					}
					if (currDist < dist[r][c]) {
						dist[r][c] = currDist; // RECORD: same cost as here
						dq.addFirst(new int[] { r, c }); // FRONT: a free move is taken next
					}
				}
				map.remove(ch); // USED: this letter never jumps again
			}
			// STEP: one move to each open neighbour, cost 1
			for (int k = 0; k < 4; k++) {
				int x = row + dx[k];
				int y = col + dy[k];
				if (safe(x, y) && currDist + 1 < dist[x][y]) {
					dq.addLast(new int[] { x, y }); // BACK: a paid move is taken later
					dist[x][y] = currDist + 1;
				}
			}
		}

		return -1; // RETURN: the goal was never reached
	}

	private boolean safe(int x, int y) {
		boolean row = x >= 0 && x < rows;
		boolean col = y >= 0 && y < cols;
		return row && col && grid[x][y] != '#'; // SKIP: outside the grid or a wall
	}

	public static void main(String[] args) {
		GridTeleportationTraversal gtt = new GridTeleportationTraversal();
		String[] grid1 = { "A..", ".A.", "..." };
		System.out.println(gtt.minMoves(grid1)); // 2

		String[] grid2 = { ".#...", ".#.#.", ".#.#.", "...#." };
		System.out.println(gtt.minMoves(grid2)); // 13

		String[] grid3 = { ".", "#" };
		System.out.println(gtt.minMoves(grid3)); // -1

		String[] grid4 = { ".", "A" };
		System.out.println(gtt.minMoves(grid4)); // 1

		String[] grid5 = { ".A", "CA" };
		System.out.println(gtt.minMoves(grid5)); // 1

		String[] grid6 = { ".#..#.", "CCEH.D", "..D.FB" };
		System.out.println(gtt.minMoves(grid6)); // 4

		String[] grid7 = { ".D", "EH" };
		System.out.println(gtt.minMoves(grid7)); // 2

	}
}
