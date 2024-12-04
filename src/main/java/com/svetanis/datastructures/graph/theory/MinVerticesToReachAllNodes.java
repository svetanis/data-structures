package com.svetanis.datastructures.graph.theory;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// 1557. Minimum Number of Vertices to Reach All Nodes

public final class MinVerticesToReachAllNodes {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	public static List<Integer> smallestSetOfVertices(int n, List<List<Integer>> edges) {
		int[] inDegree = new int[n];
		for (List<Integer> edge : edges) {
			int to = edge.get(1);
			inDegree[to]++;
		}
		return vertices(n, inDegree);
	}

	private static List<Integer> vertices(int n, int[] inDegree) {
		List<Integer> list = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			if (inDegree[node] == 0) {
				list.add(node);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		List<List<Integer>> e1 = new ArrayList<>();
		e1.add(asList(0, 1));
		e1.add(asList(0, 2));
		e1.add(asList(2, 5));
		e1.add(asList(3, 4));
		e1.add(asList(4, 2));
		System.out.println(smallestSetOfVertices(6, e1)); // 0,3

		List<List<Integer>> e2 = new ArrayList<>();
		e2.add(asList(0, 1));
		e2.add(asList(2, 1));
		e2.add(asList(3, 1));
		e2.add(asList(1, 4));
		System.out.println(smallestSetOfVertices(5, e2)); // 0,2,3
	}
}
