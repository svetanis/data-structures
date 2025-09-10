package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 104. Maximum Depth of Binary Tree

// Given a binary tree, find its maximum depth (or height).
// Max depth is the number of nodes along 
// the longest path from the root node to
// the farthest leaf node

public final class MaxDepthNodesQueue {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lot(Node root) {
		if (isNull(root)) {
			return 0;
		}
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
