package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Floor of k in a BST (no LeetCode number; LC 2476, Closest Nodes Queries in a Binary Search
// Tree, asks for this and its mirror, the ceiling, together)
//
// Input: root of a BST and an int key. The key need not be in the tree. Returns the node holding
// the largest value that is less than or equal to key, or null when every value is larger.
//
// The one idea: at a node with value <= key, that node qualifies, and anything larger that still
// qualifies is in its right subtree: ask the right subtree, and if it has no answer, this node is
// the answer. At a node above key, nothing on its right qualifies: the answer is whatever the left
// subtree gives. Each call decides from its own node's value alone; reading a child's value to
// recognise the answer early fails on every node with no such child.
//
// Time: O(h) -- one call per level, h <= n.
// Space: O(h) -- the recursion is as deep as the path it walks.
//
// Siblings: LargestNodeLessOrEqualKIterative is this as a loop with no stack.
// FirstNodeLargerOrEqualKRecursive is the mirror, the ceiling.

public final class LargestNodeLessOrEqualKRecursive {

	public static TreeNode largestLessOrEqual(TreeNode root, int key) {
		if (isNull(root)) {
			return null; // BASE: nothing qualifies below an empty tree
		}
		if (root.val <= key) {
			// a candidate. anything larger that still qualifies is on
			// the right, so keep this one and go look
			TreeNode better = largestLessOrEqual(root.right, key); // WALK: look for a larger one
			return isNull(better) ? root : better; // RETURN: the right's answer, else this node
		}
		return largestLessOrEqual(root.left, key); // SKIP: too large, the answer is on the left
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

		System.out.println(largestLessOrEqual(root, 12)); // 12
		System.out.println(largestLessOrEqual(root, 0)); // null
		System.out.println(largestLessOrEqual(null, 1)); // null
	}
}