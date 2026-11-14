package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 104. Maximum Depth of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the maximum depth: the number of nodes along the longest path
// from the root down to the farthest leaf (0 for an empty tree).
//
// The one idea: pass the depth down instead of adding it on the way back. dfs(node, height)
// carries the height of node itself (the root is 1). Walking past a leaf into an empty child at
// height h means the last real node sat at h - 1, so an empty child reports height - 1, and each
// node returns the larger of its two sides.
//
// Time: O(n) -- each node is visited once, and each empty child once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: MaxDepthNodes returns the depth up instead; MaxDepthTopDownGlobal passes it down too
// but keeps the best in a field.

public final class MaxDepthTopDown {

	public static int maxDepth(TreeNode root) {
		return dfs(root, 1); // START: root's height = 1
	}

	private static int dfs(TreeNode root, int height) {
		if (isNull(root)) {
			return height - 1; // BASE: the empty child is one below the last real node
		}
		int left = dfs(root.left, height + 1); // WALK: one level deeper
		int right = dfs(root.right, height + 1);
		return max(left, right); // RETURN: the deeper side
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(4);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(8);
		System.out.println(maxDepth(root)); // 3

		TreeNode root2 = new TreeNode(3);
		root2.left = new TreeNode(9);
		root2.right = new TreeNode(20);
		root2.right.left = new TreeNode(15);
		root2.right.right = new TreeNode(7);
		System.out.println(maxDepth(root2)); // 3

		TreeNode root3 = new TreeNode(1);
		root3.right = new TreeNode(2);
		System.out.println(maxDepth(root3)); // 2
		System.out.println(maxDepth(null)); // 0
	}
}
