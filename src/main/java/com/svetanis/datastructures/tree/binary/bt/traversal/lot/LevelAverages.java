package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 637. Average of Levels in Binary Tree

// Given a binary tree, populate an array 
// to represent the averages of all of its levels.

public final class LevelAverages {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Double> lot(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		List<Double> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			double sum = 0;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				sum += node.data;
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			list.add(sum / size);
		}
		return list;
	}

	public static void main(String[] args) {
		Node root = new Node(3);
		root.left = new Node(9);
		root.right = new Node(20);
		root.right.left = new Node(15);
		root.right.right = new Node(7);
		System.out.println(lot(root)); // 3.0 14.5 11.0

		Node root1 = new Node(3);
		root1.left = new Node(9);
		root1.right = new Node(20);
		root1.left.left = new Node(15);
		root1.left.right = new Node(7);
		System.out.println(lot(root1)); // 3.0 14.5 11.0
	}
}
