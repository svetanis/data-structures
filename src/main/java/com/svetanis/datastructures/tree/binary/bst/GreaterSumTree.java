package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 538. Convert BST to Greater Tree

public final class GreaterSumTree {
	// Time Complexity: O(n)

	public static Node gst(Node root) {
		AtomicInteger sum = new AtomicInteger(0);
		transform(root, sum);
		return root;
	}

	private static void transform(Node root, AtomicInteger sum) {
		if (root == null) {
			return;
		}
		// recur for right subtree
		transform(root.right, sum);
		// update sum
		sum.getAndAdd(root.data);
		// store sum in current node
		root.data = sum.get();
		// recur for left subtree
		transform(root.left, sum);
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(1);
		root.right = new Node(6);
		root.left.left = new Node(0);
		root.left.right = new Node(2);
		root.left.right.right = new Node(3);

		root.right.left = new Node(5);
		root.right.right = new Node(7);
		root.right.right.right = new Node(8);

		inOrder(root);
		System.out.println();
		System.out.println();

		gst(root);

		inOrder(root);
		System.out.println();
		System.out.println();
	}
}