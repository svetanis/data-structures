package com.svetanis.datastructures.tree.nary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 589. N-ary Tree Preorder Traversal
//
// Input: root, the root of an N-ary tree, or null. Each node holds an int val and a list of
// children. Returns the values in preorder: the node itself, then every child's subtree, in
// order from the first child to the last. On LeetCode: 0 <= number of nodes <= 10^4,
// 0 <= Node.val <= 10^4, and the height of the tree is at most 1000.
//
// The one idea: an explicit stack holds the nodes whose value has not been taken yet. Pop a
// node, take its value, then push its children from the LAST to the FIRST, so the first child
// is on top and pops next. Its whole subtree is taken before the second child comes off the
// stack, because everything that subtree pushes lands on top of the second child.
//
// Time: O(n) -- every node is pushed once and popped once.
// Space: O(n) -- the result, plus a stack of up to n - 1 nodes (a root with n - 1 children).
//
// Siblings: PostOrderTraversal (590) is the same stack walk with the children pushed first to
// last and the order of the values turned around.

public final class PreOrderTraversal {

	public static List<Integer> preOrder(Node root) {
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
			for (int i = children.size() - 1; i >= 0; i--) {
				dq.push(children.get(i)); // PUSH: last to first, so the first child pops next
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// LeetCode example 1: [1,null,3,2,4,null,5,6]
		Node root = node(1, node(3, node(5), node(6)), node(2), node(4));
		System.out.println(preOrder(root)); // [1, 3, 5, 6, 2, 4]

		// LeetCode example 2:
		// [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
		Node root2 = node(1, node(2), node(3, node(6), node(7, node(11, node(14)))),
				node(4, node(8, node(12))), node(5, node(9, node(13)), node(10)));
		System.out.println(preOrder(root2)); // [1, 2, 3, 6, 7, 11, 14, 4, 8, 12, 5, 9, 13, 10]

		System.out.println(preOrder(null)); // []
		System.out.println(preOrder(new Node(7))); // [7]
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
