package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.svetanis.datastructures.graph.unionfind.UnionFind;

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
			if (!dsu.isSame(a, b)) {
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
}
