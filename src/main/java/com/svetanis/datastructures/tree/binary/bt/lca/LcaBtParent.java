package com.svetanis.datastructures.tree.binary.bt.lca;

// 1650. Lowest Common Ancestor of a Binary Tree III
// given two nodes in a BT, find LCA
// assume each Node has a parent pointer
// the tree has n nodes and height h

public final class LcaBtParent {
	// Time Complexity: O(h)
	// Space Complexity: O(1)

	public static Node lca(Node root, Node u, Node v) {
		Node node1 = u;
		Node node2 = v;
		while (node1 != node2) {
			node1 = node1.parent == null ? v : node1.parent;
			node2 = node2.parent == null ? u : node2.parent;
		}
		return node1;
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
