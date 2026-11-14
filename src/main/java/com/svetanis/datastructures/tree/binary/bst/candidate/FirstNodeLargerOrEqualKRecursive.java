package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Ceiling of k in a BST (no LeetCode number; LC 2476, Closest Nodes Queries in a Binary Search
// Tree, asks for this and its mirror, the floor, together)
//
// Input: root of a BST and an int key. The key need not be in the tree. Returns the node holding
// the smallest value that is greater than or equal to key, or null when every value is smaller.
//
// The one idea: at a node with value >= key, that node qualifies, and anything smaller that still
// qualifies is in its left subtree: ask the left subtree, and if it has no answer, this node is
// the answer. At a node below key, nothing on its left qualifies: the answer is whatever the right
// subtree gives. Each call decides from its own node's value alone; reading a child's value to
// recognise the answer early fails on every node with no such child.
//
// Time: O(h) -- one call per level, h <= n.
// Space: O(h) -- the recursion is as deep as the path it walks.
//
// Siblings: FirstNodeLargerOrEqualKIterative is this as a loop with no stack.
// LargestNodeLessOrEqualKRecursive is the mirror, the floor.

public final class FirstNodeLargerOrEqualKRecursive {

	public static TreeNode firstLargerOrEqualK(TreeNode root, int key) {
		if (isNull(root)) {
			return null; // BASE: nothing qualifies below an empty tree
		}
		if (root.val >= key) {
			// a candidate. anything smaller that still qualifies is
			// on the left, so keep this one and go look
			TreeNode better = firstLargerOrEqualK(root.left, key); // WALK: look for a smaller one
			return isNull(better) ? root : better; // RETURN: the left's answer, else this node
		}
		return firstLargerOrEqualK(root.right, key); // SKIP: too small, the answer is on the right
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
		System.out.println(firstLargerOrEqualK(root, 18)); // 19

		System.out.println(firstLargerOrEqualK(root, 21)); // 21
		System.out.println(firstLargerOrEqualK(root, 4)); // 7
		System.out.println(firstLargerOrEqualK(root, 99)); // null
		System.out.println(firstLargerOrEqualK(null, 1)); // null
	}
}