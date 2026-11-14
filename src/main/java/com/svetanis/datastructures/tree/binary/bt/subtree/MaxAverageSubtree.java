package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1120. Maximum Average Subtree
//
// Input: the root of a binary tree. A subtree is a node together with everything below it; its
// average is the sum of its values divided by how many nodes it has. Returns the largest average
// over all subtrees (any node may be the top). On LeetCode: 1 <= number of nodes <= 10^4,
// 0 <= Node.val <= 10^5; an answer within 10^-5 of the true value is accepted.
//
// The one idea: one call returns two numbers for one node's subtree: the sum of its values and its
// node count. A node's pair is its own value plus its children's sums, and 1 plus its children's
// counts, so one pass bottom-up has the pair of every node. Each node divides its own pair once and
// keeps the largest average seen. Averages are compared as doubles, which is exact enough here:
// sums are at most 10^9 and counts at most 10^4.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain. The two-number
// arrays are short-lived: only those on the current path exist at once.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 10^4 nodes (each
// node the only child of the one before) overflows the default Java stack. It happens only on a
// very deep input; MaxAverageSubtreeIterative is the same method with an explicit stack.
//
// Siblings: MaxAverageSubtreeIterative -- the same pairs without recursion.

public final class MaxAverageSubtree {

	private double maxAverage; // BEST: the largest average seen so far

	public double maxAvgSubtree(TreeNode root) {
		this.maxAverage = 0.0; // RESET: values are never negative, so 0 is a safe start
		dfs(root); // WALK: every node offers its subtree's average
		return maxAverage; // RETURN: the largest average
	}

	// returns { sum of the subtree's values, number of nodes in it }
	private int[] dfs(TreeNode node) {
		if (node == null) {
			return new int[] { 0, 0 }; // BASE: an empty subtree has no values and no nodes
		}
		int[] left = dfs(node.left); // WALK: the left subtree's pair
		int[] right = dfs(node.right); // WALK: the right subtree's pair
		int sum = node.val + left[0] + right[0]; // SUM: this subtree's values
		int count = 1 + left[1] + right[1]; // COUNT: this subtree's nodes
		double average = (double) sum / count;
		maxAverage = Math.max(maxAverage, average); // RECORD: keep the largest
		return new int[] { sum, count };
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(6);
		root.right = newNode(1);
		root.left.left = newNode(2);
		MaxAverageSubtree mst = new MaxAverageSubtree();
		System.out.println(mst.maxAvgSubtree(root)); // 4.0

		TreeNode root1 = newNode(5);
		root1.left = newNode(6);
		root1.right = newNode(1);
		System.out.println(mst.maxAvgSubtree(root1)); // 6.0
	}
}
