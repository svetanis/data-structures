package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 95. Unique Binary Search Trees II
//
// Input: n. Returns every structurally unique binary search tree that stores the keys 1 to n, in
// any order. On LeetCode: 1 <= n <= 8. (For n <= 0 this returns an empty list.)
//
// The one idea: construct(start, end) returns every tree that holds exactly the keys start..end,
// and dp[start][end] remembers that list once it is built. Pick one key i as the root; the keys
// below i go left, the keys above i go right, and every pairing of a left tree and a right tree
// is a different tree. An empty range has one tree, the empty one, so it returns a list holding
// one null. The same range is asked for again and again by different roots, and the cache hands
// back the same list each time; the trees are shared, not copied, which is fine while nobody
// changes them.
//
// Time: O(4^n / n^1.5) -- the number of trees is the n-th Catalan number, and one new node is
// made per tree and per subtree pairing; each of the O(n^2) ranges is built once.
// Space: O(4^n / n^1.5) -- the nodes, each kept in a list, plus the O(n^2) table.
//
// Siblings: AllUniqueBSTs is the same recursion without the cache, rebuilding each range's list
// every time. AllUniqueBSTsCount* only count the trees.

public final class AllUniqueBSTsTopDown {

	public static List<TreeNode> construct(int n) {
		if (n <= 0) {
			return new ArrayList<>();
		}
		List<TreeNode>[][] dp = new ArrayList[n + 1][n + 1]; // dp[start][end]: the trees on keys start..end, or null
		return construct(1, n, dp);
	}

	private static List<TreeNode> construct(int start, int end, List<TreeNode>[][] dp) {
		List<TreeNode> list = new ArrayList<>();
		// if start > end then subtree is empty
		if (start > end) {
			list.add(null); // BASE: one tree, the empty one
			return list;
		}
		if (dp[start][end] != null) {
			return dp[start][end]; // CACHED: built before
		}
		for (int i = start; i <= end; i++) {
			List<TreeNode> left = construct(start, i - 1, dp); // LEFT: every tree on the keys below i
			List<TreeNode> right = construct(i + 1, end, dp); // RIGHT: every tree on the keys above i
			// loop through all left and right subtrees
			// and connect them to i-th root
			for (int j = 0; j < left.size(); j++) {
				for (int k = 0; k < right.size(); k++) {
					TreeNode node = new TreeNode(i); // make value i as root
					node.left = left.get(j); // connect left subtree
					node.right = right.get(k); // connect right subtree
					list.add(node); // RECORD: one tree per pairing
				}
			}
		}
		return dp[start][end] = list; // STORE: keep the list for the next root that asks
	}

	public static void main(String[] args) {
		// construct all possible BTs
		List<TreeNode> trees = construct(3);
		for (int i = 0; i < trees.size(); i++) {
			preOrder(trees.get(i));
			System.out.println(); // 1 2 3 / 1 3 2 / 2 1 3 / 3 1 2 / 3 2 1, one tree per line
		}
	}
}
