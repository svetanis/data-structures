package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Floor of k in a BST (no LeetCode number; LC 2476, Closest Nodes Queries in a Binary Search
// Tree, asks for this and its mirror, the ceiling, together)
//
// Input: root of a BST and an int key. The key need not be in the tree. Returns the node holding
// the largest value that is less than or equal to key, or null when every value is larger.
//
// The one idea: walk down as a search for key would. At a node with value <= key, that node
// qualifies; anything larger that still qualifies is on its right, so remember it and go right.
// At a node above key, nothing on its right qualifies: go left. The last node remembered is the
// largest one that is at or below key. An exact match is itself the answer, so the test is <=; a
// strict < would walk past it and answer the strict predecessor. The candidate starts as null, not
// the root: a tree whose every value is above key must answer null, not a value larger than key.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- one variable.
//
// Siblings: LargestNodeLessOrEqualKRecursive is this loop as recursion.
// FirstNodeLargerOrEqualKIterative is the mirror, the ceiling.

public final class LargestNodeLessOrEqualKIterative {

	public static TreeNode largestLessOrEqual(TreeNode root, int key) {
		TreeNode node = null; // the best qualifying node so far; null when none was met
		while (isNotNull(root)) {
			if (root.val <= key) {
				node = root; // RECORD: it qualifies; a larger one may be on the right
				root = root.right;
			} else {
				root = root.left; // SKIP: too large, and so is everything on its right
			}
		}
		return node;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(2);
		root.right = newNode(12);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		root.right.left = newNode(9);
		root.right.right = newNode(21);
		root.right.right.left = newNode(19);
		root.right.right.right = newNode(25);

		inOrder(root); // prints 1 2 3 5 9 12 19 21 25
		System.out.println();
		System.out.println(largestLessOrEqual(root, 24)); // 21
		System.out.println(largestLessOrEqual(root, 4)); // 3

		// key present in the tree
		System.out.println(largestLessOrEqual(root, 12)); // 12
		// below every value
		System.out.println(largestLessOrEqual(root, 0)); // null
	}
}