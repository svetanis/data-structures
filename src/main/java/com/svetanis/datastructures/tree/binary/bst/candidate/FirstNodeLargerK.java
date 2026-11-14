package com.svetanis.datastructures.tree.binary.bst.candidate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// First Node Larger Than K in a BST (no LeetCode number; the same step as LC 285, Inorder Successor
// in BST, when the key is a node of the tree)
//
// Input: root of a BST with distinct values and a key k. The key has to be present in the tree: that
// is the contract of this file. Returns the first node after k in an in-order walk, the smallest
// value larger than k, or absent when k is not in the tree or nothing larger follows it.
//
// The one idea: walk down as a search for k would. At a node above k, that node is a candidate for
// the answer and anything smaller that is still above k is on its left: remember it and go left.
// At a node below k, go right. At k itself, the answer is not k, so go right -- the larger values
// are there. The last candidate remembered is the smallest value above k. A flag records whether k
// was met on the way; without it, a key that is not in the tree would still get an answer.
//
// Time: O(h) -- one step down per level, h <= n.
// Space: O(1) -- two variables.
//
// Siblings: FirstNodeLargerOrEqualKIterative answers without the contract: the smallest value at or
// above k whether or not k is present, as a plain node (null for none). InorderSuccessor does the
// same job as this file for a key that is a node of the tree, with no flag.

public final class FirstNodeLargerK {

	public static Optional<TreeNode> firstLargerK(TreeNode root, int key) {
		TreeNode node = null;
		boolean found = false;
		while (isNotNull(root)) {
			if (root.val == key) {
				found = true; // MARK: the key is in the tree
				root = root.right; // WALK: the answer is above the key
			} else if (root.val > key) {
				node = root; // RECORD: a candidate, the smaller ones are on the left
				root = root.left;
			} else { // root.val < key
				root = root.right; // SKIP: this node is too small, the answer is above it
			}
		}
		// key can be present and still have nothing above it -- it is the
		// largest value in the tree. of(null) throws, so the two reasons
		// for "no answer" both have to come back as absent
		return found && node != null ? of(node) : empty();
	}

	public static void main(String[] args) {
		TreeNode root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(70);
		root.left.left = newNode(20);
		root.left.right = newNode(40);
		root.right.left = newNode(60);
		root.right.right = newNode(80);

		inOrder(root); // prints 20 30 40 50 60 70 80
		System.out.println();
		System.out.println(firstLargerK(root, 20)); // Optional[30]

		// key is the largest value, so nothing follows it
		System.out.println(firstLargerK(root, 80)); // Optional.empty
		// key is not in the tree at all
		System.out.println(firstLargerK(root, 45)); // Optional.empty
	}
}