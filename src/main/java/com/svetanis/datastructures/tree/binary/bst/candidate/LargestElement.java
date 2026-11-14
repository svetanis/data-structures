package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Largest element in a BST (no LeetCode number)
//
// Input: root of a BST, possibly empty. Returns the node holding the largest value, or null for an
// empty tree. Two versions: largest is a loop, largestRecursive is the same walk as recursion.
//
// The one idea: in a BST everything in a node's right subtree is larger than the node, so the
// largest value is reached by always going right. The node with no right child is the largest.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) for largest; O(h) for largestRecursive, which is as deep as the right edge is long.
//
// Siblings: SecondLargestIterative and SecondLargestRecursive find the node one place before this
// one in order.

public final class LargestElement {

	public static TreeNode largestRecursive(TreeNode root) {
		// an empty tree has no largest element; SecondLargestIterative and
		// SecondLargestRecursive also answer null for the same input
		if (isNull(root)) {
			return null; // BASE: empty tree
		}
		if (isNull(root.right)) {
			return root; // RETURN: no right child, this is the largest
		}
		return largestRecursive(root.right); // WALK: the right subtree holds the larger values
	}

	public static TreeNode largest(TreeNode root) {
		if (isNull(root)) {
			return null; // BASE: empty tree
		}
		while (isNotNull(root.right)) {
			root = root.right; // WALK: always right
		}
		return root;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(8);
		root.left.left = newNode(1);
		root.left.right = newNode(4);
		root.right.left = newNode(7);
		root.right.right = newNode(12);
		root.right.right.left = newNode(10);
		root.right.right.left.left = newNode(9);
		root.right.right.left.right = newNode(11);

		inOrder(root); // prints 1 3 4 5 7 8 9 10 11 12
		System.out.println();
		System.out.println(largest(root)); // 12
		System.out.println(largestRecursive(root)); // 12

		TreeNode root1 = newNode(5);
		root1.left = newNode(3);
		root1.right = newNode(8);
		root1.left.left = newNode(1);
		root1.left.right = newNode(4);
		root1.right.left = newNode(7);
		root1.right.right = newNode(9);
		inOrder(root1); // prints 1 3 4 5 7 8 9
		System.out.println();
		System.out.println(largest(root1)); // 9
		System.out.println(largestRecursive(root1)); // 9

		// the empty tree
		System.out.println(largest(null)); // null
		System.out.println(largestRecursive(null)); // null
	}
}