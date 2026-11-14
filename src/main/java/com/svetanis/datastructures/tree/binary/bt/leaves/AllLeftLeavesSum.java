package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 404. Sum of Left Leaves
//
// Input: the root of a binary tree. A left leaf is a leaf -- a node with no children -- that is
// the left child of its parent. Returns the sum of all left leaves. On LeetCode: 1 <= nodes <=
// 1000, -1000 <= Node.val <= 1000.
//
// The one idea: whether a node is a left leaf is known only from its parent -- the parent looks
// at its left child and checks that it has no children. So every call adds that child's value
// when it qualifies, then adds the sums of the two subtrees. The root has no parent, so a tree
// that is a single node has no left leaf.
//
// Time: O(n) -- one call per node.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: AllRightLeavesSum is the mirror image, with right leaves.

public final class AllLeftLeavesSum {

	public static int sum(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty tree has no leaves
		}
		int sum = 0;
		if (root.left != null && isLeaf(root.left)) {
			sum += root.left.val; // TAKE: the left child is a leaf
		}
		sum += sum(root.left); // WALK: left subtree
		sum += sum(root.right); // WALK: right subtree
		return sum; // RETURN: this node's left leaf plus both subtrees
	}

	private static boolean isLeaf(TreeNode node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(20);
		root.left = new TreeNode(9);
		root.right = new TreeNode(49);
		root.right.left = new TreeNode(23);
		root.right.right = new TreeNode(52);
		root.right.right.left = new TreeNode(50);
		root.left.left = new TreeNode(5);
		root.left.right = new TreeNode(12);
		root.left.right.right = new TreeNode(12);
		System.out.println(sum(root)); // 78 -- the left leaves are 5, 23 and 50
	}
}
