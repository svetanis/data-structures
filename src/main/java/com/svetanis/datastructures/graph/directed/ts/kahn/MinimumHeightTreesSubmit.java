package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 310. Minimum Height Trees

public final class MinimumHeightTreesSubmit {
	// Time Complexity: O(V + E)

	public static List<Integer> mht(int v, int[][] edges) {
		List<Integer> list = new ArrayList<>();
		if (v == 1) {
			list.add(0);
			return list;
		}
		int[] inDegree = new int[v];
		List<Integer>[] graph = graphInit(v);
		buildGraph(v, edges, inDegree, graph);
		Queue<Integer> queue = leaves(v, inDegree);
		// remove leaves level by level
		// until left with 1 or 2 nodes
		while (!queue.isEmpty()) {
			list.clear();
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int leaf = queue.poll();
				list.add(leaf);
				for (int neighbor : graph[leaf]) {
					inDegree[neighbor]--;
					if (inDegree[neighbor] == 1) {
						queue.add(neighbor);
					}
				}
			}
		}
		return list;
	}

	private static Queue<Integer> leaves(int v, int[] inDegree) {
		Queue<Integer> queue = new LinkedList<>();
		// all leaves (all nodes with only 1 in-degree)
		for (int i = 0; i < v; i++) {
			if (inDegree[i] == 1) {
				queue.add(i);
			}
		}
		return queue;
	}

	private static void buildGraph(int v, int[][] edges, int[] inDegree, List<Integer>[] list) {
		// this is undirected graph
		for (int[] edge : edges) {
			int node1 = edge[0];
			int node2 = edge[1];
			// add a link for both nodes
			list[node1].add(node2);
			list[node2].add(node1);
			// increment the in-degrees for both nodes
			inDegree[node1]++;
			inDegree[node2]++;
		}
	}

	private static List<Integer>[] graphInit(int v) {
		List<Integer>[] list = new List[v];
		for (int i = 0; i < v; i++) {
			list[i] = new ArrayList<>();
		}
		return list;
	}

	public static void main(String[] args) {
		int[][] m0 = { { 0, 1 }, { 1, 2 }, { 1, 3 }, { 2, 4 } };
		int[][] m1 = { { 0, 1 }, { 0, 2 }, { 2, 3 } };
		int[][] m2 = { { 0, 1 }, { 1, 2 }, { 1, 3 } };
		System.out.println(mht(5, m0)); // 1, 2
		System.out.println(mht(4, m1)); // 0, 2
		System.out.println(mht(4, m2)); // 1
	}
}
