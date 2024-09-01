package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, find its maximum depth (or height).

public final class MaxDepth {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lot(Node root) {
		Queue<Node> queue = newLinkedList();
		queue.offer(root);

		int height = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			height++;
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
		}
		return height;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.right = new Node(6);
		System.out.println(lot(root));
	}
}
