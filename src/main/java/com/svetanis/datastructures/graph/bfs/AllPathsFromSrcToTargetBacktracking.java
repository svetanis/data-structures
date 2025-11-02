package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayList;
import java.util.List;

// 797. All Paths From Source to Target

public final class AllPathsFromSrcToTargetBacktracking {
	// Time Complexity: O(2^n * n)
	// Space Complexity: O(2^n * n)

	public static List<List<Integer>> allPaths(int[][] graph) {
		List<Integer> path = new ArrayList<>();
		path.add(0);
		List<List<Integer>> paths = new ArrayList<>();
		dfs(0, graph, path, paths);
		return paths;
	}

	private static void dfs(int index, int[][] graph, List<Integer> path, List<List<Integer>> paths) {
		if (index == graph.length - 1) {
			paths.add(new ArrayList<>(path));
			return;
		}
		for (int node : graph[index]) {
			path.add(node);
			dfs(node, graph, path, paths);
			path.remove(path.size() - 1);
		}
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 3 }, { 3 }, {} };
		System.out.println(allPaths(g1)); // [[0, 1, 3], [0, 2, 3]]

		// [[0, 4], [0, 3, 4], [0, 1, 4], [0, 1, 3, 4], [0, 1, 2, 3, 4]]
		int[][] g2 = { { 4, 3, 1 }, { 3, 2, 4 }, { 3 }, { 4 }, {} };
		System.out.println(allPaths(g2));
	}
}
