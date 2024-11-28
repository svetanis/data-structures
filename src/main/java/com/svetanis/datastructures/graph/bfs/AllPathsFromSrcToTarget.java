package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

// 797. All Paths From Source to Target

public final class AllPathsFromSrcToTarget {
	// Time Complexity: O(2^n * n)
	// Space Complexity: O(2^n * n)

	public static List<List<Integer>> allPaths(int[][] graph) {
		int dst = graph.length - 1;
		Queue<List<Integer>> queue = new ArrayDeque<>();
		queue.add(Arrays.asList(0));
		List<List<Integer>> paths = new ArrayList<>();
		while (!queue.isEmpty()) {
			List<Integer> path = queue.poll();
			int node = path.get(path.size() - 1);
			if (node == dst) {
				paths.add(path);
			} else {
				for (int neighbour : graph[node]) {
					List<Integer> next = new ArrayList<>(path);
					next.add(neighbour);
					queue.add(next);
				}
			}
		}
		return paths;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 1, 2 }, { 3 }, { 3 }, {} };
		System.out.println(allPaths(g1)); // [[0, 1, 3], [0, 2, 3]]

		// [[0, 4], [0, 3, 4], [0, 1, 4], [0, 1, 3, 4], [0, 1, 2, 3, 4]]
		int[][] g2 = { { 4, 3, 1 }, { 3, 2, 4 }, { 3 }, { 4 }, {} };
		System.out.println(allPaths(g2));
	}
}
