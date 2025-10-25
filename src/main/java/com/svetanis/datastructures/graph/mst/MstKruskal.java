package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Minimum Spanning Tree: Kruskal's Algorithm

public final class MstKruskal {
	// Time Complexity: O(m log m)
	// Space Complexity: O(n)

	private UnionFind<Integer> dsu = new UnionFind<>();

	public int mst(int n, int[][] grid) {
		List<Edge> edges = edges(grid);
		int total = 0;
		int count = 0;
		for (Edge edge : edges) {
			if (dsu.find(edge.a) != dsu.find(edge.b)) {
				dsu.union(edge.a, edge.b);
				total += edge.weight;
				count++;
				if (count == n - 1) {
					break;
				}
			}
		}
		return total;
	}

	private List<Edge> edges(int[][] grid) {
		List<Edge> list = new ArrayList<>();
		for (int[] row : grid) {
			list.add(new Edge(row[0], row[1], row[2]));
		}
		Collections.sort(list, (a, b) -> a.weight - b.weight);
		return list;
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 2, 1 }, { 2, 5, 1 }, { 4, 5, 2 }, { 1, 5, 3 }, { 3, 2, 3 }, { 3, 4, 5 }, { 4, 1, 6 } };
		MstKruskal kruskal = new MstKruskal();
		System.out.println(kruskal.mst(5, grid)); // 7
	}

	private static class UnionFind<T> {

		private Map<T, T> map = new HashMap<>();

		public T find(T x) {
			T y = map.getOrDefault(x, x);
			if (y != x) {
				y = find(y);
				map.put(x, y);
			}
			return y;
		}

		public void union(T x, T y) {
			map.put(find(x), find(y));
		}
	}

	private static class Edge {

		private int a, b;
		private int weight;

		public Edge(int a, int b, int weight) {
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}
}
