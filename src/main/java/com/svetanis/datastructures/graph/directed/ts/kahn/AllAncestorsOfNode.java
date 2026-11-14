package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 2192. All Ancestors of a Node in a Directed Acyclic Graph
//
// Input: n -- the number of nodes, numbered 0 .. n - 1; edges -- pairs {from, to} of a directed
// acyclic graph. A node u is an ancestor of v if v can be reached from u by following edges.
// Returns: a list answer where answer[i] is the ancestors of node i in increasing order. On
// LeetCode: 1 <= n <= 1000, 0 <= edges.length <= min(2000, n * (n - 1) / 2), no repeated edge,
// the graph is acyclic.
//
// The one idea: "u is an ancestor of v" is the same as "v is reachable from u". So ask the question
// from u's side: from every node u in turn, walk forward and add u to the answer list of every node
// the walk reaches. The walk marks the nodes it has met, so each node is reached once per u. Going
// through u in increasing order means each answer list is built already sorted.
//
// Time: O(V * (V + E)) -- one walk of O(V + E) from each of the V nodes.
// Space: O(V + E) -- the graph, plus the answer, which can hold O(V^2) numbers.
//
// Siblings: none in this package.

public final class AllAncestorsOfNode {

	private int n;
	private List<Integer>[] graph;
	private List<List<Integer>> ancestors;

	public List<List<Integer>> ancestors(int n, int[][] edges) {
		this.n = n;
		this.ancestors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ancestors.add(new ArrayList<>());
		}
		buildGraph(edges);
		for (int node = 0; node < n; node++) { // START a walk from every node, smallest first
			bfs(node);
		}
		return ancestors;
	}

	private void bfs(int src) {
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(src);
		boolean[] visited = new boolean[n];
		visited[src] = true;
		while (!dq.isEmpty()) {
			int node = dq.poll();
			for (int child : graph[node]) {
				if (!visited[child]) { // MARK on entry, so each node is reached once per walk
					visited[child] = true;
					dq.offer(child);
					ancestors.get(child).add(src); // RECORD: src reaches child, so src is an ancestor of child
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void buildGraph(int[][] edges) {
		this.graph = new List[n];
		Arrays.setAll(graph, i -> new ArrayList<>());
		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];
			graph[from].add(to); // EDGE: from -> to
		}
	}

	public static void main(String[] args) {
		AllAncestorsOfNode aan = new AllAncestorsOfNode();
		int[][] edges1 = { { 0, 3 }, { 0, 4 }, { 1, 3 }, { 2, 4 }, { 2, 7 }, { 3, 5 }, { 3, 6 }, { 3, 7 }, { 4, 6 } };
		System.out.println(aan.ancestors(8, edges1)); // [[], [], [], [0, 1], [0, 2], [0, 1, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3]]
		int[][] edges2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 2, 3 }, { 2, 4 },
				{ 3, 4 } };
		System.out.println(aan.ancestors(5, edges2)); // [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3]]
	}
}
