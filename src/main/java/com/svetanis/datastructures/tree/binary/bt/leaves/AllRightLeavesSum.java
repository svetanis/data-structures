package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Sum of Right Leaves (no LeetCode number; the mirror image of 404, Sum of Left Leaves)
//
// Input: the root of a binary tree. A right leaf is a leaf -- a node with no children -- that is
// the right child of its parent. Returns the sum of all right leaves.
//
// The one idea: whether a node is a right leaf is known only from its parent -- the parent looks
// at its right child and checks that it has no children. So every call adds that child's value
// when it qualifies, then adds the sums of the two subtrees. The root has no parent, so a tree
// that is a single node has no right leaf.
//
// Time: O(n) -- one call per node.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: AllLeftLeavesSum is the mirror image, with left leaves.

public final class AllRightLeavesSum {

	public static int sum(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty tree has no leaves
		}
		int sum = 0;
		if (root.right != null && isLeaf(root.right)) {
			sum += root.right.val; // TAKE: the right child is a leaf
		}
		sum += sum(root.left); // WALK: left subtree
		sum += sum(root.right); // WALK: right subtree
		return sum; // RETURN: this node's right leaf plus both subtrees
	}

	private static boolean isLeaf(TreeNode node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.left.left.right = new TreeNode(2);
		root.right.right = new TreeNode(8);
		root.right.right.left = new TreeNode(6);
		root.right.right.right = new TreeNode(7);
		System.out.println(sum(root)); // 14 -- the right leaves are 2, 5 and 7
	}
}
