package com.svetanis.datastructures.tree.nary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// 590. N-ary Tree Postorder Traversal
//
// Input: root, the root of an N-ary tree, or null. Each node holds an int val and a list of
// children. Returns the values in postorder: every child's subtree, in order from the first
// child to the last, and then the node itself. On LeetCode: 0 <= number of nodes <= 10^4,
// 0 <= Node.val <= 10^4, and the height of the tree is at most 1000.
//
// The one idea: postorder is the mirror image of preorder, read backwards. Walk the tree
// taking the node first and then its children from the LAST child to the FIRST: that gives the
// node, then the last child's subtree, ..., then the first child's subtree. Turn that order
// around and it reads the first child's subtree, ..., the last child's subtree, then the node --
// which is postorder. An explicit stack does the walk: one stack entry is a node whose value has
// not been taken yet; pushing the children first to last makes the last child pop first.
//
// Two methods, one walk: postOrder puts each value at the FRONT of a LinkedList as it pops;
// postOrderReverse appends each value and reverses the list once at the end.
//
// Time: O(n) -- every node is pushed once and popped once; each addFirst is O(1), and the one
// reverse touches n values.
// Space: O(n) -- the result, plus a stack of up to n - 1 nodes (a root with n - 1 children).
//
// Siblings: PreOrderTraversal (589) is the same stack walk with the children pushed last to
// first and the values kept in the order they pop.

public final class PostOrderTraversal {

	public static List<Integer> postOrder(Node root) {
		if (root == null) { // BASE: no nodes, no values
			return new ArrayList<>();
		}
		Deque<Node> dq = new ArrayDeque<>(); // STACK: nodes whose value is not taken yet
		LinkedList<Integer> list = new LinkedList<>();
		dq.offerLast(root);
		while (!dq.isEmpty()) {
			Node curr = dq.pollLast(); // POP: the node on top (the end of the deque)
			list.addFirst(curr.val); // TAKE: its value, at the FRONT, so the later pops end up first
			for (Node child : curr.children) {
				dq.offerLast(child); // PUSH: first to last, so the last child pops first
			}
		}
		return list;
	}

	public static List<Integer> postOrderReverse(Node root) {
		if (root == null) { // BASE: no nodes, no values
			return new ArrayList<>();
		}
		Deque<Node> dq = new ArrayDeque<>(); // STACK: nodes whose value is not taken yet
		List<Integer> list = new ArrayList<>();
		dq.push(root);
		while (!dq.isEmpty()) {
			Node curr = dq.pop(); // POP: the node on top
			list.add(curr.val); // TAKE: its value, before any child's
			List<Node> children = curr.children;
			for (int i = 0; i < children.size(); i++) {
				dq.push(children.get(i)); // PUSH: first to last, so the last child pops first
			}
		}
		Collections.reverse(list); // REVERSE: node, last..first child  ->  first..last child, node
		return list;
	}

	public static void main(String[] args) {
		// LeetCode example 1: [1,null,3,2,4,null,5,6]
		Node root = node(1, node(3, node(5), node(6)), node(2), node(4));
		System.out.println(postOrder(root)); // [5, 6, 3, 2, 4, 1]
		System.out.println(postOrderReverse(root)); // [5, 6, 3, 2, 4, 1]

		// LeetCode example 2:
		// [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
		Node root2 = node(1, node(2), node(3, node(6), node(7, node(11, node(14)))),
				node(4, node(8, node(12))), node(5, node(9, node(13)), node(10)));
		System.out.println(postOrder(root2)); // [2, 6, 14, 11, 7, 3, 12, 8, 4, 13, 9, 10, 5, 1]
		System.out.println(postOrderReverse(root2)); // [2, 6, 14, 11, 7, 3, 12, 8, 4, 13, 9, 10, 5, 1]

		System.out.println(postOrder(null)); // []
		System.out.println(postOrder(new Node(7))); // [7]
	}

	private static Node node(int val, Node... children) {
		return new Node(val, List.of(children));
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
