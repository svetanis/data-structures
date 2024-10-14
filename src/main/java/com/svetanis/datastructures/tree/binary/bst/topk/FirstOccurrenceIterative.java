package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a key,
// find the node containing k
// that would appear first in
// an in-order walk

public final class FirstOccurrenceIterative {

	public static Node firstOccurrence(Node root, int key) {
		while (isNotNull(root)) {
			if (root.data < key) {
				root = root.right;
			} else if (root.data > key) {
				root = root.left;
			} else {
				// search for the leftmost in the left subtree
				Node left = root.left;
				while (isNotNull(left) && left.data == key) {
					root = left;
					left = left.left;
				}
			}
			return root;
		}
		return null;
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