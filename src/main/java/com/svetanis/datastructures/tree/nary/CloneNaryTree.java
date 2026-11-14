package com.svetanis.datastructures.tree.nary;

import java.util.ArrayList;
import java.util.List;

// 1490. Clone N-ary Tree
//
// Input: root, the root of an N-ary tree, or null for an empty tree. Each node holds an int val
// and a list of child nodes. Returns a deep copy: new nodes holding the same values in the same
// shape, sharing no node with the original. On LeetCode: 0 <= number of nodes <= 10^4, and the
// depth of the tree is at most 1000.
//
// The one idea: one node holds a value and the list of its children, and each child is the root
// of a smaller tree of the same kind. So the copy of a node is a new node with the same value
// whose children are the copies of the original's children. Each call answers one question --
// "what is the copy of this subtree?" -- and gets the answer for every child by asking the
// same question of the child. A null node has nothing to copy, which ends the recursion.
//
// Time: O(n) -- every node is visited once and copied once.
// Space: O(n) -- the copy itself; the recursion stack adds O(h) for a tree of height h <= 1000.
//
// Siblings: clone and cloneTree solve the same problem. clone copies the children first and
// hands the finished list to the new node's constructor; cloneTree creates the new node first
// and adds each copied child to its list. The nested Node starts every list empty, as
// LeetCode's own Node does.

public final class CloneNaryTree {

	public Node clone(Node root) {
		if (root == null) { // BASE: nothing to copy
			return null;
		}
		List<Node> cloned = new ArrayList<>();
		for (Node child : root.children) { // WALK: every child
			cloned.add(clone(child)); // COPY: the child's whole subtree
		}
		return new Node(root.val, cloned); // RETURN: new node over the copied children
	}

	public Node cloneTree(Node root) {
		return dfs(root);
	}

	private Node dfs(Node node) {
		if (node == null) { // BASE: nothing to copy
			return null;
		}
		Node clone = new Node(node.val); // NEW: same value, empty child list
		for (Node child : node.children) { // WALK: every child
			clone.children.add(dfs(child)); // COPY: hang the child's copy on the new node
		}
		return clone; // RETURN
	}

	public static void main(String[] args) {
		// LeetCode example 1: [1,null,3,2,4,null,5,6]
		//        1
		//      / | \
		//     3  2  4
		//    / \
		//   5   6
		Node three = new Node(3, List.of(new Node(5), new Node(6)));
		Node root = new Node(1, List.of(three, new Node(2), new Node(4)));
		CloneNaryTree tree = new CloneNaryTree();
		Node copy = tree.clone(root);
		System.out.println(values(copy)); // [1, 3, 5, 6, 2, 4]
		System.out.println(copy != root); // true
		Node copy2 = tree.cloneTree(root);
		System.out.println(values(copy2)); // [1, 3, 5, 6, 2, 4]
		System.out.println(copy2.children.get(0) != three); // true
		System.out.println(tree.cloneTree(null)); // null
		System.out.println(values(tree.cloneTree(new Node(7)))); // [7]
	}

	// the values in preorder: a node, then each child's subtree in turn
	private static List<Integer> values(Node node) {
		List<Integer> list = new ArrayList<>();
		collect(node, list);
		return list;
	}

	private static void collect(Node node, List<Integer> list) {
		list.add(node.val);
		for (Node child : node.children) {
			collect(child, list);
		}
	}

	public static class Node {
		private int val;
		private List<Node> children = new ArrayList<>();

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
