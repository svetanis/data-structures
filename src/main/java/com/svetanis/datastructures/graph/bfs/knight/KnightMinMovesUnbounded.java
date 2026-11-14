package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Cell;

// 1197. Minimum Knight Moves
//
// Input: x, y -- the target square on an infinite chessboard where a knight starts at [0, 0]. A
// knight moves two squares in one direction and one square across, in any of 8 ways. Returns the
// least number of moves to reach [x, y]. On LeetCode: -300 <= x, y <= 300 and |x| + |y| <= 300;
// every square can be reached.
//
// The one idea: one queue entry holds one square the knight can stand on. The queue is drained a
// whole ring at a time: the first ring is [0, 0] itself (0 moves), the next ring is every new
// square one knight move away (1 move), and so on, so `steps` is the number of rings finished.
// A square is marked seen when it is queued, so none is queued twice, and the first ring that
// contains [x, y] is its shortest distance. The board has no edge, so the only thing that stops the
// search is finding the target -- which always exists, so the search always ends.
//
// Time: O((|x| + |y|)^2) -- the rings out to the target hold every square within d knight moves
// of the origin, where d (the answer) grows in proportion to |x| + |y|, and the number of
// squares within d moves grows with d squared; for [x, 0] that is x^2 squares, not |x| * |y| = 0
// Space: O((|x| + |y|)^2) -- the seen set holds all of those squares
//
// Siblings: KnightMinMovesUnboundedHashMap keeps a distance per square instead of counting rings;
// KnightMinMovesUnboundedSubmit is the LeetCode submission on a fixed array; KnightMinMoves is
// this search on an n x n board.
public final class KnightMinMovesUnbounded {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int x, int y) {
		Cell src = new Cell(0, 0);
		Cell dst = new Cell(x, y);
		return bfs(src, dst);
	}

	public static int bfs(Cell src, Cell dst) {
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
				List<Cell> neighbors = neighbors(curr);
				for (Cell neighbor : neighbors) {
					if (!set.contains(neighbor)) { // SKIP: already seen on this ring or an earlier one
						set.add(neighbor); // RECORD: mark when queued, so it is queued once
						queue.add(neighbor);
					}
				}
			}
			steps++; // the ring is finished
		}
		return -1; // RETURN: never reached on an infinite board
	}

	private static List<Cell> neighbors(Cell curr) {
		List<Cell> list = new ArrayList<>();
		for (int dir = 0; dir < dx.length; dir++) { // WALK: the 8 knight moves, none can leave the board
			int x = curr.getRow() + dx[dir];
			int y = curr.getCol() + dy[dir];
			list.add(new Cell(x, y));
		}
		return list;
	}

	public static void main(String[] args) {
		System.out.println(ksp(4, 1)); // 3
		System.out.println(ksp(7, 0)); // 5
		System.out.println(ksp(2, 1)); // 1
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(1, 1)); // 2
		System.out.println(ksp(-2, -1)); // 1
	}
}
