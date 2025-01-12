package com.svetanis.datastructures.tree.binary.bt;

import java.util.HashMap;
import java.util.Map;

// 1485. Clone BT With Random Pointer
// com.svetanis.datastructures.tree.binary.bt.CloneBTRandomNode

public final class CloneBTRandomNode1485 {

	public static Node clone(Node root) {
		if (root == null) {
			return null;
		}
		Map<Node, Node> map = new HashMap<>();
		return clone(root, map);
	}

	private static Node clone(Node node, Map<Node, Node> map) {
		if (node == null) {
			return null;
		}
		if (map.containsKey(node)) {
			return map.get(node);
		}
		Node copy = new Node(node.data);
		map.put(node, copy);
		copy.left = clone(node.left, map);
		copy.right = clone(node.right, map);
		copy.random = clone(node.random, map);
		return copy;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.random = root.left.right;
		root.left.left.random = root;
		root.left.right.random = root.right;

		printInOrder(root);
		System.out.println();

		Node clone = clone(root);
		printInOrder(clone);
		System.out.println();
	}

	private static void printInOrder(Node node) {
		if (node == null) {
			return;
		}
		printInOrder(node.left);
		System.out.print("[" + node.data + " ");
		if (node.random == null) {
			System.out.print("NULL], ");
		} else {
			System.out.print(node.random.data + "], ");
		}
		printInOrder(node.right);
	}

	private static class Node {
		private int data;
		private Node left;
		private Node right;
		private Node random;

		Node(int data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.random = null;
		}
	}
}
