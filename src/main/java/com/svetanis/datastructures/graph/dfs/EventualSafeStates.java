package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.List;

// 802. Find Eventual Safe States
//
// Input: grid -- a directed graph on nodes 0 .. n - 1; grid[i] lists the nodes i points to. A
// terminal node has no outgoing edge; a node is safe when every path that starts at it reaches a
// terminal node, so no path from it can run round a cycle. Returns
// the safe nodes in increasing order. On LeetCode: n == graph.length, 1 <= n <= 10^4,
// 0 <= graph[i].length <= n, graph[i] is sorted with no repeats, no self-loops, at most
// 4 * 10^4 edges in all.
//
// The one idea: colors[node] says what the walk knows about one node. 0 -- not reached yet.
// 1 -- on the walk we are in the middle of (grey). 2 -- finished and found safe (black). Meeting
// a grey node means the walk has come round to a node it is still inside, so there is a cycle and
// the whole route is unsafe. Meeting a black node is a settled answer: safe. A node still at 0
// is entered, set to 1, and every one of its edges must lead to a safe node; if they all do, it
// becomes 2. A node that fails is left at 1, which is correct: any later visit meets 1 and
// answers false, the same answer the node would give again.
//
// Time: O(N + E) -- each node is entered once, each edge is looked at once, then answers are reused.
// Space: O(N) -- the colors, and a recursion as deep as the longest unbroken route (at most N).
//
// Siblings: AllPathsSrcToDstState uses the same three states for a different question.

public final class EventualSafeStates {

	public static List<Integer> safeStates(int[][] grid) {
		int n = grid.length;
		int[] colors = new int[n];
		List<Integer> list = new ArrayList<>();
		for (int src = 0; src < n; src++) {
			if (dfs(grid, src, colors)) {
				list.add(src); // TAKE: this node is safe
			}
		}
		return list;
	}

	private static boolean dfs(int[][] g, int src, int[] colors) {
		// node already visited
		if (colors[src] > 0) {
			return colors[src] == 2; // MEMO / CYCLE: black is safe, grey is a cycle
		}
		// mark node as visited (color 1)
		colors[src] = 1; // MARK: grey, on the current walk
		for (int neighbor : g[src]) {
			if (!dfs(g, neighbor, colors)) { // WALK: every edge must lead to a safe node
				return false;
			}
		}
		// all connected nodes are safe
		// mark node as safe (color 2)
		colors[src] = 2; // RECORD: black, finished and safe
		return true;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 2, 3 }, { 5 }, { 0 }, { 5 }, {}, {} };
		System.out.println(safeStates(g1)); // [2, 4, 5, 6]
		int[][] g2 = { { 1, 2, 3, 4 }, { 1, 2 }, { 3, 4 }, { 0, 4 }, {} };
		System.out.println(safeStates(g2)); // [4]
	}
}
