package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a binary tree, invert it and
// return the new value. you may invert
// it in-place. to invert a binary tree,
// switch the left subtree and the right
// subtree, and invert them both. inverting
// an empty tree does nothing.

public final class InvertBinaryTreeRecursive {
	// Time Complexity: O(n)
	// Space Complexity: (h)

	public static Node invert(Node root) {
		if (isNull(root)) {
			return root;
		}
		// do the subtrees
		Node left = invert(root.left);
		Node right = invert(root.right);
		return new Node(root.data, right, left);
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		inOrder(root); // 4 2 5 1 6 3 7
		System.out.println();
		Node inverted = invert(root);
		inOrder(inverted); // 7 3 6 1 5 2 4
		System.out.println();
	}
}
