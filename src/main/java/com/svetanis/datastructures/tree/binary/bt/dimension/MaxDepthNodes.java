package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 104. Maximum Depth of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 10^4,
// -100 <= val <= 100. Returns the maximum depth: the number of nodes along the longest path
// from the root down to the farthest leaf (0 for an empty tree).
//
// The one idea: ask each subtree for its own depth and add one. maxDepth(node) is 0 for an empty
// subtree; otherwise it is node itself (1) plus the deeper of the two children's depths, because
// the longest path from node goes into whichever child is deeper.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: MaxDepthTopDown and MaxDepthTopDownGlobal pass the depth down instead of returning
// it up; MaxDepthNodesQueue counts levels with a queue; MaxDepthEdges counts edges.

public final class MaxDepthNodes {

	// the return value is the node-count of
	// the longest subroot-to-leaf path in the
	// current subtree after visiting a node
	private static int maxDepth(TreeNode root) {
		if (isNull(root)) {
			return 0; // BASE: an empty subtree has depth 0
		}
		int left = maxDepth(root.left); // WALK: depth of the left subtree
		int right = maxDepth(root.right); // WALK: depth of the right subtree
		return 1 + Math.max(left, right); // RETURN: this node plus the deeper side
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
	}
}
