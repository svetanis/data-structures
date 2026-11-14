package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 1743. Restore the Array From Adjacent Pairs
//
// Input: pairs -- the n - 1 pairs of neighbours of a lost array of n different numbers; pairs[i] =
// {u, v} says u and v sit next to each other in the array, in either order, and the pairs
// come in any order. Returns the array; either the original or its reverse is accepted. On
// LeetCode: 2 <= n <= 10^5, -10^5 <= every number <= 10^5.
//
// The one idea: one entry of the map g is one number of the array, with the list of the numbers
// next to it. A number in the middle of the array has two neighbours; the first and the last
// numbers have only one. So a number whose list has one entry is an end of the array: start there.
// The second number is its only neighbour. Every number after that is the neighbour of the previous
// number that is not the one before it: the previous number has exactly two neighbours, one of
// them is where we came from, the other is the way on. The numbers are all different, so "the one
// before" is told apart by its value.
//
// Time: O(n) -- the map is built from n - 1 pairs, then each number is placed once.
// Space: O(n) -- the map.

public final class RestoreFromAdjacentPairs {

	public static int[] restoreArray(int[][] pairs) {
		Map<Integer, List<Integer>> g = graph(pairs);
		int n = pairs.length + 1;
		int[] a = restoredInit(n, g);
		for (int i = 2; i < n; i++) {
			List<Integer> neighbors = g.get(a[i - 1]);
			boolean prev = neighbors.get(0) == a[i - 2]; // first neighbour = where we came from?
			a[i] = prev ? neighbors.get(1) : neighbors.get(0); // TAKE: the other neighbour
		}
		return a; // RETURN
	}

	private static int[] restoredInit(int n, Map<Integer, List<Integer>> g) {
		int[] a = new int[n];
		for (int key : g.keySet()) {
			if (g.get(key).size() == 1) { // an end of the array has one neighbour
				a[0] = key; // BASE: either end will do; the later one found wins
				a[1] = g.get(key).get(0);
			}
		}
		return a;
	}

	private static Map<Integer, List<Integer>> graph(int[][] pairs) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int[] pair : pairs) { // BUILD: each number of a pair lists the other
			int src = pair[0], dst = pair[1];
			map.computeIfAbsent(src, k -> new ArrayList<>()).add(dst);
			map.computeIfAbsent(dst, k -> new ArrayList<>()).add(src);
		}
		return map;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 2, 1 }, { 3, 4 }, { 3, 2 } };
		System.out.println(Arrays.toString(restoreArray(g1))); // [4, 3, 2, 1] (reverse accepted)

		int[][] g3 = { { 4, -2 }, { 1, 4 }, { -3, 1 } };
		System.out.println(Arrays.toString(restoreArray(g3))); // [-3, 1, 4, -2] (reverse accepted)

		int[][] g4 = { { 100000, -100000 } };
		System.out.println(Arrays.toString(restoreArray(g4))); // [-100000, 100000]
	}
}
