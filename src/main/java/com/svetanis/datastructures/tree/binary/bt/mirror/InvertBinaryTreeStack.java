package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a binary tree, invert it and
// return the new value. you may invert
// it in-place. to invert a binary tree,
// switch the left subtree and the right
// subtree, and invert them both. inverting
// an empty tree does nothing.

public final class InvertBinaryTreeStack {
	// Time Complexity: O(n)
	// Space Complexity: (n)

	public static void invert(Node root) {
		if (isNull(root)) {
			return;
		}
		Deque<Node> stack = new ArrayDeque<>();
		stack.add(root);
		while (!stack.isEmpty()) {
			Node node = stack.poll();
			swap(node);
			if (isNotNull(root.right)) {
				stack.add(root.right);
			}
			if (isNotNull(root.left)) {
				stack.add(root.left);
			}
		}
	}

	private static void swap(Node root) {
		// swap the nodes
		Node temp = root.left;
		root.left = root.right;
		root.right = temp;
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

		invert(root);

		inOrder(root); // 7 3 6 1 5 2 4
		System.out.println();
	}
}
