package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// First occurrence of a key in a BST (no LeetCode number; LC 34, Find First and Last Position of
// Element in Sorted Array, is the same question on a sorted array)
//
// Input: root of a BST and an int key. The values may repeat, and equal values may sit on either
// side of an equal node (the BST rule is left <= node <= right). Returns the node holding key that
// comes first in an in-order walk, or null when key is not in the tree.
//
// The one idea: in-order means smallest first, so of the nodes holding key the first is the one
// furthest left. At a node holding key, ask its left subtree for an earlier match; if there is
// none, this node is the first. At any other node, only one side can hold key: go that way.
//
// Time: O(h) -- one call per level, h <= n.
// Space: O(h) -- the recursion is as deep as the path it walks.
//
// Siblings: FirstOccurrenceIterative is the same search as a loop that remembers the last match.

public final class FirstOccurrenceRecursive {

	public static TreeNode firstOccurrence(TreeNode root, int key) {

		if (isNull(root)) {
			return null; // BASE: key is not below an empty tree
		} else if (root.val == key) {
			// search for the leftmost in the left subtree
			TreeNode left = firstOccurrence(root.left, key); // WALK: look for an earlier match
			return isNull(left) ? root : left; // RETURN: the earlier match, else this node
		}

		if (key > root.val) {
			return firstOccurrence(root.right, key); // WALK: key can only be on the right
		} else {
			return firstOccurrence(root.left, key); // WALK: key can only be on the left
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(6);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);

		inOrder(root); // prints 1 2 3 4 6 6
		System.out.println();
		System.out.println(firstOccurrence(root, 6)); // 6
		System.out.println(firstOccurrence(root, 1)); // 1
		System.out.println(firstOccurrence(root, 9)); // null
	}
}