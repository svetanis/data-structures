package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// Knight's minimum moves on an n x n board (not a LeetCode problem; the unbounded board is 1197)
//
// Input: n -- the board is n x n; src and dst -- two squares, as (row, col) with 0 <= row, col < n.
// A knight moves two squares in one direction and one square across, in any of 8 ways, and must
// stay on the board. Returns the least number of moves from src to dst, or -1 when dst cannot be
// reached (on a 2 x 2 or 3 x 3 board some squares cannot).
//
// The one idea: one queue entry holds one square the knight can stand on. The queue is drained a
// whole ring at a time: the first ring is src itself (0 moves), the next ring is every new square
// one knight move away (1 move), and so on, so `steps` is the number of rings finished. A square
// is marked seen when it is put in the queue, so no square is queued twice, and the first ring
// that contains dst is its shortest distance. The 8 neighbours are the only knight-specific part;
// the rest is the same ring-by-ring search as on any grid.
//
// Time: O(n^2) -- each of the n * n squares is queued once and tries 8 moves
// Space: O(n^2) -- the seen set and the queue hold each square at most once
//
// Siblings: KnightMinMovesHashMap stores each square's distance in a map instead of counting
// rings; KnightShortestPath and KnightShortestPathPattern do the same ring search and return an
// Optional instead of -1; KnightMinMovesUnbounded is the same search on an infinite board.
public final class KnightMinMoves {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int n, Cell src, Cell dst) {
		Set<Cell> set = new HashSet<>();
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src); // BASE: the start is seen, 0 moves away
		int steps = 0;
		while (!queue.isEmpty()) {
			int size = queue.size(); // frozen: exactly one ring is drained per pass
			for (int i = 0; i < size; i++) { // WALK: one whole ring
				Cell curr = queue.poll();
				if (curr.getRow() == dst.getRow() && curr.getCol() == dst.getCol()) {
					return steps; // RETURN: the ring number is the distance
				}
				List<Cell> neighbors = neighbors(curr, n);
				for (Cell neighbor : neighbors) {
					if (!set.contains(neighbor)) { // SKIP: already seen on this ring or an earlier one
						set.add(neighbor); // RECORD: mark when queued, so it is queued once
						queue.add(neighbor);
					}
				}
			}
			steps++; // the ring is finished
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
