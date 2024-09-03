package com.svetanis.datastructures.tree.binary.bt.lca;

// given two nodes in a BT, find LCA
// assume each Node has a parent pointer
// the tree has n nodes and height h

public final class LcaBtParentLevel {
	// Time Complexity: O(h)
	// Space Complexity: O(1)

	// LCA Level Based
	public static Node lca(Node root, Node u, Node v) {

		while (level(root, u.data) > level(root, v.data)) {
			u = u.parent;
		}

		while (level(root, v.data) > level(root, u.data)) {
			v = v.parent;
		}

		while (u.data != v.data) {
			u = u.parent;
			v = v.parent;
		}
		return u;
	}

	private static int level(Node root, int data) {
		return level(root, data, 1);
	}

	private static int level(Node node, int data, int level) {

		if (node == null) {
			return 0;
		}

		if (node.data == data) {
			return level;
		}

		int left = level(node.left, data, level + 1);
		if (left != 0) {
			return left;
		}
		return level(node.right, data, level + 1);
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
