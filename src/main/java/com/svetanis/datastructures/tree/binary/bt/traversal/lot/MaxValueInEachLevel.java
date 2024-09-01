package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.max;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Find the largest value on each level of a binary tree.

public final class MaxValueInEachLevel {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static ImmutableList<Integer> lot(Node root) {
		if (isNull(root)) {
			return newList();
		}

		Queue<Node> queue = newLinkedList();
		queue.offer(root);

		List<Integer> list = newArrayList();
		while (!queue.isEmpty()) {
			int max = 0;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				max = max(max, node.data);
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
			list.add(max);
		}
		return newList(list);
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
