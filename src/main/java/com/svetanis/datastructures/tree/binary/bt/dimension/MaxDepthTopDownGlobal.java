package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 104. Maximum Depth of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the maximum depth: the number of nodes along the longest path
// from the root down to the farthest leaf (0 for an empty tree).
//
// The one idea: pass the depth down and keep the deepest one seen in a field. dfs(node, height)
// knows the height of node itself (the root is 1); every real node offers its height to the
// field and the biggest offer is the answer. Nothing is returned up the recursion. The field is
// set back to 0 at the start of every call, so one object can answer many trees.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: MaxDepthTopDown passes the depth down too but returns the answer up instead of
// keeping it in a field; MaxDepthNodes only returns it up.

public final class MaxDepthTopDownGlobal {

	private int max;

	public int maxDepth(TreeNode root) {
		max = 0; // BASE: reset, so one instance can answer many trees
		dfs(root, 1); // START: root's height = 1
		return max;
	}

	private void dfs(TreeNode root, int height) {
		if (isNull(root)) {
			return;
		}
		max = Math.max(max, height); // RECORD: the deepest node seen so far
		dfs(root.left, height + 1); // WALK: one level deeper
		dfs(root.right, height + 1);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(4);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(8);
		MaxDepthTopDownGlobal mdg = new MaxDepthTopDownGlobal();
		System.out.println(mdg.maxDepth(root)); // 3

		TreeNode root2 = new TreeNode(3);
		root2.left = new TreeNode(9);
		root2.right = new TreeNode(20);
		root2.right.left = new TreeNode(15);
		root2.right.right = new TreeNode(7);
		System.out.println(mdg.maxDepth(root2)); // 3

		TreeNode root3 = new TreeNode(1);
		root3.right = new TreeNode(2);
		System.out.println(mdg.maxDepth(root3)); // 2 -- same object, smaller tree
	}
}
