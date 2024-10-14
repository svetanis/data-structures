package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class LargestNodeLessOrEqualKIterative {

	public static Node largestLessOrEqual(Node root, int key) {
		Node node = root;
		while (isNotNull(root)) {
			if (root.data < key) {
				node = root;
				root = root.right;
			} else { // root.data >= key
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
		System.out.println(largestLessOrEqual(root, 24));
		System.out.println(largestLessOrEqual(root, 4));
	}
}