package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.LinkedList;
import java.util.Queue;

// Connect All Level Order Siblings (not a LeetCode problem)
//
// Input: root -- the root of a binary tree, assumed not null; each Node has a next pointer that
// starts out null. Connects every node to the node that follows it in level order, so the whole
// tree becomes one chain. Unlike LC 116 and 117, the last node of a level points to the first node
// of the next level, and only the very last node of the tree keeps next == null.
//
// The one idea: a queue hands the nodes out in level order, so the node taken out just before this
// one is exactly its level order predecessor. Keep that previous node, and each time a node is
// taken out set prev.next to it.
//
// Time: O(n) -- every node is queued and taken out once.
// Space: O(n) -- the queue holds a whole level, up to about n / 2 nodes.
//
// Siblings: ConnectSameLevelNodes and ConnectSameLevelNodesMemoryOptimized (LC 116 and 117) reset
// prev at the start of each level, so a level's last node keeps next == null.

public final class ConnectAllLevelOrderSiblings {

	public static void connect(Node root) {
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		Node prev = null; // PREV: the node taken out just before this one
		while (!queue.isEmpty()) {
			Node curr = queue.poll(); // TAKE: the next node in level order
			if (prev != null) {
				prev.next = curr; // LINK: its predecessor now points here
			}
			prev = curr;

			if (curr.left != null) { // QUEUE: the children, left first
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
		root1.printTree(); // prints 1 2 3 4 5 6 7

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(9);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);

		connect(root2);
		root2.printTree(); // prints 12 7 1 9 10 5
	}
}
