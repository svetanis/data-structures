package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.List;

// 261. Graph Valid Tree
//
// Input: n -- nodes labelled 0..n-1; edges -- pairs [a, b], each a wire between two nodes. Returns
// true if the edges make a tree: connected, with no loop. On LeetCode: 1 <= n <= 2000,
// 0 <= edges.length <= 5000, a != b, no edge repeated.
//
// The one idea: no union-find here; walk instead. Start a depth-first search at node 0, passing
// along the node we came from. Meeting an already-visited node that is NOT the one we came from
// means a second route to it, which is a loop. When the search ends, every node must have been
// visited, or the graph is in several pieces. With n >= 1 node 0 always exists.
//
// Time: O(n + e) -- the neighbour lists take e steps; each node is visited once and each edge is
// looked at twice.
// Space: O(n + e) -- the lists and the visited array; the recursion is at most n deep.
//
// Siblings: ValidTree and ValidTreeSubmit decide it with union-find, counting groups.

public final class ValidTreeDfs {

	private List<Integer>[] g;

	public boolean validTree(int n, int[][] edges) {
		this.g = graph(n, edges);
		boolean[] visited = new boolean[n];
		if (hasCycle(0, -1, visited)) {
			return false; // a loop
		}
		for (boolean v : visited) {
			if (!v) {
				return false; // a node the search never reached: more than one piece
			}
		}
		return true;
	}

	private boolean hasCycle(int node, int parent, boolean[] visited) {
		visited[node] = true; // MARK
		for (int child : g[node]) {
			if (!visited[child]) {
				if (hasCycle(child, node, visited)) { // WALK on
					return true;
				}
			} else if (visited[child] && child != parent) {
				return true; // LOOP: seen already, and not the edge we arrived by
			}
		}
		return false;
	}

	private List<Integer>[] graph(int n, int[][] edges) {
		List<Integer>[] g = new List[n];
		for (int i = 0; i < n; i++) {
			g[i] = new ArrayList<>();
		}
		for (int[] edge : edges) {
			int src = edge[0];
			int dst = edge[1];
			g[src].add(dst); // BUILD: the wire is usable in both directions
			g[dst].add(src);
		}
		return g;
	}

	public static void main(String[] args) {
		ValidTreeDfs vt = new ValidTreeDfs();
		int[][] g = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, g)); // true -- a chain of four nodes
	}
}
