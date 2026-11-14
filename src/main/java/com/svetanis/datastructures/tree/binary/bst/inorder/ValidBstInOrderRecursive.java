package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 98. Validate Binary Search Tree
//
// Input: the root of a binary tree. Returns true if it is a valid binary search tree: for every
// node, every value in its left subtree is smaller and every value in its right subtree is
// larger. On LeetCode: 1 to 10^4 nodes, -2^31 <= node.val <= 2^31 - 1.
//
// The one idea: the in-order walk of a BST visits the values in strictly ascending order, and
// no other tree does that. So walk in order, keep the value visited just before (prev), and
// answer false if any value is not larger than prev. prev is a boxed Integer that starts as
// null, not Integer.MIN_VALUE: a node may hold -2^31 itself. It is a field, so isValidBst()
// resets it on its first line; without that, a second call would compare the new tree's first
// value with the old tree's last.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of ten thousand
// nodes (LC's maximum) or more can overflow the default stack, depending on how warm the JIT
// compiler is. ValidBstInOrderIterative is the same walk with an explicit stack.
//
// Time: O(n) -- each node is visited once; the walk returns at the first failure.
// Space: O(h) -- the recursion stack.
//
// Siblings: ValidBstInOrder is the same walk with prev in a reference passed down, and it does
// not stop at the first failure; ValidBstInOrderIterative uses an explicit stack.

public final class ValidBstInOrderRecursive {

	private Integer prev;

	public boolean isValidBst(TreeNode root) {
		this.prev = null;
		return inOrder(root);
	}

	private boolean inOrder(TreeNode root) {
		// an empty tree is a BST
		if (root == null) { // BASE
			return true;
		}
		// check if left subtree is BST or not
		if (!inOrder(root.left)) { // WALK: smaller values first, stop at the first failure
			return false;
		}
		// value of current node should be
		// more than that of prev node
		if (prev != null && prev >= root.val) { // CHECK: not strictly above the one before
			return false;
		}
		// update the prev node
		prev = root.val; // ADVANCE
		// check if right subtree is BST or not
		return inOrder(root.right); // WALK: larger values last
	}

	public static void main(String[] args) {
		TreeNode root = newNode(2);
		root.left = newNode(1);
		root.right = newNode(3);
		ValidBstInOrderRecursive vb1 = new ValidBstInOrderRecursive();
		System.out.println(vb1.isValidBst(root)); // true

		TreeNode root2 = newNode(5);
		root2.left = newNode(1);
		root2.right = newNode(4);
		root2.right.left = newNode(3);
		root2.right.right = newNode(6);
		ValidBstInOrderRecursive vb2 = new ValidBstInOrderRecursive();
		System.out.println(vb2.isValidBst(root2)); // false

		TreeNode root3 = new TreeNode(0);
		ValidBstInOrderRecursive vb3 = new ValidBstInOrderRecursive();
		System.out.println(vb3.isValidBst(root3)); // true

		TreeNode root4 = new TreeNode(1);
		root4.left = new TreeNode(1);
		ValidBstInOrderRecursive vb4 = new ValidBstInOrderRecursive();
		System.out.println(vb4.isValidBst(root4)); // false
	}
}
