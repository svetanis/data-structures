package com.svetanis.datastructures.graph.undirected.bipartite;

// 785. Is Graph Bipartite?
//
// Input: g -- an undirected graph as an adjacency array: g[u] lists the vertices joined to u. On
// LeetCode: 1 <= g.length <= 100, 0 <= g[u].length < g.length, 0 <= g[u][i] <= g.length - 1,
// g[u] never contains u, holds no value twice, and if v is in g[u] then u is in g[v]. The graph
// may be disconnected. Returns true when the vertices can be split into two groups so that every
// edge joins one group to the other.
//
// The one idea: colors[u] holds the group of vertex u -- 1 or 2, or 0 while u has none. Take any
// vertex without a group, give it 1, and walk out from it depth first. Every neighbour of a
// vertex in group c has to be in the other group, 3 - c: a neighbour with no group is given 3 - c
// and visited next, and one that already has group c closes an odd-length loop, so no split
// exists and the answer is false. A new walk is started from every vertex still without a group,
// because a disconnected graph is bipartite only if each piece is.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the colours, plus the recursion, at most V calls deep (V <= 100 on LeetCode).
//
// Siblings: IsBipartiteBfs -- the same colouring, walked in rings with a queue and no recursion.
// BipartiteGraphDfs takes a Graph object and looks only at the part reachable from one source.

public final class IsBipartiteDfs {

	public static boolean isBipartite(int[][] g) {
		int n = g.length;
		int[] colors = new int[n]; // 0: no group yet
		for (int node = 0; node < n; node++) {
			if (colors[node] == 0 && !dfs(g, colors, node, 1)) { // NEW PIECE: start it with group 1
				return false;
			}
		}
		return true;
	}

	private static boolean dfs(int[][] g, int[] colors, int node, int color) {
		colors[node] = color; // COLOR: give node its group
		for (int neighbor : g[node]) {
			if (colors[neighbor] == 0) {
				if (!dfs(g, colors, neighbor, 3 - color)) { // WALK: the other group
					return false;
				}
			} else if (colors[neighbor] == color) { // CLASH: same group on both ends of an edge
				return false;
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
