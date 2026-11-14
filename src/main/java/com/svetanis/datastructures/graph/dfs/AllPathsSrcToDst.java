package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1059. All Paths from Source Lead to Destination
//
// Input: n nodes numbered 0 .. n - 1; edges -- directed edges [from, to]; src and dst, two nodes.
// The graph may have self-loops and parallel edges. Returns true when every path that starts at
// src ends at dst, and a path that can never stop (it runs round a cycle) counts as ending
// nowhere. On LeetCode: 1 <= n <= 10^4, 0 <= edges.length <= 10^4, every node is in 0 .. n - 1.
//
// The one idea: two arrays hold what is known about one node. dp[node] is the answer to "do all
// paths that start at this node end at dst?" -- 0 not known yet, 1 yes, -1 no. visited[node]
// says this node is on the walk we are in the middle of. A node with no way out is a place a
// path stops, so it must be dst. A node whose dp is settled gives its answer at once. A node
// that is visited with dp still 0 is on the current walk, so the walk has gone round a cycle
// and never stops: false. Otherwise try every out-edge; one bad edge makes the node bad (-1),
// all good edges make it good (1). visited is never cleared, and that is safe: a node is
// visited and unsettled only while its own call is still running.
//
// Time: O(V + E) -- every node is expanded once; a later visit reads dp and stops.
// Space: O(V + E) -- the adjacency lists, the two arrays, and up to V nested calls.
//
// Siblings: AllPathsSrcToDstState does the same walk with one Boolean[] (null = unseen,
// false = on the walk or finished bad, true = finished good) instead of dp and visited.

public final class AllPathsSrcToDst {

	private int dst;
	private int[] dp;
	private boolean[] visited;
	private List<Integer>[] graph;

	public boolean leadsToDst(int n, int[][] edges, int src, int dst) {
		this.dst = dst;
		this.dp = new int[n];
		this.visited = new boolean[n];
		this.graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int[] edge : edges) { // BUILD: out-edges per node
			int from = edge[0];
			int to = edge[1];
			this.graph[from].add(to);
		}
		return dfs(src);
	}

	private boolean dfs(int node) {
		if (graph[node].size() == 0) {
			return node == dst; // BASE: a path stops here, so it has to stop at dst
		}
		if (dp[node] != 0) {
			return dp[node] == 1; // MEMO: settled before
		}
		// no second size() == 0 test here: the first if above already
		// returned for exactly that case, so repeating it only invites the
		// reader to think the two are asking different questions
		if (visited[node]) {
			return false; // CYCLE: on the current walk, so it never stops
		}
		visited[node] = true; // MARK: on the current walk
		for (int next : graph[node]) {
			if (!dfs(next)) { // WALK: every out-edge must be good
				dp[node] = -1; // RECORD: one bad edge makes the node bad
				return false;
			}
		}
		dp[node] = 1; // RECORD: all edges good
		return true;
	}

	public static void main(String[] args) {
		AllPathsSrcToDst psd = new AllPathsSrcToDst();
		int[][] edges1 = { { 0, 1 }, { 0, 2 } };
		System.out.println(psd.leadsToDst(3, edges1, 0, 2)); // false

		int[][] edges2 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 2, 1 } };
		System.out.println(psd.leadsToDst(4, edges2, 0, 3)); // false

		int[][] edges3 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(psd.leadsToDst(4, edges3, 0, 3)); // true
	}
}
