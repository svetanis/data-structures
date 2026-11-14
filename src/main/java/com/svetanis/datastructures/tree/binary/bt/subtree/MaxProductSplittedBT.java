package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1339. Maximum Product of Splitted Binary Tree
//
// Input: the root of a binary tree. Removing one edge splits the tree into two trees. Returns the
// largest possible product of the two trees' value sums, modulo 10^9 + 7 (the maximum is found
// before the modulo is taken). On LeetCode: 2 <= number of nodes <= 5 * 10^4,
// 1 <= Node.val <= 10^4.
//
// The one idea: cutting the edge above a node splits off exactly that node's subtree, so there is
// one candidate split per node below the root: its subtree sum s against the rest, total - s. The
// product s * (total - s) is largest when s is nearest half the total, but trying every node is
// just as easy. So: one pass finds the total; a second pass finds each subtree sum from its
// children's sums and keeps the largest product. The products reach about 6 * 10^16, so they are
// kept in a long and the modulo is taken only once at the end.
//
// Time: O(n) -- two passes, one call per node in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 5 * 10^4 nodes
// (each node the only child of the one before) overflows the default Java stack. It happens only
// on a very deep input; MaxProductSplittedBTIterative is the same method with an explicit stack.
//
// Siblings: MaxProductSplittedBTIterative -- the same two passes without recursion.

public final class MaxProductSplittedBT {

	private static final int MOD = (int) 1e9 + 7;

	private long maxProduct; // BEST: the largest product so far, before the modulo

	public int maxProduct(TreeNode root) {
		this.maxProduct = 0; // RESET: a second call on this object starts from zero
		long total = sum(root); // PASS 1: the sum of the whole tree
		dfs(root, total); // PASS 2: every node tries the split above it
		return (int) (maxProduct % MOD); // RETURN: the modulo comes last
	}

	private long dfs(TreeNode node, long total) {
		if (node == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		long sum = node.val + dfs(node.left, total) + dfs(node.right, total); // SUM: this subtree
		long product = sum * (total - sum); // SPLIT: this subtree against the rest
		maxProduct = Math.max(maxProduct, product); // RECORD: keep the largest
		return sum;
	}

	private long sum(TreeNode node) {
		if (node == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		long left = sum(node.left);
		long right = sum(node.right);
		return node.val + left + right; // RETURN: this subtree's sum
	}

	public static void main(String[] args) {

		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		MaxProductSplittedBT mst = new MaxProductSplittedBT();
		System.out.println(mst.maxProduct(root)); // 110

		TreeNode root2 = newNode(1);
		root2.right = newNode(2);
		root2.right.left = newNode(3);
		root2.right.right = newNode(4);
		root2.right.right.left = newNode(5);
		root2.right.right.right = newNode(6);
		MaxProductSplittedBT mst2 = new MaxProductSplittedBT();
		System.out.println(mst2.maxProduct(root2)); // 90

	}
}
