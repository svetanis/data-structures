package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List
//
// Input: the root of a binary tree. On LeetCode: 0 to 2000 nodes, -100 <= val <= 100.
// Returns: nothing -- the tree is rewired in place so that, following only right pointers from the
// root, the nodes appear in pre-order (node, left subtree, right subtree), and every left pointer
// is null.
//
// The one idea: in pre-order the whole left subtree comes right after the node and before its right
// subtree. So at each node that has a left subtree, find the last node of that subtree in
// pre-order -- it is the rightmost node, found by following right pointers from the left child --
// and hang the node's old right subtree below it. Then move the left subtree over to the right
// pointer and clear the left pointer. Now the node's right pointer leads through the old left
// subtree and on into the old right subtree, in pre-order. Step to the right child and repeat.
// The walk always returns to the right place by itself, because the rewiring has just built the
// path, so nothing needs to be remembered: no stack, no recursion.
//
// Time: O(n) -- each rightmost search walks along a path that is never searched again once the
// rewiring has made it part of the main chain, so all searches together walk fewer than n steps.
// Space: O(1) -- only the TreeNode variables the loop reuses.
//
// Siblings: FlattenBinaryTreeRecursive builds the same list by flattening both children first
// (O(n^2) time on a left-leaning tree); FlattenBinaryTreeStack keeps the nodes still to visit on a
// stack (O(h) space).

public final class FlattenBinaryTreeIterative {

	public static void flatten(TreeNode root) {
		while (root != null) { // WALK: one step down the new right chain per round
			if (root.left != null) {
				TreeNode rightmost = root.left;
				while (rightmost.right != null) { // FIND: last node of the left subtree in pre-order
					rightmost = rightmost.right;
				}
				rightmost.right = root.right; // HANG: old right subtree below that last node
				root.right = root.left; // MOVE: left subtree to the right pointer
				root.left = null; // CLEAR: no left pointers in the result
			}
			root = root.right;
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.right = newNode(6);
		flatten(root);
		Nodes.preOrder(root); // 1 2 3 4 5 6
		System.out.println();
		TreeNode root1 = newNode(0);
		flatten(root1);
		Nodes.preOrder(root1); // 0
		System.out.println();
	}
}
