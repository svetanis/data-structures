package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Second largest element in a BST (no LeetCode number; LC 230, Kth Smallest Element in a BST, and
// the rank/ package ask the same kind of question for any k)
//
// Input: root of a BST with distinct values, possibly empty. Returns the node holding the second
// largest value, or null when the tree has fewer than two nodes.
//
// The one idea: the largest node is at the end of the right edge, and the second largest is one of
// two nodes next to it. If the largest has no left subtree, the second largest is its parent. If it
// has one, the second largest is the largest node of that left subtree. So walk right; at a node
// whose right child is a leaf, that node is the answer (the right child is the largest, with no
// left subtree); at a node with no right child but a left subtree, this node is the largest, and
// the answer is the largest of its left subtree. In every other case, step right.
//
// Time: O(h) -- one step down the right edge, then at most one more down the left subtree's
// right edge, h <= n.
// Space: O(1) -- one variable.
//
// Siblings: SecondLargestRecursive is the same walk as recursion.

public final class SecondLargestIterative {

	public static TreeNode secondLargest(TreeNode root) {
		while (isNotNull(root)) {
			// this is a parent of the largest
			// and largest has no left subtree
			if (isNotNull(root.right) && isLeaf(root.right)) {
				return root; // RETURN: the right child is the largest, this is the one before it
			}

			// largest has left subtree and
			// second largest is largest element
			// in the left subtree
			if (isNull(root.right) && isNotNull(root.left)) {
				return largest(root.left); // RETURN: the largest of the left subtree
			}
			// otherwise keep going to the right
			root = root.right; // WALK: down the right edge
		}
		return root; // RETURN: null -- the tree was empty or held one node
	}

	public static TreeNode largest(TreeNode root) {
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
		System.out.println(secondLargest(root)); // 11

		TreeNode root1 = newNode(5);
		root1.left = newNode(3);
		root1.right = newNode(8);
		root1.left.left = newNode(1);
		root1.left.right = newNode(4);
		root1.right.left = newNode(7);
		root1.right.right = newNode(9);
		inOrder(root1); // prints 1 3 4 5 7 8 9
		System.out.println();
		System.out.println(secondLargest(root1)); // 8

		System.out.println(secondLargest(null)); // null
		System.out.println(secondLargest(newNode(1))); // null
	}
}