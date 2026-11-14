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
// The one idea: the same walk as ConnectSameLevelNodesFirstChild -- walk a linked level through its
// next pointers, link the level below from prev, the last child found -- with the per-child step
// written once, as handleChild. That step changes two values, prev and nextStart, so they are fields
// of an instance made for one call: the walk then reads as "handle the left child, handle the right
// child, move on". This is the shape of LeetCode's own solution, with its leftmost named nextStart.
//
// Time: O(n) -- every node is walked once as a parent and handled once as a child.
// Space: O(1) -- three pointers and one instance, whatever the size of the tree.
//
// Siblings: ConnectSameLevelNodesFirstChild writes the per-child step inline, twice.
// ConnectSameLevelNodesMemoryOptimized starts each level's chain at a spare node, so the first child
// is not a special case. ConnectSameLevelNodesPerfect is 116 only. ConnectSameLevelNodes keeps the
// level in a queue (O(n) space).

public final class ConnectSameLevelNodesHandleChild {

	private Node prev; // PREV: the last child found on the level below
	private Node nextStart; // NEXT START: the first child found on the level below

	public static Node connect(Node root) {
		return new ConnectSameLevelNodesHandleChild().link(root); // one instance per call: nothing shared
	}

	private Node link(Node root) {
		Node cur = root; // CUR: walks a level that is already linked
		while (cur != null) { // one pass per level
			prev = null; // no child found yet on the level below
			nextStart = null;
			while (cur != null) { // WALK: across this level
				handleChild(cur.left);
				handleChild(cur.right);
				cur = cur.next; // WALK: along this level's links
			}
			cur = nextStart; // DOWN: the next walk starts at the first child found
		}
		return root;
	}

	private void handleChild(Node child) {
		if (child == null) { // a missing child: nothing to link
			return;
		}
		if (prev == null) {
			nextStart = child; // FIRST: nothing to link from; the next walk starts here
		} else {
			prev.next = child; // LINK: from the last child found
		}
		prev = child; // every child: now the last one found
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
