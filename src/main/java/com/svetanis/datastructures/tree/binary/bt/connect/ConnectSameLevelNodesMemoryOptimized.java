package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

// 116. Populating Next Right Pointers in Each Node
// 117. Populating Next Right Pointers in Each Node II
//
// Input: root -- the root of a binary tree (perfect in 116, any shape in 117), possibly null; each
// Node has a next pointer that starts out null. Connects every node to the node on its right on the
// same level; the last node of a level keeps next == null. Returns root. On LeetCode: 116 has
// 0 <= nodes <= 2^12 - 1 and -1000 <= Node.val <= 1000; 117 has 0 <= nodes <= 6000 and
// -100 <= Node.val <= 100.
//
// The one idea: once a level is linked through its next pointers, that chain is a ready-made queue
// of the level, so no queue is needed. Walk the chain from its first node; for each node, append its
// left and right children (when present) to a new chain that is being built for the level below.
// The new chain starts at a dummy node, so the first child needs no special case. When the walk
// reaches the end, dummy.next is the first node of the level below; it becomes the next chain to
// walk. dummy.next is cleared before the next level so the old head is not reused.
//
// Time: O(n) -- every node is walked once as a parent and linked once as a child.
// Space: O(1) -- one dummy node and two pointers, whatever the size of the tree.
//
// Siblings: ConnectSameLevelNodes keeps the level in a queue (O(n) space) and links with a prev
// pointer. ConnectSameLevelNodesPerfect is 116 only: on a perfect tree each node links its own two
// children and its right child to its neighbour's left child, with no dummy.
// ConnectSameLevelNodesFirstChild and ConnectSameLevelNodesHandleChild do this walk with no dummy:
// the first child of a level is a case of its own, inline or in a method.

public final class ConnectSameLevelNodesMemoryOptimized {

	public static Node connect(Node root) {
		Node dummy = new Node(0); // DUMMY: head of the chain being built for the level below
		Node curr = root; // CURR: walks the level that is already linked
		while (curr != null) {
			Node tail = dummy; // TAIL: last node of the new chain so far
			while (curr != null) { // WALK: this level, through its next pointers
				if (curr.left != null) {
					tail.next = curr.left; // APPEND: left child to the new chain
					tail = tail.next;
				}
				if (curr.right != null) {
					tail.next = curr.right; // APPEND: right child behind it
					tail = tail.next;
				}
				curr = curr.next;
			}
			curr = dummy.next; // DESCEND: first node of the level below
			dummy.next = null; // RESET: the dummy starts the next chain empty
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
