package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class FirstNodeLargerOrEqualKIterative {

	public static Node firstLargerOrEqualK(Node root, int key) {
		Node node = null;
		while (isNotNull(root)) {
			if (root.data == key) {
				root = root.right;
			} else if (root.data > key) {
				node = root;
				root = root.left;
			} else { // root.data < key
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
		System.out.println(firstLargerOrEqualK(root, 20));
	}
}