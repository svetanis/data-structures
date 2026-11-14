package com.svetanis.datastructures.tree.binary.bst.pruning;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 235. Lowest Common Ancestor of a Binary Search Tree
//
// Input: root -- the root of a binary search tree with distinct values; p and q -- two different
// nodes (or, in the int overload, two values) that are in the tree, in either order. Returns the
// lowest node that has both p and q as descendants, where a node counts as a descendant of
// itself. On LeetCode: 2 <= number of nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values
// distinct, p != q, both in the tree.
//
// The one idea: one call looks at one node, which holds one value; everything left of it is
// smaller and everything right of it larger. If the larger of the two values is smaller than the
// node's value, both are in its left subtree, so the answer is the call on the left child. If the
// smaller of the two is larger than the node's value, the same on the right. Otherwise one value
// is <= the node's value and the other >=, so the paths to them split here (or one of them is
// this node), and this is the lowest node that has both beneath it.
//
// Time: O(h) -- one path down; h is the tree's height, log n when balanced, up to n on a chain.
// Space: O(h) -- the call stack.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of about 10^5 nodes
// (LeetCode's largest) overflows the default stack. LcaInBstIterative does it with a loop.
//
// Siblings: LcaInBstIterative -- the same decisions in a loop, O(1) space.

public final class LcaInBstRecursive {

	public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || p == null || q == null) {
			return null; // BASE: nothing to search, or a target missing
		}
		if (max(p.val, q.val) < root.val) {
			return lca(root.left, p, q); // WALK left: both targets are smaller
		} else if (min(p.val, q.val) > root.val) {
			return lca(root.right, p, q); // WALK right: both targets are larger
		} else {
			return root; // RETURN: the paths split here
		}
	}

	public static TreeNode lca(TreeNode root, int p, int q) {
		if (root == null) {
			return null; // BASE: nothing to search
		}
		if (max(p, q) < root.val) {
			return lca(root.left, p, q); // WALK left: both targets are smaller
		} else if (min(p, q) > root.val) {
			return lca(root.right, p, q); // WALK right: both targets are larger
		} else {
			return root; // RETURN: the paths split here
		}
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(6);
		root.left = new TreeNode(2);
		root.right = new TreeNode(8);
		root.left.left = new TreeNode(0);
		root.left.right = new TreeNode(4);
		root.left.right.left = new TreeNode(3);
		root.left.right.right = new TreeNode(5);
		root.right.left = new TreeNode(7);
		root.right.right = new TreeNode(9);
		System.out.println(lca(root, 2, 8)); // 6
		System.out.println(lca(root, 2, 4)); // 2
	}
}