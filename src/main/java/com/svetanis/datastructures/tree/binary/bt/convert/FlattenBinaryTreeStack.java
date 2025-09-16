package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List

public final class FlattenBinaryTreeStack {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static void flatten(Node root) {
		if (root == null) {
			return;
		}
		Deque<Node> dq = new ArrayDeque<>();
		dq.push(root);
		while (!dq.isEmpty()) {
			Node node = dq.pop();
			if (node.right != null) {
				dq.push(node.right);
			}
			if (node.left != null) {
				dq.push(node.left);
			}
			if (!dq.isEmpty()) {
				node.right = dq.peek();
			}
			node.left = null;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.right = newNode(6);
		flatten(root); // 1 2 3 4 5 6
		Nodes.preOrder(root);
		System.out.println();
		Node root1 = newNode(0);
		flatten(root1); // 0
		Nodes.preOrder(root1);
		System.out.println();
	}
}
