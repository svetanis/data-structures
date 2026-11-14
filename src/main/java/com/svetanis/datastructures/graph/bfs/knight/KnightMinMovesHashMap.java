package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

// Knight's minimum moves on an n x n board (not a LeetCode problem; the unbounded board is 1197)
//
// Input: n -- the board is n x n; src and dst -- two squares, as (row, col) with 0 <= row, col < n.
// A knight moves two squares in one direction and one square across, in any of 8 ways, and must
// stay on the board. Returns the least number of moves from src to dst, or -1 when dst cannot be
// reached (on a 2 x 2 or 3 x 3 board some squares cannot).
//
// The one idea: one queue entry holds one square the knight can stand on, and the map holds, for
// every square seen, how many moves it took to first reach it. The queue hands out squares
// nearest-first, so the first time a square is put in the map is also its shortest distance; a
// square already in the map is skipped, so the map doubles as the "seen" mark. A new square is
// one move further than the square it was found from.
//
// Time: O(n^2) -- each of the n * n squares is queued once and tries 8 moves
// Space: O(n^2) -- the map and the queue hold each square at most once
//
// Siblings: KnightMinMoves counts rings instead of keeping a distance per square; the map is
// the same idea as OpenLock's. KnightMinMovesUnboundedHashMap is this search on an infinite board.
public final class KnightMinMovesHashMap {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int n, Cell src, Cell dst) {
		Map<Cell, Integer> map = new HashMap<>();
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		map.put(src, 0); // BASE: the start is 0 moves from itself
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			if (curr.getRow() == dst.getRow() && curr.getCol() == dst.getCol()) {
				return map.get(curr); // RETURN: first time dst comes off the queue is its shortest
			}
			List<Cell> neighbors = neighbors(curr, n);
			for (Cell neighbor : neighbors) {
				if (!map.containsKey(neighbor)) { // SKIP: already reached by a route at least as short
					map.put(neighbor, map.get(curr) + 1); // RECORD: one move more than its parent
					queue.add(neighbor);
				}
			}
		}
		return -1; // RETURN: the queue ran dry, dst cannot be reached
	}

	private static List<Cell> neighbors(Cell curr, int n) {
		List<Cell> list = new ArrayList<>();
		for (int dir = 0; dir < dx.length; dir++) { // WALK: the 8 knight moves
			int x = curr.getRow() + dx[dir];
			int y = curr.getCol() + dy[dir];
			if (valid(x, y, n)) { // SKIP: off the board
				list.add(new Cell(x, y));
			}
		}
		return list;
	}

	private static boolean valid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		Cell src1 = new Cell(0, 0);
		Cell dst1 = new Cell(4, 1);
		System.out.println(ksp(5, src1, dst1)); // 3

		Cell src2 = new Cell(0, 7);
		Cell dst2 = new Cell(7, 0);
		System.out.println(ksp(8, src2, dst2)); // 6

		// the start is the target
		System.out.println(ksp(8, src1, src1)); // 0

		// on a 3 x 3 board the centre square cannot be entered or left
		System.out.println(ksp(3, src1, new Cell(1, 1))); // -1
	}
}
