package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

// 116. Populating Next Right Pointers in Each Node
//
// Input: root -- the root of a perfect binary tree (every parent has two children, every leaf on
// the same level), possibly null; each Node has a next pointer that starts out null. Connects every
// node to the node on its right on the same level; the last node of a level keeps next == null.
// Returns root. On LeetCode: 0 <= nodes <= 2^12 - 1, so at most 12 levels, and
// -1000 <= Node.val <= 1000.
//
// The one idea: once a level is linked through its next pointers, that chain can be walked like a
// list, and while it is walked the level below gets linked. Standing on a node, its left child links
// to its right child; its right child links to the left child of the node's next neighbour, unless
// the node is last on its level. The next walk starts one level down, at the left child of the
// level's first node -- kept in leftmost, which does not move during the walk.
//
// Time: O(n) -- every node is walked once as a parent.
// Space: O(1) -- two pointers, whatever the size of the tree.
//
// Known limit: needs a perfect tree. With a missing child, cur.left.next throws (LC 117's
// [1,2,3,4,5,null,7]); ConnectSameLevelNodesMemoryOptimized handles any shape.
//
// Siblings: ConnectSameLevelNodesMemoryOptimized builds each level below as a new chain from a
// dummy node, so it works on any tree (116 and 117). ConnectSameLevelNodes keeps the level in a
// queue (O(n) space).

public final class ConnectSameLevelNodesPerfect {

	public static Node connect(Node root) {
		Node leftmost = root; // LEFTMOST: the first node of the level being walked
		while (leftmost != null && leftmost.left != null) { // a level below to link
			Node cur = leftmost; // WALK: starts at the level's first node
			while (cur != null) {
				cur.left.next = cur.right; // LINK: my two children
				if (cur.next != null) { // the last node of a level has no neighbour
					cur.right.next = cur.next.left; // LINK: my right child to my neighbour's left child
				}
				cur = cur.next; // WALK: along this level's links
			}
			leftmost = leftmost.left; // DOWN: the next level's first node
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);

		connect(root);
		root.printLevelOrder(); // prints the levels: 1 / 2 3 / 4 5 6 7
		System.out.println(connect(null)); // null
	}
}
