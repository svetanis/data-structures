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
// The one idea: visited[node] has three states. null -- never looked at. false -- either on the
// walk we are in the middle of, or finished and found bad. true -- finished and found good.
// Meeting a node that is not null returns its value at once: false for "on the walk" is exactly
// the cycle answer (a walk that comes back to itself never stops), and false for "finished bad"
// is the memo. A node with no way out is a place a path stops, so it must be dst; it is never
// marked. Any other node is set to false on entry, then true once every out-edge has come back
// good; one bad edge returns false and leaves the false in place.
//
// Time: O(V + E) -- every node is expanded once; a later visit reads the state and stops.
// Space: O(V + E) -- the adjacency lists, the state array, and up to V nested calls.
//
// Siblings: AllPathsSrcToDst keeps the same knowledge in two arrays (dp and visited).

public final class AllPathsSrcToDstState {

	private int dst;
	private Boolean[] visited;
	private List<Integer>[] graph;

	public boolean leadsToDst(int n, int[][] edges, int src, int dst) {
		this.dst = dst;
		this.visited = new Boolean[n];
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
		if (visited[node] != null) {
			return visited[node]; // MEMO / CYCLE: settled before, or on the current walk (false)
		}

		if (graph[node].size() == 0) {
			return node == dst; // BASE: a path stops here, so it has to stop at dst
		}
		visited[node] = false; // MARK: on the current walk
		for (int next : graph[node]) {
			if (!dfs(next)) { // WALK: every out-edge must be good
				return false; // RETURN: one bad edge; the false stays as the memo
			}
		}
		visited[node] = true; // RECORD: all edges good
		return true;
	}

	public static void main(String[] args) {
		AllPathsSrcToDstState psd = new AllPathsSrcToDstState();
		int[][] edges1 = { { 0, 1 }, { 0, 2 } };
		System.out.println(psd.leadsToDst(3, edges1, 0, 2)); // false

		int[][] edges2 = { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 2, 1 } };
		System.out.println(psd.leadsToDst(4, edges2, 0, 3)); // false

		int[][] edges3 = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(psd.leadsToDst(4, edges3, 0, 3)); // true
	}
}
