package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 669. Trim a Binary Search Tree
//
// Input: root -- the root of a binary search tree with distinct values; low and high -- the ends
// of the range to keep. Returns the root of the tree that keeps exactly the nodes whose value v
// has low <= v <= high, with the relative structure of the kept nodes unchanged (a kept node
// stays a descendant of the kept nodes it was a descendant of). The root may change. On
// LeetCode: 1 <= number of nodes <= 10^4, 0 <= Node.val <= 10^4, all values distinct,
// 0 <= low <= high <= 10^4.
//
// The one idea: one call looks at one node, which holds one value, and returns the root of that
// node's subtree after trimming. A node above the range (v > high) is cut, and so is its whole
// right subtree, which is larger still; what is left to keep can only be in its left subtree, so
// the answer is the call on the left child. A node below the range (v < low) is the mirror: the
// answer is the call on the right child. A node inside the range stays, and each child becomes
// whatever the call on it returns.
//
// Time: O(n) -- a node inside the range is visited and so are the out-of-range nodes between it
// and what is kept; a subtree on the wrong side of a cut node is never entered.
// Space: O(h) -- the call stack; h is the tree's height.
//
// Depth: the recursion is as deep as the tree is tall; the default stack holds LeetCode's largest
// chain (10^4 nodes) and overflows near 12,000.

public final class TrimBst {

	public static TreeNode trim(TreeNode root, int low, int high) {
		if (root == null) {
			return null; // BASE: nothing to trim
		}
		if (root.val > high) {
			return trim(root.left, low, high); // CUT: this node and its larger right side are out
		}
		if (root.val < low) {
			return trim(root.right, low, high); // CUT: this node and its smaller left side are out
		}
		root.left = trim(root.left, low, high); // KEEP: trim each side
		root.right = trim(root.right, low, high);
		return root;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(0);
		root.right = newNode(2);
		Nodes.preOrder(trim(root, 1, 2)); // 1 2
		System.out.println();

		TreeNode root2 = newNode(3);
		root2.left = newNode(0);
		root2.right = newNode(4);
		root2.left.right = newNode(2);
		root2.left.right.left = newNode(1);
		Nodes.preOrder(trim(root2, 1, 3)); // 3 2 1
		System.out.println();
	}
}
