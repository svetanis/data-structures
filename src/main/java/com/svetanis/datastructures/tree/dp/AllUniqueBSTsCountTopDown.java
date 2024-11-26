package com.svetanis.datastructures.tree.dp;

import java.util.HashMap;
import java.util.Map;

// 96. Unique Binary Search Trees

// given a number n count
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTsCountTopDown {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)

	public static int count(int n) {
		Map<Integer, Integer> map = new HashMap<>();
		return dfs(n, map);
	}

	private static int dfs(int n, Map<Integer, Integer> map) {
		if (n <= 1) {
			return 1;
		}
		if (map.containsKey(n)) {
			map.get(n);
		}
		int count = 0;
		// making i root of the tree
		for (int root = 1; root <= n; root++) {
			// number of unique bst in left subtree
			int left = dfs(root - 1, map);
			// number of unique bst in right subtree
			int right = dfs(n - root, map);
			count += left * right;
		}
		map.put(n, count);
		return count;
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
	}
}
