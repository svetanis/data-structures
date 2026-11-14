package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 98. Validate Binary Search Tree
//
// Input: the root of a binary tree. Returns true if it is a valid binary search tree: for every
// node, every value in its left subtree is smaller and every value in its right subtree is
// larger. On LeetCode: 1 to 10^4 nodes, -2^31 <= node.val <= 2^31 - 1.
//
// The one idea: the in-order walk of a BST visits the values in strictly ascending order, and
// no other tree does that. So walk in order, keep the value visited just before (prev), and
// answer false if any value is not larger than prev. The walk uses an explicit stack of the
// nodes whose left subtree is being explored, so it stops at the first failure and a very tall
// tree cannot overflow the call stack. prev is a boxed Integer that starts as null, not
// Integer.MIN_VALUE: a node may hold -2^31 itself.
//
// Time: O(n) -- each node is pushed once and popped once; it stops early on a failure.
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: ValidBstInOrderRecursive is the same walk, recursive, with prev in a field;
// ValidBstInOrder is recursive too, and does not stop at the first failure.

public final class ValidBstInOrderIterative {

	public static boolean isValidBst(TreeNode root) {
		Integer prev = null; // null: nothing visited yet
		TreeNode curr = root;
		Deque<TreeNode> dq = new ArrayDeque<>();
		while (!dq.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the left side
				dq.push(curr);
				curr = curr.left;
			}
			curr = dq.pop(); // VISIT: the smallest value not yet visited
			if (prev != null && curr.val <= prev) { // CHECK: not strictly above the one before
				return false;
			}
			prev = curr.val; // ADVANCE
			curr = curr.right; // WALK: then the larger values
		}
		return true;
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
	}
}
