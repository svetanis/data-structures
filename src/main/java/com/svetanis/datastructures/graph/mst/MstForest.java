package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Minimum Spanning Tree | Forests

public final class MstForest {
	// Time Complexity: O(m log n)
	// Space Complexity: O(n)

	private UnionFind<Integer> dsu = new UnionFind<>();

	public int mst(int n, int[][] grid) {
		int total = 0;
		List<List<Integer>> edges = edges(grid);
		for (List<Integer> edge : edges) {
			int a = edge.get(0), b = edge.get(1);
			int weight = edge.get(2);
			if (dsu.find(a) != dsu.find(b)) {
				dsu.union(a, b);
				total += weight;
			}
		}
		return total;
	}

	private List<List<Integer>> edges(int[][] grid) {
		List<List<Integer>> list = new ArrayList<>();
		for (int[] row : grid) {
			list.add(Arrays.asList(row[0], row[1], row[2]));
		}
		Collections.sort(list, (a, b) -> a.get(2).compareTo(b.get(2)));
		return list;
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 2, 1 }, { 2, 4, 2 }, { 3, 5, 3 }, { 4, 4, 4 } };
		MstForest kruskal = new MstForest();
		System.out.println(kruskal.mst(5, grid)); // 6
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
}
