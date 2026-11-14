package com.svetanis.datastructures.tree.nary;

import java.util.List;

// 559. Maximum Depth of N-ary Tree
//
// Input: root, the root of an N-ary tree, or null. Each node holds an int val and a list of
// children. Returns the number of nodes on the longest path from the root down to a leaf; an
// empty tree has depth 0 and a single node has depth 1. On LeetCode: 0 <= number of nodes <=
// 10^4, and the depth of the tree is at most 1000.
//
// The one idea: a node's depth is 1 (the node itself) plus the greatest depth among its
// children, and each child is the root of a smaller tree of the same kind, so each call answers
// "how deep is this subtree?" for one child. A null node is an empty tree, depth 0. A node with
// no children is a leaf: its greatest child depth is 0, so its depth is 1. A node whose child
// list is null is treated like one whose list is empty, because this file's Node leaves the
// list null when the one-argument constructor is used.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack holds one frame per level, h <= 1000.

public final class MaxDepthNaryTree {

	public static int maxDepth(Node root) {
		if (root == null) { // BASE: an empty tree has no depth
			return 0;
		}
		int max = 0; // MAX: the deepest child so far; 0 when there is none
		if (root.children != null) { // a null list is the same as an empty one
			for (Node child : root.children) { // WALK: every child
				max = Math.max(max, maxDepth(child));
			}
		}
		return 1 + max; // RETURN: this node plus the deepest child
	}

	public static void main(String[] args) {
		// LeetCode example 1: [1,null,3,2,4,null,5,6]
		// 1
		// / | \
		// 3 2 4
		// / \
		// 5 6
		Node five = new Node(5);
		Node six = new Node(6);
		Node three = new Node(3, List.of(five, six));
		Node root = new Node(1, List.of(three, new Node(2), new Node(4)));
		System.out.println(maxDepth(root)); // 3

		System.out.println(maxDepth(new Node(1))); // 1 -- children is null
		System.out.println(maxDepth(new Node(1, List.of()))); // 1 -- children is empty
		System.out.println(maxDepth(null)); // 0
	}

	public static class Node {
		private int val;
		private List<Node> children;

		public Node() {}

		public Node(int val) {
			this.val = val;
		}

		public Node(int val, List<Node> children) {
			this.val = val;
			this.children = children;
		}
	}
}
