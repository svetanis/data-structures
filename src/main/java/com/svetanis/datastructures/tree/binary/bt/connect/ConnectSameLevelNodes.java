package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.LinkedList;
import java.util.Queue;

// 117. Populating Next Right Pointers in Each Node I

// Given a binary tree, 
// connect each node with 
// its level order successor. 
// The last node of each level 
// should point to a null node.

public final class ConnectSameLevelNodes {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static void connect(Node root) {
		if (root == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node prev = null;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
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
	}

	public static void main(String[] args) {
		Node root1 = newNode(12);
		root1.left = newNode(7);
		root1.right = newNode(1);
		root1.left.left = newNode(9);
		root1.right.left = newNode(10);
		root1.right.right = newNode(5);

		connect(root1);
		root1.printLevelOrder();

		Node root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.left = newNode(6);
		root2.right.right = newNode(7);

		connect(root2);
		root2.printLevelOrder();
	}
}
