package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the smallest number
// that is greater than or equal to k

public final class FirstNodeLargerOrEqualKIterative {
	// Time Complexity: O(h)

	public static Node firstLargerOrEqualK(Node root, int key) {
		Node node = null;
		while (isNotNull(root)) {
			// >=, so an exact match is the answer and there is nothing
			// smaller left to look for. a separate == branch that walks
			// right instead skips it and returns the strict successor,
			// which is a different question and gives null when key is
			// the largest value in the tree
			if (root.data >= key) {
				node = root;
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return node;
	}

	public static void main(String[] args) {
		Node root = newNode(19);
		root.left = newNode(7);
		root.right = newNode(21);
		root.left.left = newNode(3);
		root.left.right = newNode(11);
		root.left.right.left = newNode(9);
		root.left.right.right = newNode(14);

		inOrder(root);
		System.out.println();
		System.out.println(firstLargerOrEqualK(root, 20)); // 21

		// key present in the tree. walking right on == printed null here
		System.out.println(firstLargerOrEqualK(root, 21)); // 21
		System.out.println(firstLargerOrEqualK(root, 3)); // 3
		// above every value
		System.out.println(firstLargerOrEqualK(root, 99)); // null
	}
}