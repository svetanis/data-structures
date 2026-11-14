package com.svetanis.datastructures.graph.bfs.knight;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Cell;

// 1197. Minimum Knight Moves
//
// Input: x, y -- the target square on an infinite chessboard where a knight starts at [0, 0]. A
// knight moves two squares in one direction and one square across, in any of 8 ways. Returns the
// least number of moves to reach [x, y]. On LeetCode: -300 <= x, y <= 300 and |x| + |y| <= 300;
// every square can be reached.
//
// The one idea: one queue entry holds one square the knight can stand on, and the map holds, for
// every square seen, how many moves it took to first reach it. The queue hands out squares
// nearest-first, so the first time a square is put in the map is also its shortest distance; a
// square already in the map is skipped, so the map doubles as the "seen" mark. A new square is
// one move further than the square it was found from. The board has no edge, so the search ends
// only by finding the target -- which always exists.
//
// Time: O((|x| + |y|)^2) -- the squares settled before the target comes off the queue are all
// those within d knight moves of the origin, where d (the answer) grows in proportion to
// |x| + |y|, and the number of squares within d moves grows with d squared; for [x, 0] that is
// x^2 squares, not |x| * |y| = 0
// Space: O((|x| + |y|)^2) -- the map holds all of those squares
//
// Siblings: KnightMinMovesUnbounded counts rings instead of keeping a distance per square;
// KnightMinMovesUnboundedSubmit is the LeetCode submission on a fixed array;
// KnightMinMovesHashMap is this search on an n x n board.
public final class KnightMinMovesUnboundedHashMap {

	private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static int ksp(int x, int y) {
		Cell src = new Cell(0, 0);
		Cell dst = new Cell(x, y);
		return bfs(src, dst);
	}

	public static int bfs(Cell src, Cell dst) {
		Map<Cell, Integer> map = new HashMap<>();
		Queue<Cell> queue = new ArrayDeque<>();
		queue.add(src);
		map.put(src, 0); // BASE: the start is 0 moves from itself
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			if (curr.getRow() == dst.getRow() && curr.getCol() == dst.getCol()) {
				return map.get(curr); // RETURN: first time dst comes off the queue is its shortest
			}
			List<Cell> neighbors = neighbors(curr);
			for (Cell neighbor : neighbors) {
				if (!map.containsKey(neighbor)) { // SKIP: already reached by a route at least as short
					map.put(neighbor, map.get(curr) + 1); // RECORD: one move more than its parent
					queue.add(neighbor);
				}
			}
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
