package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, populate an array to represent its zigzag level order traversal. 
// You should populate the values of all nodes of the first level from left to right, 
// then right to left for the next level and keep alternating in the same manner for the following levels.

public final class ZigZagQueue {

	public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
		// Time Complexity: O(n)
		// Space Complexity: O(n)

		if (isNull(root)) {
			return newList();
		}

		boolean leftToRight = true;
		Queue<Node> queue = newLinkedList();
		queue.offer(root);
		List<ImmutableList<Integer>> lists = newArrayList();

		while (!queue.isEmpty()) {
			int size = queue.size();
			List<Integer> list = newLinkedList();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (leftToRight) {
					list.add(node.data);
				} else {
					list.add(0, node.data);
				}
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
			lists.add(newList(list));
			leftToRight = !leftToRight;
		}
		return newList(lists);
	}

	public static void main(String[] args) {
		Node root = newNode(12);
		root.left = newNode(7);
		root.right = newNode(1);
		root.left.left = newNode(9);
		root.right.left = newNode(10);
		root.right.right = newNode(5);
		root.right.left.left = newNode(20);
		root.right.left.right = newNode(17);
		printLists(traverse(root));
	}
}
