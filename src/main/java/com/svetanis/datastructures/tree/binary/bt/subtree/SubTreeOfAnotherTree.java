package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 572. Subtree of Another Tree
//
// Input: root and subRoot, the roots of two binary trees. A subtree of a tree is a node together
// with all of its descendants. Returns true when subRoot has the same shape and the same values as
// some subtree of root. On LeetCode: 1 <= nodes in root <= 2000, 1 <= nodes in subRoot <= 1000,
// -10^4 <= Node.val <= 10^4. For two empty trees this file returns false (an empty root holds
// nothing); LeetCode never passes one.
//
// The one idea: subRoot sits inside root exactly when it is the same tree as root itself, or as the
// tree under root's left child, or as the tree under root's right child. "The same tree" is a
// separate question: both empty, or both non-empty with equal values and the same tree on the left
// and the same tree on the right. So try the match at the current node, and if it fails, ask the
// same question of each child.
//
// Time: O(n * m) -- at most one match attempt per node of root, each costing at most m, the size of
// subRoot, since an attempt stops at the first difference.
// Space: O(h) -- the recursion stack; h is the height of root, n for a chain.
//
// Siblings: SubTreeNodeComparison -- the same method using the repo's IdenticalBinaryTreesRecursive
// for the match (and true for two empty trees); SubTreeInOrderPreOrder -- compares strings of the
// two trees' node characters instead of comparing nodes.

public final class SubTreeOfAnotherTree {

	public static boolean isSubTree(TreeNode root, TreeNode subRoot) {
		if (isNull(root)) {
			return false; // BASE: nothing left to search
		}
		// empty tree is always a subtree
		if (isNull(subRoot)) {
			return true; // BASE: an empty subRoot matches any non-empty root
		}
		if (isIdentical(root, subRoot)) {
			return true; // MATCH: subRoot is exactly the tree at this node
		}
		boolean left = isSubTree(root.left, subRoot); // WALK: look under the left child
		boolean right = isSubTree(root.right, subRoot); // WALK: look under the right child
		return left || right;
	}

	private static boolean isIdentical(TreeNode root1, TreeNode root2) {
		if (isNull(root1) && isNull(root2)) {
			return true; // BASE: both ended together
		}
		if (isNull(root1) || isNull(root2)) {
			return false; // MISMATCH: the shapes differ
		}
		if (root1.val != root2.val) {
			return false; // MISMATCH: the values differ
		}
		boolean left = isIdentical(root1.left, root2.left); // WALK: the left pair
		boolean right = isIdentical(root1.right, root2.right); // WALK: the right pair
		return left && right;
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(3);
		root1.left = newNode(4);
		root1.right = newNode(5);
		root1.left.left = newNode(1);
		root1.left.right = newNode(2);
		root1.right = newNode(5);

		TreeNode root2 = newNode(4);
		root2.left = newNode(1);
		root2.right = newNode(2);

		System.out.println(isSubTree(root1, root2)); // true

		TreeNode root3 = newNode(3);
		root3.left = newNode(4);
		root3.right = newNode(5);
		root3.left.left = newNode(1);
		root3.left.right = newNode(2);
		root3.left.right.left = newNode(0);
		System.out.println(isSubTree(root3, root2)); // false
	}
}
