package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a key,
// find the node containing k
// that would appear first in
// an in-order walk

public final class FirstOccurrenceRecursive {

	public static Node firstOccurrence(Node root, int key) {

		if (isNull(root)) {
			return null;
		} else if (root.data == key) {
			// search for the leftmost in the left subtree
			Node left = firstOccurrence(root.left, key);
			return isNull(left) ? root : left;
		}

		if (key > root.data) {
			return firstOccurrence(root.right, key);
		} else {
			return firstOccurrence(root.left, key);
		}
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(6);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);

		inOrder(root);
		System.out.println();
		System.out.println(firstOccurrence(root, 6));
	}
}