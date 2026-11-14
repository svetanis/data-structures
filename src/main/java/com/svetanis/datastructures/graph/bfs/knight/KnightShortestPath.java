package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// Knight's shortest path on an n x n board (not a LeetCode problem; the unbounded board is 1197)
//
// Input: n -- the board is n x n; src and dst -- two squares, as Cell(row, col) with
// 0 <= row, col < n. A knight moves two squares in one direction and one square across, in any of
// 8 ways, and must stay on the board. Returns the least number of moves from src to dst, or an
// empty Optional when dst cannot be reached (on a 2 x 2 or 3 x 3 board some squares cannot).
//
// The one idea: one queue entry holds one square the knight can stand on. The queue is drained a
// whole ring at a time: the first ring is src itself (0 moves), the next ring is every new square
// one knight move away (1 move), and so on, so `dist` is the number of rings finished and no
// square needs a distance of its own. A square is marked seen when it is put in the queue, so no
// square is queued twice, and the first ring that contains dst is its shortest distance.
//
// Time: O(n^2) -- each of the n * n squares is queued once and tries 8 moves
// Space: O(n^2) -- the seen set and the queue hold each square at most once
//
// Siblings: KnightShortestPathPattern is the same search with the neighbours built by a helper;
// KnightMinMoves and KnightMinMovesHashMap are the same search that returns -1 for an unreachable dst.
public final class KnightShortestPath {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static Optional<Integer> ksp(int n, Cell src, Cell dst) {
		Set<Cell> set = new HashSet<>();
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src); // BASE: the start is seen, 0 moves away
		// one drain of the queue is one level, so every square dequeued
		// inside the inner loop is exactly `dist` moves from the source.
		// that is why the distance does not have to live on the node
		int dist = 0;
		while (!queue.isEmpty()) {
			for (int size = queue.size(); size > 0; size--) { // WALK: one whole ring
				Cell node = queue.poll();
				if (node.getX() == dst.getX() && node.getY() == dst.getY()) {
					return Optional.of(dist); // RETURN: the ring number is the distance
				}
				// check all 8 moves and enqueue
				// each valid movement in the queue
				for (int dir = 0; dir < dx.length; dir++) { // WALK: the 8 knight moves
					int x = node.getX() + dx[dir];
					int y = node.getY() + dy[dir];
					Cell next = new Cell(x, y);
					// mark at ENQUEUE: marking at poll lets the same square
					// sit in the queue several times over
					if (valid(x, y, n) && set.add(next)) { // SKIP: off the board, or already seen
						queue.add(next); // RECORD: the add above marked it
					}
				}
			}
			dist++; // the ring is finished
		}
		return Optional.empty(); // RETURN: the queue ran dry, dst cannot be reached
	}

	private static boolean valid(int x, int y, int n) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

	public static void main(String[] args) {
		Cell src1 = new Cell(0, 0);
		Cell dst1 = new Cell(4, 1);
		System.out.println(ksp(5, src1, dst1)); // Optional[3]

		Cell src2 = new Cell(0, 7);
		Cell dst2 = new Cell(7, 0);
		System.out.println(ksp(8, src2, dst2)); // Optional[6]

		// the start is the target
		System.out.println(ksp(8, src1, src1)); // Optional[0]

		// on a 3 x 3 board the centre square cannot be entered or left
		System.out.println(ksp(3, src1, new Cell(1, 1))); // Optional.empty
	}
}
