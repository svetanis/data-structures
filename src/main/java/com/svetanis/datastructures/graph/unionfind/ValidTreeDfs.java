package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.List;

// 261. Graph Valid Tree

public final class ValidTreeDfs {
	// Time Complexity: O(n)

	private List<Integer>[] g;

	public boolean validTree(int n, int[][] edges) {
		this.g = graph(n, edges);
		boolean[] visited = new boolean[n];
		if (hasCycle(0, -1, visited)) {
			return false;
		}
		for (boolean v : visited) {
			if (!v) {
				return false;
			}
		}
		return true;
	}

	private boolean hasCycle(int node, int parent, boolean[] visited) {
		visited[node] = true;
		for (int child : g[node]) {
			if (!visited[child]) {
				if (hasCycle(child, node, visited)) {
					return true;
				}
			} else if (visited[child] && child != parent) {
				return true;
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
			g[src].add(dst);
			g[dst].add(src);
		}
		return g;
	}

	public static void main(String[] args) {
		ValidTreeDfs vt = new ValidTreeDfs();
		int[][] g = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
		System.out.println(vt.validTree(4, g)); // true
	}
}
