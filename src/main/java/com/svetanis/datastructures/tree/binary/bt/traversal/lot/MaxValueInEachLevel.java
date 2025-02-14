package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 515. Find Largest Value in Each Tree Row

// Find the largest value on each level of a binary tree.

public final class MaxValueInEachLevel {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Integer> lot(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<Node> queue = new ArrayDeque<>();
		queue.offer(root);
		List<Integer> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			int max = queue.peek().data;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				max = Math.max(max, node.data);
				if (node.left != null) {
					queue.offer(node.left);
				}
				if (node.right != null) {
					queue.offer(node.right);
				}
			}
			list.add(max);
		}
		return list;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(9);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.right = newNode(7);
		print(lot(root));
	}
}
