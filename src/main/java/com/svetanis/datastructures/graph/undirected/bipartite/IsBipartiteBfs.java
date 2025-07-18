package com.svetanis.datastructures.graph.undirected.bipartite;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// 785. Is Graph Bipartite?

public final class IsBipartiteBfs {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V)

	public static boolean isBipartite(int[][] g) {
		int n = g.length;
		int[] colors = new int[n];
		Arrays.fill(colors, -1);
		for (int i = 0; i < n; i++) {
			if (colors[i] == -1) {
				Deque<Integer> dq = new ArrayDeque<>();
				dq.offer(i);
				colors[i] = 0;
				while (!dq.isEmpty()) {
					int node = dq.poll();
					for (int adj : g[node]) {
						if (colors[adj] == -1) {
							colors[adj] = 1 - colors[node];
							dq.offer(adj);
						} else if (colors[adj] == colors[node]) {
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
