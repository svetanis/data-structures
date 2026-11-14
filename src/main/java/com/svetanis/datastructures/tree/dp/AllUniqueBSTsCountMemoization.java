package com.svetanis.datastructures.tree.dp;

// 96. Unique Binary Search Trees
//
// Input: n. Returns the number of structurally unique binary search trees that store the keys 1
// to n. On LeetCode: 1 <= n <= 19. The answer for n = 19 is 1,767,263,190, the last one that fits
// in an int: from n = 20 the products and sums overflow.
//
// The one idea: dfs(k) is the number of different trees on k keys. With no keys there is one tree,
// the empty one. For k keys pick the root: the root - 1 smaller keys form the left subtree and the
// k - root larger keys form the right subtree, and each left tree goes with each right tree, so
// that root gives dfs(root - 1) * dfs(k - root) trees. Add that up over every root. The result for
// each k goes into dp[k] and is read back the next time k is asked for, so each size is worked out
// once.
//
// Time: O(n^2) -- n sizes, each a sum of up to n products, each size worked out once.
// Space: O(n) -- the table and the recursion depth.
//
// Siblings: AllUniqueBSTsCountTopDown is the same memo kept in a HashMap, and returns 1 for k = 1
// as well as for k = 0. AllUniqueBSTsCountBruteForce has no memo; AllUniqueBSTsCountBottomUp fills
// the table in a loop.

public final class AllUniqueBSTsCountMemoization {

	public static int count(int n) {
		Integer[] dp = new Integer[n + 1]; // null == not worked out yet
		return dfs(n, dp); // not dp[n]: dfs(0) returns before anything is stored
	}

	private static int dfs(int k, Integer[] dp) {
		if (k == 0) {
			return 1; // one empty tree
		}
		if (dp[k] != null) {
			return dp[k]; // CACHED: worked out before
		}
		int count = 0;
		for (int root = 1; root <= k; root++) { // root is a key, 1..k
			count += dfs(root - 1, dp) * dfs(k - root, dp); // keys below root * keys above root
		}
		return dp[k] = count; // STORE: and return it
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
		System.out.println(count(0)); // 1
		System.out.println(count(19)); // 1767263190
	}
}
