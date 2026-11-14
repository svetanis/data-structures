package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 538. Convert BST to Greater Tree
//
// Input: the root of a binary search tree. Returns the same tree with every node's value
// replaced by the sum of all values in the tree that are greater than or equal to it. On
// LeetCode: 0 to 10^4 nodes, -10^4 <= node.val <= 10^4, all values unique.
//
// The one idea: the answer for a node is the sum of the values at or above it. Walk the tree
// in REVERSE in-order -- right subtree, node, left subtree -- which visits the values from the
// largest down. Keep one running total of every value visited so far. When the walk reaches a
// node, the total holds exactly the values larger than it; add the node's own value, and the
// total is the sum of the values greater than or equal to it. Store that in the node and carry
// on to the left.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack; the running total is one number.

public final class GreaterSumTree {

	public static TreeNode gst(TreeNode root) {
		AtomicInteger sum = new AtomicInteger(0); // TOTAL: sum of the values visited so far
		transform(root, sum);
		return root;
	}

	private static void transform(TreeNode root, AtomicInteger sum) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		transform(root.right, sum); // WALK: the larger values first
		sum.getAndAdd(root.val); // ADD: the total now covers this node and everything above it
		root.val = sum.get(); // STORE: the greater-or-equal sum replaces the value
		transform(root.left, sum); // WALK: the smaller values last
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(1);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(0);
		root.left.right = new TreeNode(2);
		root.left.right.right = new TreeNode(3);

		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(7);
		root.right.right.right = new TreeNode(8);

		inOrder(root); // 0 1 2 3 4 5 6 7 8
		System.out.println();
		System.out.println();

		gst(root);

		inOrder(root); // 36 36 35 33 30 26 21 15 8
		System.out.println();
		System.out.println();
	}
}