package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Ceiling of k in a BST (no LeetCode number; LC 2476, Closest Nodes Queries in a Binary Search
// Tree, asks for this and its mirror, the floor, together)
//
// Input: root of a BST and an int key. The key need not be in the tree. Returns the node holding
// the smallest value that is greater than or equal to key, or null when every value is smaller.
//
// The one idea: walk down as a search for key would. At a node with value >= key, that node
// qualifies; anything smaller that still qualifies is on its left, so remember it and go left.
// At a node below key, nothing on its left qualifies: go right. The last node remembered is the
// smallest one that is at or above key. An exact match is itself the answer, so it is taken like
// any other qualifying node; a separate branch that walked right on a match would skip it and
// return the strict successor, which is a different question.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- one variable.
//
// Siblings: FirstNodeLargerOrEqualKRecursive is this loop as recursion. FirstNodeLargerK asks the
// strict question (larger than a key that must be present). LargestNodeLessOrEqualKIterative is
// the mirror, the floor.

public final class FirstNodeLargerOrEqualKIterative {

	public static TreeNode firstLargerOrEqualK(TreeNode root, int key) {
		TreeNode node = null; // the best qualifying node so far; null when none was met
		while (isNotNull(root)) {
			if (root.val >= key) {
				node = root; // RECORD: it qualifies; a smaller one may be on the left
				root = root.left;
			} else {
				root = root.right; // SKIP: too small, and so is everything on its left
			}
		}
		return node;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(19);
		root.left = newNode(7);
		root.right = newNode(21);
		root.left.left = newNode(3);
		root.left.right = newNode(11);
		root.left.right.left = newNode(9);
		root.left.right.right = newNode(14);

		inOrder(root); // prints 3 7 9 11 14 19 21
		System.out.println();
		System.out.println(firstLargerOrEqualK(root, 20)); // 21

		// key present in the tree
		System.out.println(firstLargerOrEqualK(root, 21)); // 21
		System.out.println(firstLargerOrEqualK(root, 3)); // 3
		// above every value
		System.out.println(firstLargerOrEqualK(root, 99)); // null
	}
}