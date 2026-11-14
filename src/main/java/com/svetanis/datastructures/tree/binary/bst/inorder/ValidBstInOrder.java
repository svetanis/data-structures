package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicReference;

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
// null, not a number such as Integer.MIN_VALUE: a node may hold -2^31 itself, and would then
// be compared with a made-up predecessor equal to it and rejected.
//
// This version computes the left result and the right result before combining them, so a
// failure in the left subtree does not stop it: it goes on to the current node and the right
// subtree. The answer is the same; ValidBstInOrderRecursive returns at the first failure
// instead -- read the two together.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of ten thousand
// nodes (LC's maximum) or more can overflow the default stack, depending on how warm the JIT
// compiler is. ValidBstInOrderIterative is the same walk with an explicit stack.
//
// Time: O(n) -- each node is visited at most once.
// Space: O(h) -- the recursion stack.
//
// Siblings: ValidBstInOrderRecursive keeps prev in a field and stops at the first failure;
// ValidBstInOrderIterative is the same walk with an explicit stack.

public final class ValidBstInOrder {

	public static boolean isValidBst(TreeNode root) {
		AtomicReference<Integer> prev = new AtomicReference<>(); // null: nothing visited yet
		return isValidBst(root, prev);
	}

	private static boolean isValidBst(TreeNode root, AtomicReference<Integer> prev) {
		// an empty tree is a BST
		if (isNull(root)) { // BASE
			return true;
		}
		// check if left subtree is BST or not
		boolean left = isValidBst(root.left, prev); // WALK: smaller values first
		// value of current node should be
		// more than that of prev node
		if (prev.get() != null && root.val <= prev.get()) { // CHECK: not strictly above the one before
			return false;
		}
		// update the prev node
		prev.set(root.val); // ADVANCE
		// check if right subtree is BST or not
		boolean right = isValidBst(root.right, prev); // WALK: larger values last
		return left && right; // RETURN: both sides must hold
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(isValidBst(root)); // true

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.right = newNode(6);
		System.out.println(isValidBst(root2)); // false

		// the smallest node holds Integer.MIN_VALUE
		System.out.println(isValidBst(newNode(Integer.MIN_VALUE))); // true
		TreeNode root3 = newNode(0);
		root3.left = newNode(Integer.MIN_VALUE);
		System.out.println(isValidBst(root3)); // true
	}
}
