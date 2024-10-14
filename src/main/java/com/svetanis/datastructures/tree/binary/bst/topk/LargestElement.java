package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class LargestElement {

	public static Node largestRecursive(Node root) {
		while (isNotNull(root.right)) {
			return largestRecursive(root.right);
		}
		return root;
	}

	public static Node largest(Node root) {
		while (isNotNull(root.right)) {
			root = root.right;
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(8);
		root.left.left = newNode(1);
		root.left.right = newNode(4);
		root.right.left = newNode(7);
		root.right.right = newNode(12);
		root.right.right.left = newNode(10);
		root.right.right.left.left = newNode(9);
		root.right.right.left.right = newNode(11);

		inOrder(root);
		System.out.println();
		System.out.println(largest(root));
		System.out.println(largestRecursive(root));

		Node root1 = newNode(5);
		root1.left = newNode(3);
		root1.right = newNode(8);
		root1.left.left = newNode(1);
		root1.left.right = newNode(4);
		root1.right.left = newNode(7);
		root1.right.right = newNode(9);
		inOrder(root1);
		System.out.println();
		System.out.println(largest(root1));
		System.out.println(largestRecursive(root1));

	}
}