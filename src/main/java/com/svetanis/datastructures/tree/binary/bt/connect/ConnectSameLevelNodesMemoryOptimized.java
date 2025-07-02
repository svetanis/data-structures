package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

// 116. Populating Next Right Pointers in Each Node
// 117. Populating Next Right Pointers in Each Node I

// Given a binary tree, 
// connect each node with 
// its level order successor. 
// The last node of each level 
// should point to a null node.

public final class ConnectSameLevelNodesMemoryOptimized {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static Node connect(Node root) {
		Node dummy = new Node(0);
		Node curr = root;
		while (curr != null) {
			Node tail = dummy;
			while (curr != null) {
				if (curr.left != null) {
					tail.next = curr.left;
					tail = tail.next;
				}
				if (curr.right != null) {
					tail.next = curr.right;
					tail = tail.next;
				}
				curr = curr.next;
			}
			curr = dummy.next;
			dummy.next = null;
		}
		return root;
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
