package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

// 116. Populating Next Right Pointers in Each Node
// 117. Populating Next Right Pointers in Each Node II
//
// Input: root -- the root of a binary tree (perfect in 116, any shape in 117), possibly null; each
// Node has a next pointer that starts out null. Connects every node to the node on its right on the
// same level; the last node of a level keeps next == null. Returns root. On LeetCode: 116 has
// 0 <= nodes <= 2^12 - 1; 117 has 0 <= nodes <= 6000.
//
// The one idea: a level that is already linked can be walked through its next pointers, and while
// it is walked the level below gets linked. Children can be missing, so a child is linked from the
// last child found before it -- prev -- whichever parent that one came from. The first child found
// on a level has nothing before it: it is not linked, it is remembered as nextStart, where the next
// walk begins. Every child found then becomes prev.
//
// Time: O(n) -- every node is walked once as a parent and handled once as a child.
// Space: O(1) -- three pointers, whatever the size of the tree.
//
// Siblings: ConnectSameLevelNodesHandleChild is this method with the per-child step written once,
// as its own method. ConnectSameLevelNodesMemoryOptimized starts each level's chain at a spare
// node, so the first child is not a special case. ConnectSameLevelNodesPerfect is 116 only.
// ConnectSameLevelNodes keeps the level in a queue (O(n) space).

public final class ConnectSameLevelNodesFirstChild {

	public static Node connect(Node root) {
		Node cur = root; // CUR: walks a level that is already linked
		while (cur != null) { // one pass per level
			Node prev = null; // PREV: the last child found on the level below -- none yet
			Node nextStart = null; // NEXT START: the first child found on the level below
			while (cur != null) { // WALK: across this level
				if (cur.left != null) {
					if (prev == null) {
						nextStart = cur.left; // FIRST: nothing to link from; the next walk starts here
					} else {
						prev.next = cur.left; // LINK: from the last child found
					}
					prev = cur.left; // every child: now the last one found
				}
				if (cur.right != null) {
					if (prev == null) {
						nextStart = cur.right; // FIRST: nothing to link from; the next walk starts here
					} else {
						prev.next = cur.right; // LINK: from the last child found
					}
					prev = cur.right; // every child: now the last one found
				}
				cur = cur.next; // WALK: along this level's links
			}
			cur = nextStart; // DOWN: the next walk starts at the first child found
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(7);

		connect(root);
		root.printLevelOrder(); // prints the levels: 1 / 2 3 / 4 5 7
	}
}
