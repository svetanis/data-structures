package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.LinkedList;
import java.util.Queue;

// 116. Populating Next Right Pointers in Each Node
// 117. Populating Next Right Pointers in Each Node II
//
// Input: root -- the root of a binary tree (perfect in 116, any shape in 117), possibly null; each
// Node has a next pointer that starts out null. Connects every node to the node on its right on the
// same level; the last node of a level keeps next == null. Returns root. On LeetCode: 116 has
// 0 <= nodes <= 2^12 - 1 and -1000 <= Node.val <= 1000; 117 has 0 <= nodes <= 6000 and
// -100 <= Node.val <= 100.
//
// The one idea: take the tree one level at a time. Before a level is processed the queue holds
// exactly that level's nodes, left to right, and its size says how many. Walk those nodes keeping the
// previous one, and point prev.next at the current one. prev starts as null at each level, so the
// first node of a level has nothing pointing to it from the left and the last one is never given a
// next. The children go to the back of the queue and form the next level. Nothing depends on the
// tree being perfect.
//
// Time: O(n) -- every node is queued and taken out once.
// Space: O(n) -- the queue holds a whole level, up to about n / 2 nodes.
//
// Siblings: ConnectSameLevelNodesMemoryOptimized does the same linking in O(1) extra space by
// walking the next pointers of the level above in place of a queue. ConnectAllLevelOrderSiblings
// also links the last node of a level to the first of the next.

public final class ConnectSameLevelNodes {

	public static Node connect(Node root) {
		if (root == null) { // BASE: empty tree
			return null;
		}
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node prev = null; // PREV: reset, so a level's first node has no left neighbour
			int size = queue.size(); // LEVEL: how many nodes belong to this level
			for (int i = 0; i < size; i++) {
				Node curr = queue.poll(); // TAKE: next node of this level, left to right
				if (prev != null) {
					prev.next = curr; // LINK: the left neighbour points here
				}
				prev = curr;
				if (curr.left != null) { // QUEUE: children go behind the level, left first
					queue.offer(curr.left);
				}
				if (curr.right != null) {
					queue.offer(curr.right);
				}
			}
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
		root1.printLevelOrder(); // prints the levels: 12 / 7 1 / 9 10 5

		Node root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.left = newNode(6);
		root2.right.right = newNode(7);

		connect(root2);
		root2.printLevelOrder(); // prints the levels: 1 / 2 3 / 4 5 6 7
	}
}
