package com.svetanis.datastructures.graph.undirected.bipartite;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 785. Is Graph Bipartite?
//
// Input: g -- an undirected graph as an adjacency array: g[u] lists the vertices joined to u. On
// LeetCode: 1 <= g.length <= 100, 0 <= g[u].length < g.length, 0 <= g[u][i] <= g.length - 1,
// g[u] never contains u, holds no value twice, and if v is in g[u] then u is in g[v]. The graph
// may be disconnected. Returns true when the vertices can be split into two groups so that every
// edge joins one group to the other.
//
// The one idea: colors[u] holds the group of vertex u -- 0 or 1, or -1 while u has none. Take any
// vertex without a group, give it 0, and walk out from it in rings with a queue. Every neighbour
// of a vertex in group c has to be in group 1 - c: a neighbour with no group is given 1 - c, and
// one that already has group c closes an odd-length loop, so no split exists and the answer is
// false. A new walk is started from every vertex still without a group, because a disconnected
// graph is bipartite only if each piece is.
//
// Time: O(V + E) -- each vertex is queued once and each adjacency entry is read once.
// Space: O(V) -- the colours and the queue.
//
// Siblings: IsBipartiteDfs -- the same colouring, walked depth first with recursion.
// BipartiteGraphDfs takes a Graph object and looks only at the part reachable from one source.

public final class IsBipartiteBfs {

	public static boolean isBipartite(int[][] g) {
		int n = g.length;
		int[] colors = new int[n];
		Arrays.fill(colors, -1); // -1: no group yet
		for (int i = 0; i < n; i++) {
			if (colors[i] == -1) { // NEW PIECE: i is in a part not yet reached
				Deque<Integer> dq = new ArrayDeque<>();
				dq.offer(i);
				colors[i] = 0; // BASE: the first vertex of a piece takes group 0
				while (!dq.isEmpty()) {
					int node = dq.poll();
					for (int adj : g[node]) {
						if (colors[adj] == -1) {
							colors[adj] = 1 - colors[node]; // COLOR: the other group
							dq.offer(adj);
						} else if (colors[adj] == colors[node]) { // CLASH: same group on both ends of an edge
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2, 3 }, { 0, 2 }, { 0, 1, 3 }, { 0, 2 } };
		System.out.println(isBipartite(g1)); // false
		int[][] g2 = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };
		System.out.println(isBipartite(g2)); // true
	}
}
