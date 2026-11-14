package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 337. House Robber III
//
// Input: the root of a binary tree; each node is a house holding TreeNode.val money. Robbing two
// houses joined by an edge -- a parent and its child -- trips the alarm. Returns the most money
// that can be robbed. On LeetCode: 1 <= nodes <= 10^4, 0 <= Node.val <= 10^4. (This version is too
// slow for the upper end of that range.)
//
// The one idea: dfs(node) is the most money from the subtree under node. Either rob the node,
// which rules out its two children and so continues at its four grandchildren, or skip the node
// and take the best of each child's subtree. The answer is the better of the two.
//
// Time: O(1.62^n) -- the same subtree is asked for again and again, from its parent and from its
// grandparent. The worst shape is a chain of single children, where the calls follow the
// Fibonacci numbers; on a balanced tree the count is a polynomial, about n^1.7. Counted over every
// tree shape up to 11 nodes: the chain is the most expensive.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: HouseThiefTopDown is this recursion with the answers remembered, so it is O(n).
// HouseThief carries a pair per subtree (maxProfit as an object, maxProfitArray as an int[2]) and
// never asks twice.

public final class HouseThiefRecursive {

	public static int maxProfit(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return dfs(root);
	}

	private static int dfs(TreeNode node) {
		if (node == null) {
			return 0; // BASE: no house
		}
		// 1. rob this house + grandchildren
		int profit = node.val;
		if (node.left != null) {
			profit += dfs(node.left.left) + dfs(node.left.right); // ROB: skip the left child, go to its children
		}
		if (node.right != null) {
			profit += dfs(node.right.left) + dfs(node.right.right); // ROB: skip the right child, go to its children
		}
		// 2. don't rob this house, go to his children
		int noRobbing = dfs(node.left) + dfs(node.right); // SKIP: the children are free to be robbed
		return Math.max(profit, noRobbing); // RETURN: the better of the two choices
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(3);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(3);
		root1.right.right = newNode(1);
		System.out.println(maxProfit(root1)); // 7

		TreeNode root2 = newNode(3);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right = newNode(5);
		root2.right.right = newNode(1);
		System.out.println(maxProfit(root2)); // 9
	}
}
