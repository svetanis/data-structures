package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Find the minimum depth of a binary tree. 
// The min depth is the number of nodes along the shortest 
// path from the root node to the nearest leaf node.

public final class MinDepth {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lot(Node root) {
		int minDepth = 0;
		Queue<Node> queue = newLinkedList();
		queue.offer(root);

		while (!queue.isEmpty()) {
			minDepth++;
			for (int i = 0; i < queue.size(); i++) {
				Node node = queue.poll();
				if (isLeaf(node)) {
					return minDepth;
				}
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
		}
		return minDepth;
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
