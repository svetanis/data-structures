package com.svetanis.datastructures.tree.dp;

import java.util.HashMap;
import java.util.Map;

// 96. Unique Binary Search Trees
//
// Input: n. Returns the number of structurally unique binary search trees that store the keys 1
// to n. On LeetCode: 1 <= n <= 19. The answer for n = 19 is 1,767,263,190, the last one that fits
// in an int: from n = 20 the products and sums overflow.
//
// The one idea: dfs(n) is the number of different trees on n keys. With 0 or 1 keys there is one
// tree. For more, pick the root: the root - 1 smaller keys form the left subtree and the n - root
// larger keys form the right subtree, and each left tree goes with each right tree, so that root
// gives dfs(root - 1) * dfs(n - root) trees. Add that up over every root. The result for each
// size goes into a map and is read back the next time that size is asked for, so each size is
// worked out once.
//
// Time: O(n^2) -- n sizes, each a sum of up to n products, each size worked out once.
// Space: O(n) -- the map and the recursion depth.
//
// Siblings: AllUniqueBSTsCountMemoization is the same memo kept in an array.
// AllUniqueBSTsCountBruteForce has no memo; AllUniqueBSTsCountBottomUp fills the table in a loop.

public final class AllUniqueBSTsCountTopDown {

	public static int count(int n) {
		Map<Integer, Integer> map = new HashMap<>(); // MEMO: size -> number of trees
		return dfs(n, map);
	}

	private static int dfs(int n, Map<Integer, Integer> map) {
		if (n <= 1) {
			return 1; // BASE: no keys or one key, a single tree
		}
		if (map.containsKey(n)) {
			return map.get(n); // CACHED: worked out before
		}
		int count = 0;
		// making i root of the tree
		for (int root = 1; root <= n; root++) {
			// number of unique bst in left subtree
			int left = dfs(root - 1, map); // LEFT: trees on the smaller keys
			// number of unique bst in right subtree
			int right = dfs(n - root, map); // RIGHT: trees on the larger keys
			count += left * right; // ADD: every left tree with every right tree
		}
		map.put(n, count); // STORE: for the next call with this size
		return count;
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
		System.out.println(count(19)); // 1767263190, instant only if the memo is read
	}
}
