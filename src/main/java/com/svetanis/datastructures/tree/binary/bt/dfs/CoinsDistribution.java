package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 979. Distribute Coins in Binary Tree
//
// Input: root of a binary tree with n nodes where node.val is the number of coins in that node,
// and the coins add up to exactly n. One move takes one coin from a node to its parent or to one
// of its children. Returns the fewest moves that leave exactly one coin on every node. On
// LeetCode: 1 <= n <= 100, 0 <= node.val <= n, the values sum to n.
//
// The one idea: dfs(node) returns the surplus of the whole subtree under node -- its coins minus
// its node count. A positive surplus is coins that must leave the subtree through the edge to the
// parent; a negative surplus is coins that must come in through that edge. Either way the edge
// carries |surplus| coins, one move each, and that count is forced -- no order of moves can use
// the edge less. The edges are independent of each other, so the answer is the sum of |surplus|
// over every edge, one edge per child: moves += |left| + |right|.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height.

public final class CoinsDistribution {

	private int moves;

	public int distributeCoins(TreeNode root) {
		moves = 0; // RESET: the field is shared between calls
		dfs(root);
		return moves;
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty spot has no coins and no nodes
		}
		int left = dfs(root.left); // WALK: surplus of the left subtree
		int right = dfs(root.right);
		moves += Math.abs(left) + Math.abs(right); // RECORD: coins crossing the two child edges
		return root.val + left + right - 1; // RETURN: coins here and below, minus one kept per node
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(0);
		root.right = new TreeNode(0);
		CoinsDistribution cd = new CoinsDistribution();
		System.out.println(cd.distributeCoins(root)); // 2

		CoinsDistribution cd1 = new CoinsDistribution();
		TreeNode root1 = new TreeNode(0);
		root1.left = new TreeNode(3);
		root1.right = new TreeNode(0);
		System.out.println(cd1.distributeCoins(root1)); // 3
	}
}
