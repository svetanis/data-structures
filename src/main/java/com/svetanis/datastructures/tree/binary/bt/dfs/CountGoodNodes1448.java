package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1448. Count Good Nodes in Binary Tree
//
// Input: root of a binary tree. A node is good when no node on the path from the root to it
// holds a larger value. Returns the number of good nodes; the root is always good. On LeetCode:
// 1 <= number of nodes <= 10^5, -10^4 <= node.val <= 10^4.
//
// The one idea: whether a node is good depends only on the largest value above it, so carry that
// one number down as a parameter. A node is good when its value is at least the carried maximum;
// then it becomes the new maximum for its children. Starting the maximum at Integer.MIN_VALUE
// makes the root good without a special case, and the >= keeps a node equal to the maximum good.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of about 10^5 nodes (the
// LeetCode maximum) overflows the default stack. CountGoodNodes1448Iterative is the same walk
// with an explicit stack.
//
// Siblings: CountVisibleNodes is the same count under another name; it passes
// max(max, node.val) down on every call instead of updating it inside the if.
// CountGoodNodes1448Iterative is this walk without recursion.

public final class CountGoodNodes1448 {

	public static int count(TreeNode root) {
		return dfs(root, Integer.MIN_VALUE); // START: nothing above the root, so the root is good
	}

	private static int dfs(TreeNode root, int max) {
		if (root == null) {
			return 0; // BASE: an empty spot has no good nodes
		}
		int count = 0;
		if (max <= root.val) {
			count++; // TAKE: nothing larger above, this node is good
			max = root.val; // RAISE: the children must beat this value
		}
		count += dfs(root.left, max); // WALK: left subtree with the maximum so far
		count += dfs(root.right, max);
		return count;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(1);
		root.right = newNode(4);
		root.left.left = newNode(3);
		root.right.left = newNode(1);
		root.right.right = newNode(5);
		System.out.println(count(root)); // 4

		TreeNode root1 = newNode(3);
		root1.left = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(count(root1)); // 3

		TreeNode root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}
}
