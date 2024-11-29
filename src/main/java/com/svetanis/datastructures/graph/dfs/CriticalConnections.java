package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1192. Critical Connections in a Network

public final class CriticalConnections {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V + E)

	public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		int[] lowest = new int[n];
		int[] discovery = new int[n];
		List<Integer>[] graph = graph(n, connections);
		List<List<Integer>> list = new ArrayList<>();
		dfs(graph, 0, -1, 0, discovery, lowest, list);
		return list;
	}

	private static void dfs(List<Integer>[] g, int node, int parent, int current, int[] discovery, int[] lowest,
			List<List<Integer>> list) {
		current++;
		discovery[node] = lowest[node] = current;

		for (int neighbor : g[node]) {
			if (neighbor == parent) {
				continue;
			}
			// not visited yet
			if (discovery[neighbor] == 0) {
				dfs(g, neighbor, node, current, discovery, lowest, list);
				lowest[node] = Math.min(lowest[node], lowest[neighbor]);
				// low time of neighbor is greater than
				// the discovery time of current node
				// this is a critical edge
				if (lowest[neighbor] > discovery[node]) {
					list.add(Arrays.asList(node, neighbor));
				}
			} else {
				// neighbor has been visited
				lowest[node] = Math.min(lowest[node], discovery[neighbor]);
			}
		}
	}

	private static List<Integer>[] graph(int n, List<List<Integer>> connections) {
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (List<Integer> edge : connections) {
			int src = edge.get(0);
			int dst = edge.get(1);
			graph[src].add(dst);
			graph[dst].add(src);
		}
		return graph;
	}

	public static void main(String[] args) {
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(0, 1));
		list1.add(asList(1, 2));
		list1.add(asList(2, 0));
		list1.add(asList(1, 3));
		System.out.println(criticalConnections(4, list1)); // 1,3

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(0, 1));
		System.out.println(criticalConnections(2, list2)); // 0,1
	}
}
