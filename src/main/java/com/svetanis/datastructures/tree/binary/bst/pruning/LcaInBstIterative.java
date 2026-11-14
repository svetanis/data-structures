package com.svetanis.datastructures.tree.binary.bst.pruning;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 235. Lowest Common Ancestor of a Binary Search Tree
//
// Input: root -- the root of a binary search tree with distinct values; p and q -- two different
// nodes (or, in the int overloads, two values) that are in the tree, in either order. Returns the
// lowest node that has both p and q as descendants, where a node counts as a descendant of
// itself. On LeetCode: 2 <= number of nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values
// distinct, p != q, both in the tree.
//
// The one idea: one node holds one value, and everything left of it is smaller, everything right
// of it larger. Call the smaller of the two values lo and the larger hi. A node below lo has lo
// and hi both in its right subtree, so the answer is there: step right. A node above hi has both
// on its left: step left. Any other node has lo <= value <= hi, so lo is on its left (or is the
// node) and hi on its right (or is the node): the paths to them split here, and this is the
// lowest node that has both beneath it. Only one path from the root is ever walked.
//
// Time: O(h) -- one path down; h is the tree's height, log n when balanced, up to n on a chain.
// Space: O(1) -- the walk is a loop and keeps no stack.
//
// Siblings: LcaInBstRecursive -- the same decisions by recursion, O(h) call stack; it overflows
// the default stack on a chain of 10^5 nodes. lcaSimple below is the same loop taking the min
// and max inline and working on nodes; lca(int, int) is the same loop with lo and hi named.

public final class LcaInBstIterative {

	public static TreeNode lcaSimple(TreeNode root, TreeNode p, TreeNode q) {
		while (root != null) {
			if (root.val < Math.min(p.val, q.val)) {
				root = root.right; // WALK right: both targets are larger
			} else if (root.val > Math.max(p.val, q.val)) {
				root = root.left; // WALK left: both targets are smaller
			} else {
				return root; // RETURN: the paths split here
			}
		}
		return null;
	}

	public static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		return lca(root, p.val, q.val);
	}

	public static TreeNode lca(TreeNode root, int p, int q) {
		// LC 235 does not say which of the two comes first, so take the
		// smaller and the larger before comparing
		int lo = min(p, q);
		int hi = max(p, q);
		while (root != null && (root.val < lo || root.val > hi)) {
			// LCA must be in root's right child
			if (root.val < lo) {
				root = root.right; // WALK right
			} else {
				// LCA must be in root's left child
				root = root.left; // WALK left
			}
		}
		// root.val >= lo && root.val <= hi
		return root; // RETURN: root is LCA
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

		// the same two nodes handed over in the other order
		System.out.println(lca(root, 8, 2)); // 6
		System.out.println(lca(root, 4, 2)); // 2
	}
}