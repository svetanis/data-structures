package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// First occurrence of a key in a BST (no LeetCode number; LC 34, Find First and Last Position of
// Element in Sorted Array, is the same question on a sorted array)
//
// Input: root of a BST and an int key. The values may repeat, and equal values may sit on either
// side of an equal node (the BST rule is left <= node <= right). Returns the node holding key that
// comes first in an in-order walk, or null when key is not in the tree.
//
// The one idea: in-order means smallest first, so of the nodes holding key the first is the one
// furthest left. Search as usual, and each time a node holds key, remember it and keep going left:
// a match further down on the left would come earlier. Going left from a match can pass through
// smaller values, so the search continues past them, turning right when a value is below key. The
// last match remembered is the earliest one.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- one variable.
//
// Siblings: FirstOccurrenceRecursive is the same search as recursion.

public final class FirstOccurrenceIterative {

	public static TreeNode firstOccurrence(TreeNode root, int key) {
		TreeNode first = null; // the earliest match met so far; null when none was met
		while (isNotNull(root)) {
			if (root.val < key) {
				root = root.right; // WALK: key can only be on the right
			} else if (root.val > key) {
				root = root.left; // WALK: key can only be on the left
			} else {
				first = root; // RECORD: a match; an earlier one can only be on its left
				root = root.left;
			}
		}
		return first;
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

		// the key two levels down
		System.out.println(firstOccurrence(root, 1)); // 1
		System.out.println(firstOccurrence(root, 9)); // null

		// 5 / 3 / (5 on the right of 3): the earlier 5 is not on the left chain of the top 5
		TreeNode both = newNode(5);
		both.left = newNode(3);
		both.left.right = newNode(5);
		System.out.println(firstOccurrence(both, 5) == both.left.right); // true
	}
}