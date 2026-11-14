package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 886. Possible Bipartition
//
// Input: n -- people labelled 1..n; dislikes -- pairs [a, b] meaning a and b dislike each other and
// must not be in the same group. Returns true if everyone can be split into two groups with no
// disliking pair inside a group. On LeetCode: 1 <= n <= 2000, 0 <= dislikes.length <= 10^4,
// 1 <= a < b <= n, no pair repeated.
//
// The one idea: if x dislikes y and z, then y and z both go in the group opposite x, so y and z are
// in the SAME group as each other. Union-find records "same group": for each person, every person
// they dislike is merged with the first person they dislike. One element is one person (label - 1).
// The split is impossible exactly when some person ends up in the same group as someone they
// dislike -- that is the check px == py below.
//
// Time: O(n + d * alpha) -- d dislike pairs, each looked at from both ends; a find is near-constant
// with compression.
// Space: O(n + d) -- the neighbour lists and the parent array.

public final class PossibleBipartition {

	private int[] parent;

	public boolean bipartition(int n, int[][] dislikes) {
		init(n);
		List<Integer>[] graph = graph(n, dislikes); // BUILD: who dislikes whom
		for (int node = 0; node < n; node++) {
			for (int neighbor : graph[node]) {
				int px = find(node);
				int py = find(neighbor);
				if (px == py) {
					return false; // CONFLICT: node and someone it dislikes are in one group
				}
				int curr = graph[node].get(0);
				parent[py] = find(curr); // JOIN: this enemy with node's first enemy
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
			graph[node1].add(node2); // dislike goes both ways
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
			parent[x] = find(parent[x]); // WALK up, COMPRESS on the way back
		}
		return parent[x];
	}

	public static void main(String[] args) {
		PossibleBipartition pbp = new PossibleBipartition();
		int[][] g1 = { { 1, 2 }, { 1, 3 }, { 2, 4 } };
		System.out.println(pbp.bipartition(4, g1)); // true -- {1, 4} and {2, 3}

		int[][] g2 = { { 1, 2 }, { 1, 3 }, { 2, 3 } };
		System.out.println(pbp.bipartition(3, g2)); // false -- 1, 2, 3 dislike each other in a ring
	}
}
