package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// find the second largest element in BST

public final class SecondLargestRecursive {
	// Best Time Complexity (balanced): O(log n)

	public static Node secondLargest(Node root) {

		// empty tree
		if (isNull(root)) {
			return null;
		}

		// this is a parent of the largest
		// and largest has no left subtree
		if (isNotNull(root.right) && isLeaf(root.right)) {
			return root;
		}

		// largest has left subtree and
		// second largest is largest element
		// in the left subtree
		if (isNull(root.right) && isNotNull(root.left)) {
			return largestRecursive(root.left);
		}

		// otherwise keep going to the right
		return secondLargest(root.right);
	}

	public static Node largestRecursive(Node root) {
		while (isNotNull(root.right)) {
			return largestRecursive(root.right);
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
		System.out.println(secondLargest(root));

		Node root1 = newNode(5);
		root1.left = newNode(3);
		root1.right = newNode(8);
		root1.left.left = newNode(1);
		root1.left.right = newNode(4);
		root1.right.left = newNode(7);
		root1.right.right = newNode(9);
		inOrder(root1);
		System.out.println();
		System.out.println(secondLargest(root1));
	}
}