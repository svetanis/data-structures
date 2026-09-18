package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class LargestNodeLessOrEqualKIterative {
	// Time Complexity: O(h)

	public static Node largestLessOrEqual(Node root, int key) {
		// null, not root: with the root as the starting candidate, a tree
		// whose every value is above key returns the root -- a number
		// larger than the one it was asked for, and no way to tell it
		// apart from a real answer
		Node node = null;
		while (isNotNull(root)) {
			// <=, so an exact match is the answer. a strict < walks past
			// it and answers the strict predecessor instead
			if (root.data <= key) {
				node = root;
				root = root.right;
			} else {
				root = root.left;
			}
		}
		return node;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(2);
		root.right = newNode(12);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		root.right.left = newNode(9);
		root.right.right = newNode(21);
		root.right.right.left = newNode(19);
		root.right.right.right = newNode(25);

		inOrder(root);
		System.out.println();
		System.out.println(largestLessOrEqual(root, 24)); // 21
		System.out.println(largestLessOrEqual(root, 4)); // 3

		// key present in the tree. a strict < printed 9 here
		System.out.println(largestLessOrEqual(root, 12)); // 12
		// below every value. seeded with the root this printed 5
		System.out.println(largestLessOrEqual(root, 0)); // null
	}
}