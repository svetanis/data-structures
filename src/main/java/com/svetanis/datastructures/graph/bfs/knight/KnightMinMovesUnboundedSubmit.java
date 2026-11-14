package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.Queue;

// 1197. Minimum Knight Moves
//
// Input: x, y -- the target square on an infinite chessboard where a knight starts at [0, 0]. A
// knight moves two squares in one direction and one square across, in any of 8 ways. Returns the
// least number of moves to reach [x, y]. On LeetCode: -300 <= x, y <= 300 and |x| + |y| <= 300;
// every square can be reached.
//
// The one idea: one queue entry holds one square, as the pair {row, col}, with the origin moved to
// the middle of a 621 x 621 array so that negative coordinates become array positions (310 is
// the origin; the target is shifted the same way). The array is the "seen" mark. The queue is
// drained a whole ring at a time, so `steps` counts the rings finished, and the first ring that
// holds the target is its distance. The array stands in for the infinite board: a path to any
// target allowed by the constraints stays well inside it, and a square outside the array is
// simply never visited.
//
// Time: O(621^2) -- the array is allocated whole on every call and at most 621 * 621 squares are
// queued once; the search itself settles about (|x| + |y|)^2 squares, as in KnightMinMovesUnbounded
// Space: O(621^2) -- the visited array
//
// Siblings: KnightMinMovesUnbounded and KnightMinMovesUnboundedHashMap solve the same problem on
// the unbounded board with a set / a map of Cell objects, with no fixed array.
public final class KnightMinMovesUnboundedSubmit {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int x, int y) {
		x += 310; // shift the target so the origin sits at the middle of the array
		y += 310;
		boolean[][] visited = new boolean[621][621];
		visited[310][310] = true; // BASE: the origin is seen
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] { 310, 310 });
		int steps = 0;
		while (!queue.isEmpty()) {
			int size = queue.size(); // frozen: exactly one ring is drained per pass
			for (int i = 0; i < size; i++) { // WALK: one whole ring
				int[] curr = queue.poll();
				if (curr[0] == x && curr[1] == y) {
					return steps; // RETURN: the ring number is the distance
				}
				for (int dir = 0; dir < dx.length; dir++) { // WALK: the 8 knight moves
					int row = curr[0] + dx[dir];
					int col = curr[1] + dy[dir];
					if (row >= 0 && col >= 0 && row < visited.length && col < visited[0].length && !visited[row][col]) {
						visited[row][col] = true; // RECORD: mark when queued, so it is queued once
						queue.add(new int[] { row, col });
					}
				}
			}
			steps++; // the ring is finished
		}
		return -1; // RETURN: only for a target outside the array
	}

	public static void main(String[] args) {
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(4, 1)); // 3
		System.out.println(ksp(7, 0)); // 5
		System.out.println(ksp(2, 1)); // 1
		System.out.println(ksp(5, 5)); // 4
		System.out.println(ksp(1, 1)); // 2
		System.out.println(ksp(-2, -1)); // 1
	}
}
