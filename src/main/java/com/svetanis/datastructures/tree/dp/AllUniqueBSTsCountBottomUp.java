package com.svetanis.datastructures.tree.dp;

// 96. Unique Binary Search Trees

// given a number n count
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTsCountBottomUp {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)

	public static int count(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 1;
		for (int nodes = 1; nodes <= n; nodes++) {
			for (int root = 0; root < nodes; root++) {
				// number of unique bst in left subtree
				int left = dp[root];
				// number of unique bst in right subtree
				int right = dp[nodes - root - 1];
				dp[nodes] += left * right;
			}
		}
		return dp[n];
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
	}
}
