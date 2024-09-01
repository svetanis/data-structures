package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, populate an array 
// to represent the averages of all of its levels.

public final class LevelAverages {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static ImmutableList<Double> lot(Node root) {
		if (isNull(root)) {
			return newList();
		}

		Queue<Node> queue = newLinkedList();
		queue.offer(root);
		List<Double> list = newArrayList();

		while (!queue.isEmpty()) {
			double sum = 0;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				sum += node.data;
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
			list.add(sum / size);
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.right = new Node(6);
		print(lot(root));
	}
}
