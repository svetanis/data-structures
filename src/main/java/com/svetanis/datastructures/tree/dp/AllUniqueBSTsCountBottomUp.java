package com.svetanis.datastructures.tree.dp;

// 96. Unique Binary Search Trees
//
// Input: n. Returns the number of structurally unique binary search trees that store the keys 1
// to n. On LeetCode: 1 <= n <= 19. The answer for n = 19 is 1,767,263,190, the last one that fits
// in an int: from n = 20 the products and sums overflow.
//
// The one idea: dp[nodes] holds the number of different trees on that many keys -- any consecutive
// keys, since only their order matters. With no keys there is one tree, the empty one, so dp[0] = 1.
// One turn of the outer loop works out one cell; one turn of the inner loop is one group, the trees
// with one particular root. If leftKeys keys are smaller than the root, they form the left subtree
// and the other nodes - leftKeys - 1 form the right one, and each left shape goes with each right
// shape, so the group holds dp[leftKeys] * dp[nodes - leftKeys - 1] trees. Add the groups up.
// Each cell reads only cells with fewer keys, so filling 1, 2, 3, ... keys in order works.
//
// Time: O(n^2) -- n cells, each a sum of up to n products.
// Space: O(n) -- the table.
//
// Siblings: AllUniqueBSTsCountBruteForce is the same recursion with no table; AllUniqueBSTsCountTopDown
// and AllUniqueBSTsCountMemoization add a memo to it; this file fills the table bottom-up. The
// second method here, countByRootKey, is the same table with the root numbered by its key 1..nodes
// instead of by how many keys go left: leftKeys = root - 1.

public final class AllUniqueBSTsCountBottomUp {

	// the root numbered by how many keys go left: leftKeys = 0..nodes-1
	public static int count(int n) {
		int[] dp = new int[n + 1]; // dp[nodes]: how many tree shapes hold that many keys
		dp[0] = 1; // BASE: no keys, one shape -- the empty tree
		for (int nodes = 1; nodes <= n; nodes++) { // SIZE: one turn works out dp[nodes]
			// GROUP: one turn = the trees with leftKeys keys below the root
			for (int leftKeys = 0; leftKeys < nodes; leftKeys++) {
				int left = dp[leftKeys]; // LEFT: shapes the smaller keys can take
				int right = dp[nodes - leftKeys - 1]; // RIGHT: the rest, minus the root's own key
				dp[nodes] += left * right; // ADD: every left shape with every right shape
			}
		}
		return dp[n]; // RETURN: all n keys
	}

	// the same table with the root numbered by its key: root = 1..nodes
	public static int countByRootKey(int n) {
		int[] dp = new int[n + 1]; // dp[nodes]: how many tree shapes hold that many keys
		dp[0] = 1; // BASE: no keys, one shape -- the empty tree
		for (int nodes = 1; nodes <= n; nodes++) { // SIZE: one turn works out dp[nodes]
			// GROUP: one turn = the trees with the key root on top
			for (int root = 1; root <= nodes; root++) {
				int left = dp[root - 1]; // LEFT: shapes the keys 1..root-1 can take
				int right = dp[nodes - root]; // RIGHT: shapes the keys root+1..nodes can take
				dp[nodes] += left * right; // ADD: every left shape with every right shape
			}
		}
		return dp[n]; // RETURN: all n keys
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
		System.out.println(count(1)); // 1
		System.out.println(countByRootKey(3)); // 5
		System.out.println(countByRootKey(1)); // 1
		System.out.println(countByRootKey(19)); // 1767263190
	}
}
