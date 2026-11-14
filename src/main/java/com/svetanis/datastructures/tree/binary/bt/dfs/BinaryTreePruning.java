package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 814. Binary Tree Pruning
//
// Input: root of a binary tree whose nodes hold 0 or 1. Returns the same tree with every
// subtree that contains no 1 removed. On LeetCode: 1 <= number of nodes <= 200,
// node.val is 0 or 1.
//
// The one idea: a node stays if its own value is 1 or if anything below it stays. So decide the
// children first: prune the left subtree and the right subtree, and hang back whatever is left.
// After that, a node with no children left and a 0 holds no 1 anywhere in its subtree, so it is
// removed too -- returning null to its parent. Deciding children before the parent (post-order)
// is what makes one pass enough: by the time a node is judged, "nothing below" is already true
// or false.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.

public final class BinaryTreePruning {

	public static TreeNode pruneTree(TreeNode root) {
		if (root == null) {
			return null; // BASE: an empty spot holds no 1
		}
		root.left = pruneTree(root.left); // PRUNE: children first
		root.right = pruneTree(root.right);
		if (root.left == null && root.right == null && root.val == 0) {
			return null; // DROP: a 0 with nothing kept below it
		}
		return root; // KEEP: a 1, or a node with a kept child
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(0);
		root.left.left = newNode(0);
		root.left.right = newNode(0);
		root.right = newNode(1);
		root.right.left = newNode(0);
		root.right.right = newNode(1);
		Nodes.preOrder(pruneTree(root)); // 1 1 1
		System.out.println();

		TreeNode root2 = newNode(1);
		root2.right = newNode(0);
		root2.right.left = newNode(0);
		root2.right.right = newNode(1);
		Nodes.preOrder(pruneTree(root2)); // 1 0 1
	}
}
