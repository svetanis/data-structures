package com.svetanis.datastructures.graph.degree;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

// 1557. Minimum Number of Vertices to Reach All Nodes
//
// Input: n vertices numbered 0..n-1, and edges, a list of directed edges [from, to] forming an
// acyclic graph (a DAG). On LeetCode: 2 <= n <= 10^5, 1 <= edges.length <= min(10^5, n * (n - 1) /
// 2), from != to, and all pairs are different.
// Returns: the smallest set of vertices from which every vertex can be reached by following edges.
// It is unique; this code lists it in increasing order.
//
// The one idea: a vertex that has an edge coming IN can be reached from the vertex at the other
// end of that edge, so it never needs to be in the set. A vertex with no edge coming in cannot be
// reached from anywhere else, so it must be in the set. And these vertices are enough: following
// edges backwards from any vertex must stop (the graph has no cycle), and it can only stop at a
// vertex with nothing coming in. So the answer is exactly the vertices whose in-degree is 0.
//
// Time: O(n + m) -- one pass over the m edges to count in-degrees, one pass over the n vertices.
// Space: O(n) -- the in-degree array and the answer.
//
// Siblings: none.

public final class MinVerticesToReachAllNodes {

	public static List<Integer> smallestSetOfVertices(int n, List<List<Integer>> edges) {
		int[] inDegree = new int[n]; // CARDS: inDegree[v] -- how many edges end at v
		for (List<Integer> edge : edges) {
			int to = edge.get(1);
			inDegree[to]++; // COUNT: one more edge coming in
		}
		return vertices(n, inDegree);
	}

	private static List<Integer> vertices(int n, int[] inDegree) {
		List<Integer> list = new ArrayList<>();
		for (int node = 0; node < n; node++) {
			if (inDegree[node] == 0) {
				list.add(node); // TAKE: nothing leads here, so it must be a start
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
		System.out.println(smallestSetOfVertices(6, e1)); // [0, 3]

		List<List<Integer>> e2 = new ArrayList<>();
		e2.add(asList(0, 1));
		e2.add(asList(2, 1));
		e2.add(asList(3, 1));
		e2.add(asList(1, 4));
		System.out.println(smallestSetOfVertices(5, e2)); // [0, 2, 3]
	}
}
