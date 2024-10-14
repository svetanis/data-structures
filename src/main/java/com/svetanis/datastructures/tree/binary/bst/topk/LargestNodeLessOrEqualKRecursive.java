package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class LargestNodeLessOrEqualKRecursive {

	public static Node largestLessOrEqual(Node root, int key) {

		if (isLeaf(root) && root.data > key) {
			return null;
		}

		boolean one = isNull(root.right) && root.data <= key;
		if (one || root.right.data > key && root.data < key) {
			return root;
		}

		if (root.data >= key) {
			return largestLessOrEqual(root.left, key);
		} else {
			return largestLessOrEqual(root.right, key);
		}
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