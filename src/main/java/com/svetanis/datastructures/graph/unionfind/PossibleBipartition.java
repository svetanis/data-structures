package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 886. Possible Bipartition

public final class PossibleBipartition {

	private int[] parent;

	public boolean bipartition(int n, int[][] dislikes) {
		init(n);
		List<Integer>[] graph = graph(n, dislikes);
		for (int node = 0; node < n; node++) {
			for (int neighbor : graph[node]) {
				int px = find(node);
				int py = find(neighbor);
				if (px == py) {
					return false;
				}
				int curr = graph[node].get(0);
				parent[py] = find(curr);
			}
		}
		return true;
	}

	private List<Integer>[] graph(int n, int[][] dislikes) {
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (int[] edge : dislikes) {
			int node1 = edge[0] - 1;
			int node2 = edge[1] - 1;
			graph[node1].add(node2);
			graph[node2].add(node1);
		}
		return graph;
	}

	private void init(int n) {
		this.parent = new int[n];
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	private int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	public static void main(String[] args) {
		PossibleBipartition pbp = new PossibleBipartition();
		int[][] g1 = { { 1, 2 }, { 1, 3 }, { 2, 4 } };
		System.out.println(pbp.bipartition(4, g1)); // true

		int[][] g2 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(pbp.bipartition(3, g2)); // false
	}
}
