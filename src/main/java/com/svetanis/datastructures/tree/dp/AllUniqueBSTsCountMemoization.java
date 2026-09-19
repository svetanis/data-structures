package com.svetanis.datastructures.tree.dp;

// 96. Unique Binary Search Trees

// given a number n count
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTsCountMemoization {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)

	public static int count(int n) {
		Integer[] dp = new Integer[n + 1]; // null == not worked out yet
		return dfs(n, dp); // not dp[n]: dfs(0) returns before anything is stored
	}

	private static int dfs(int k, Integer[] dp) {
		if (k == 0) {
			return 1; // one empty tree
		}
		if (dp[k] != null) {
			return dp[k];
		}
		int count = 0;
		for (int root = 1; root <= k; root++) { // root is a key, 1..k
			count += dfs(root - 1, dp) * dfs(k - root, dp); // keys below root * keys above root
		}
		return dp[k] = count;
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
		System.out.println(count(0)); // 1
		System.out.println(count(19)); // 1767263190
	}
}
