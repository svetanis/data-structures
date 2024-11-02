package com.svetanis.datastructures.tree.binary.bt.lca;

import java.util.ArrayDeque;
import java.util.Deque;

// 1650. Lowest Common Ancestor of a Binary Tree III
// given two nodes in a BT, find LCA
// assume each Node has a parent pointer
// the tree has n nodes and height h

public final class LcaBtParentStack {

	public static Node lca(Node root, Node u, Node v) {
		Deque<Node> s1 = stack(root, u);
		Deque<Node> s2 = stack(root, v);
		return lca(s1, s2);
	}

	private static Node lca(Deque<Node> s1, Deque<Node> s2) {
		Node lca = null;
		while (!s1.isEmpty() && !s2.isEmpty() && s1.peek() == s2.peek()) {
			lca = s1.peek();
			s1.pop();
			s2.pop();
		}
		return lca;
	}

	private static Deque<Node> stack(Node root, Node node) {
		Deque<Node> stack = new ArrayDeque<>();
		while (node != root) {
			stack.push(node);
			node = node.parent;
		}
		stack.push(root);
		return stack;
	}

	public static void main(String[] args) {
		Node root = new Node(3);
		Node node1 = new Node(5);
		Node node2 = new Node(1);
		Node node3 = new Node(6);
		Node node4 = new Node(2);
		Node node5 = new Node(7);
		Node node6 = new Node(4);
		Node node7 = new Node(0);
		Node node8 = new Node(8);

		root.left = node1;
		root.left.parent = root;
		root.right = node2;
		root.right.parent = root;
		root.parent = null;
		root.left.left = node3;
		root.left.left.parent = node1;
		root.left.right = node4;
		root.left.right.parent = node1;
		root.left.right.left = node5;
		root.left.right.left.parent = node4;
		root.left.right.right = node6;
		root.left.right.right.parent = node4;
		root.right.left = node7;
		root.right.left.parent = node2;
		root.right.right = node8;
		root.right.right.parent = node2;

		System.out.println(lca(root, root.left, root.right));
		System.out.println(lca(root, root.left, root.left.right.right));
	}

	private static final class Node {

		public int data;
		public Node left;
		public Node right;
		public Node parent;

		public Node(int data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
		}

		@Override
		public String toString() {
			return Integer.toString(data);
		}
	}

}
