package com.svetanis.datastructures.graph.bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// Snake and Ladder: fewest dice throws from the first cell to the last.
//
// Input: n -- the number of cells, numbered 0 to n - 1; moves -- an array of size n where moves[i] is
// -1 when cell i has no snake or ladder, and otherwise the cell that the snake or ladder at i takes
// you to. A throw moves you 1 to 6 cells forward (never past cell n - 1), and if you land on a cell
// with a snake or ladder you ride it at once. Returns the fewest throws that take you from cell 0 to
// cell n - 1, or -1 if cell n - 1 cannot be reached. Assumes n >= 1 and that cells 0 and n - 1 have
// no snake or ladder; a ride ends where it lands, it is not followed by a second ride.
//
// The one idea: one queue entry is the cell you stand on after a throw (after any ride) together with
// the number of throws it took to get there. From a cell the next throw can land on the six cells
// ahead, so every cell has at most six next cells, and every throw costs the same one, which is the
// situation in which breadth first search gives the fewest throws: the first time the last cell comes
// off the queue, no shorter sequence of throws exists. visited marks the cell the DICE landed on,
// before any ride: a cell that dice landed on once leads to the same place whenever it is landed on
// again, and the first landing is on a shorter or equal throw count, so later landings add nothing.
//
// Time: O(n) -- each landing cell is queued once and a cell has six throws.
// Space: O(n) -- visited and the queue.
//
// Siblings: SnakeLadder909 is LeetCode 909, the same game with the cells arranged in a zig-zag
// square board, numbered from 1.

public final class SnakeLadder {

	public static int shortestPath(int n, int[] moves) {
		boolean[] visited = new boolean[n]; // LANDED: cells the dice has landed on before a ride
		visited[0] = true;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(new Node(0, 0)); // START: cell 0, no throws yet
		while (!queue.isEmpty()) {
			Node front = queue.poll(); // TAKE: the cell with the fewest throws not yet expanded
			int v = front.val;
			if (v == n - 1) {
				return front.dist; // RETURN: the first time the last cell comes off is the fewest
			}
			for (int j = v + 1; j <= (v + 6) && j < n; j++) { // THROW: land 1 to 6 cells ahead
				if (!visited[j]) {
					visited[j] = true;
					Node node = new Node();
					node.dist = front.dist + 1;
					// if cell j has a snake or ladder, its other end is where you stand next
					if (moves[j] != -1) {
						node.val = moves[j]; // RIDE: the snake or ladder carries you to moves[j]
					} else {
						node.val = j;
					}
					queue.offer(node);
				}
			}
		}
		return -1; // the last cell cannot be reached
	}

	public static void main(String[] args) {
		int n = 30;
		int[] moves = new int[n];
		createMoves(n, moves);
		System.out.println(shortestPath(n, moves)); // 3

		int[] flat = new int[n];
		Arrays.fill(flat, -1);
		System.out.println(shortestPath(n, flat)); // 5
	}

	private static void createMoves(int n, int[] moves) {
		for (int i = 0; i < n; i++) {
			moves[i] = -1;
		}

		// LADDERS
		moves[2] = 21;
		moves[4] = 7;
		moves[10] = 25;
		moves[19] = 28;

		// SNAKES
		moves[26] = 0;
		moves[20] = 8;
		moves[16] = 3;
		moves[18] = 6;
	}

	private static class Node {
		private int val; // vertex num
		private int dist; // distance of this vertex from src

		public Node() {}

		public Node(int v, int dist) {
			this.val = v;
			this.dist = dist;
		}
	}
}
