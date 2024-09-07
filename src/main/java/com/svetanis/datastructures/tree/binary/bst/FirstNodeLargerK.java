package com.svetanis.datastructures.tree.binary.bst;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a key k
// find first entry larger than k
// that would appear in an in-order walk

public final class FirstNodeLargerK {

	public static Optional<Node> firstLargerK(Node root, int key) {
		Node node = null;
		boolean found = false;
		while (isNotNull(root)) {
			if (root.data == key) {
				found = true;
				root = root.right;
			} else if (root.data > key) {
				node = root;
				root = root.left;
			} else { // root.data < key
				root = root.right;
			}
		}
		return found ? of(node) : absent();
	}

	public static void main(String[] args) {
		Node root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(70);
		root.left.left = newNode(20);
		root.left.right = newNode(40);
		root.right.left = newNode(60);
		root.right.right = newNode(80);

		inOrder(root);
		System.out.println();
		System.out.println(firstLargerK(root, 20));
	}
}