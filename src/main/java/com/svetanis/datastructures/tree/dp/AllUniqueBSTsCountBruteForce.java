package com.svetanis.datastructures.tree.dp;

// 96. Unique Binary Search Trees
//
// Input: n. Returns the number of structurally unique binary search trees that store the keys 1
// to n. On LeetCode: 1 <= n <= 19. The answer for n = 19 is 1,767,263,190, the last one that fits
// in an int: from n = 20 the products and sums overflow.
//
// The one idea: count(n) is the number of different trees on n keys. With 0 or 1 keys there is one
// tree. For more, pick the root i: the i - 1 smaller keys form the left subtree and the n - i
// larger keys form the right subtree, and each left tree goes with each right tree, so that root
// gives count(i - 1) * count(n - i) trees. Add that up over every root. The same smaller sizes are
// asked for again and again, and nothing remembers the answers.
//
// Time: O(3^n) -- the number of calls grows by exactly 3 for each extra key: 5 * 3^(n - 2) calls
// for n >= 2, counted for n up to 14. The same sizes are recomputed; the memoised files do the
// work in O(n^2).
// Space: O(n) -- the recursion depth.
//
// Siblings: AllUniqueBSTsCountTopDown and AllUniqueBSTsCountMemoization remember each size once
// computed; AllUniqueBSTsCountBottomUp fills a table instead of recursing.

public final class AllUniqueBSTsCountBruteForce {

	public static int count(int n) {
		if (n <= 1) {
			return 1; // BASE: no keys or one key, a single tree
		}
		int count = 0;
		// making i root of the tree
		for (int i = 1; i <= n; i++) {
			int left = count(i - 1); // LEFT: trees on the i - 1 smaller keys
			int right = count(n - i); // RIGHT: trees on the n - i larger keys
			count += (left * right); // ADD: every left tree with every right tree
		}
		return count; // RETURN: all roots added up
	}

	public static void main(String[] args) {
		System.out.println(count(3)); // 5
	}
}
