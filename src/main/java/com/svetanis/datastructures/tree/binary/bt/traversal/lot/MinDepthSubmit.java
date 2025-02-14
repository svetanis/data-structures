package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 111. Minimum Depth of Binary Tree

// Find the minimum depth of a binary tree. 
// The min depth is the number of nodes along the shortest 
// path from the root node to the nearest leaf node.

public final class MinDepthSubmit {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lot(Node root) {
		if (root == null) {
			return 0;
		}
		int minDepth = 0;
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			minDepth++;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (node.left == null && node.right == null) {
					return minDepth;
				}
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return minDepth;
	}

	public static int minDepth(Node root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null) {
			return 1 + minDepth(root.right);
		}
		if (root.right == null) {
			return 1 + minDepth(root.left);
		}
		return 1 + Math.min(minDepth(root.left), minDepth(root.right));
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
