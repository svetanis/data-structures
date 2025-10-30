package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.java.base.utils.Print;

// 1743. Restore the Array From Adjacent Pairs

public final class RestoreFromAdjacentPairs {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] restoreArray(int[][] pairs) {
		Map<Integer, List<Integer>> g = graph(pairs);
		int n = pairs.length + 1;
		int[] a = restoredInit(n, g);
		for (int i = 2; i < n; i++) {
			List<Integer> neighbors = g.get(a[i - 1]);
			boolean prev = neighbors.get(0) == a[i - 2];
			a[i] = prev ? neighbors.get(1) : neighbors.get(0);
		}
		return a;
	}

	private static int[] restoredInit(int n, Map<Integer, List<Integer>> g) {
		int[] a = new int[n];
		for (int key : g.keySet()) {
			if (g.get(key).size() == 1) {
				a[0] = key;
				a[1] = g.get(key).get(0);
			}
		}
		return a;
	}

	private static Map<Integer, List<Integer>> graph(int[][] pairs) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int[] pair : pairs) {
			int src = pair[0], dst = pair[1];
			map.computeIfAbsent(src, k -> new ArrayList<>()).add(dst);
			map.computeIfAbsent(dst, k -> new ArrayList<>()).add(src);
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 2, 1 }, { 3, 4 }, { 3, 2 } };
		Print.print(restoreArray(g1)); // 1 2 3 4

		int[][] g3 = { { 4, -2 }, { 1, 4 }, { -3, 1 } };
		Print.print(restoreArray(g3)); // -2 4 1 -3
	}
}
