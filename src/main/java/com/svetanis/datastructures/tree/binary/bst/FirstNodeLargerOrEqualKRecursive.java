package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the smallest number 
// that is greater than or equal to k

public final class FirstNodeLargerOrEqualKRecursive {

	public static Node firstLargerOrEqualK(Node root, int key) {

		if (isLeaf(root) && root.data < key) {
			return null;
		}

		boolean one = isNull(root.left) && root.data >= key;

		if (one || root.left.data < key && root.data > key) {
			return root;
		}

		if (root.data <= key) {
			return firstLargerOrEqualK(root.right, key);
		} else { // root.data > k
			return firstLargerOrEqualK(root.left, key);
		}
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
		System.out.println(firstLargerOrEqualK(root, 18));
	}
}