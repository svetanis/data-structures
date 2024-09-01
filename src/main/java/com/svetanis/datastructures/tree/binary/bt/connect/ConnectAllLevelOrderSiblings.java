package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.Queue;

// Given a binary tree, 
// connect each node with 
// its level order successor. 
// The last node of each level 
// should point to the first 
// node of the next level.

public final class ConnectAllLevelOrderSiblings {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static void connect(Node root) {
		Queue<Node> queue = newLinkedList();
		queue.offer(root);
		Node prev = null;
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			if (prev != null) {
				prev.next = curr;
			}
			prev = curr;

			if (curr.left != null) {
				queue.offer(curr.left);
			}
			if (curr.right != null) {
				queue.offer(curr.right);
			}
		}
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);

		connect(root1);
		root1.printTree();

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);

		connect(root2);
		root2.printTree();
	}
}
