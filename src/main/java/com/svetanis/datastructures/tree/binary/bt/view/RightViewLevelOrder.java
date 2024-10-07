package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, return an array 
// containing nodes in its right view. 

// The right view of a binary tree is 
// the set of nodes visible when the 
// tree is seen from the right side.

public final class RightViewLevelOrder {

	public static ImmutableList<Node> rightView(Node root) {
		// Time Complexity: O(n)

		if (isNull(root)) {
			return newList();
		}
		List<Node> list = newArrayList();
		Queue<Node> queue = newLinkedList();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			list.add(queue.peek());
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		print(rightView(root1)); // 1, 3, 7

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.left.left.left = newNode(3);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		print(rightView(root2)); // 12, 1, 5, 3
	}
}
