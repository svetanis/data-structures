package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

// 2101. Detonate the Maximum Bombs
//
// Input: bombs[i] = {x, y, r}: a bomb at point (x, y) whose blast reaches every point within
// distance r (a point exactly at distance r is hit). Setting off one bomb sets off every bomb
// inside its blast, and those set off the bombs inside theirs, and so on.
// On LeetCode: 1 <= n <= 100, 1 <= x, y, r <= 10^5.
// Returns the largest number of bombs that go off when you choose the best single bomb to start.
//
// The one idea: one node is a bomb, and the arrow i -> j means j sits inside i's blast. The
// arrows go ONE WAY -- a big bomb can reach a small one that cannot reach back -- so union-find is
// wrong (A -> B <- C would be one group of 3, but no start sets off all three). Instead, a search
// from every bomb in turn, each with its own marks, counts what that start sets off; the best start
// wins. Two roles in the arrow test: i explodes (its centre and its radius), j is checked (its
// centre only). The test is done on squares, dx^2 + dy^2 <= r^2, so no square root is taken; the
// squares reach 2 * 10^10, past int, so everything is long before it is multiplied.
//
// Time: O(n^3) -- the arrows are n^2 pairs; each of the n searches walks at most all of them.
// Space: O(n^2) -- the arrow lists; a search adds O(n).

public final class MaximumDetonation {

	public static int maxDetonation(int[][] bombs) {
		int n = bombs.length;
		List<Integer>[] graph = graph(bombs); // ARROWS: graph[i] = the bombs i's blast reaches
		int best = 0;
		for (int start = 0; start < n; start++) { // START: a fresh search from every bomb
			boolean[] visited = new boolean[n];
			Queue<Integer> dq = new ArrayDeque<>();
			visited[start] = true;
			dq.offer(start);
			int count = 0;
			while (!dq.isEmpty()) {
				int bomb = dq.poll(); // TAKE: a bomb that has just gone off
				count++; // COUNT
				for (int next : graph[bomb]) {
					if (!visited[next]) {
						visited[next] = true; // mark at enqueue: no bomb is queued twice
						dq.offer(next);
					}
				}
			}
			best = Math.max(best, count); // BEST: keep the largest chain
		}
		return best;
	}

	@SuppressWarnings("unchecked")
	private static List<Integer>[] graph(int[][] bombs) {
		int n = bombs.length;
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int i = 0; i < n; i++) { // bomb i EXPLODES: its centre, its radius
			long x = bombs[i][0], y = bombs[i][1], r = bombs[i][2];
			for (int j = 0; j < n; j++) { // bomb j is CHECKED: its centre only
				if (i == j) {
					continue; // skip, not stop: the loop goes on past i
				}
				long dx = bombs[j][0] - x, dy = bombs[j][1] - y;
				if (dx * dx + dy * dy <= r * r) { // BLAST: j inside or exactly on i's circle
					graph[i].add(j);
				}
			}
		}
		return graph;
	}

	public static void main(String[] args) {
		int[][] grid = { { 2, 1, 3 }, { 6, 1, 4 } };
		System.out.println(maxDetonation(grid)); // 2

		int[][] grid1 = { { 1, 1, 5 }, { 10, 10, 5 } };
		System.out.println(maxDetonation(grid1)); // 1

		int[][] grid2 = { { 1, 2, 3 }, { 2, 3, 1 }, { 3, 4, 2 }, { 4, 5, 3 }, { 5, 6, 4 } };
		System.out.println(maxDetonation(grid2)); // 5

		int[][] grid3 = { { 1, 1, 100000 }, { 100000, 100000, 1 } };
		System.out.println(maxDetonation(grid3)); // 1 -- the squares overflow int
	}
}
