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
			for (int root = 0; root < nodes; root++) { // root = how many keys go left, not a key
				// number of unique bst in left subtree
				int left = dp[root];
				// number of unique bst in right subtree
				int right = dp[nodes - root - 1];
				dp[nodes] += left * right;
			}
		}
		return dp[n];
	}

	// the same table with root numbered as the key itself, 1..i
	// keys 1..root-1 go left and keys root+1..i go right
	public static int countByRootKey(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 1; // one empty tree, not zero
		for (int i = 1; i <= n; i++) { // forward: every term reads a smaller size
			for (int root = 1; root <= i; root++) { // stops at i, not n
				dp[i] += dp[root - 1] * dp[i - root];
			}
		}
		return dp[n];
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
		System.out.println(countByRootKey(3)); // 5
		System.out.println(countByRootKey(1)); // 1
		System.out.println(countByRootKey(19)); // 1767263190
	}
}
