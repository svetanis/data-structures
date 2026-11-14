package com.svetanis.datastructures.tree.binary.bt;

import java.util.HashMap;
import java.util.Map;

// 1485. Clone Binary Tree With Random Pointer
//
// Input: root of a binary tree in which every node has, besides left and right, a random pointer
// that is null or points to any node of the same tree. Returns a deep copy: new nodes with the
// same values, whose left, right and random pointers point only at new nodes and form the same
// shape. On LeetCode: 0 <= number of nodes <= 1000, 1 <= node value <= 10^6.
//
// The one idea of clone: one node holds a value and three pointers, and a pointer of any kind
// leads to a node that needs exactly one copy. So clone(node) returns THE copy of that node: it
// makes the copy the first time the walk meets the node, files it in a map (original -> copy), and
// returns the filed copy every later time. The copy is filed BEFORE its pointers are followed, so
// a random pointer that leads back to a node still being built finds its copy in the map instead
// of looping. One walk follows left, right and random alike.
//
// The one idea of copy: the copy of a pointer cannot be made until the copy of the node it points
// at exists, and a random pointer may point anywhere (up, to a node not built yet). So build in
// two passes. Pass 1 copies the tree (value, left, right) and records original -> copy in a map.
// Pass 2 walks the original and the copy together and sets copy.random = map.get(original.random);
// every node has a copy by then, so every lookup succeeds (a null random is not in the map and
// gives null).
//
// Time: O(n) for both -- clone copies each node once and the other visits end at the map lookup;
// copy makes two walks and one map lookup per node.
// Space: O(n) for both -- the map holds one entry per node; the recursion is as deep as the tree
// (clone can go as deep as n, because it also follows random pointers).
//
// Siblings: none; this class holds both ways to clone -- one pass (clone) and two passes (copy).

public final class CloneBTRandomNode1485 {

	public static Node clone(Node root) {
		if (root == null) {
			return null;
		}
		Map<Node, Node> map = new HashMap<>(); // MAP: original node -> its copy
		return clone(root, map);
	}

	private static Node clone(Node node, Map<Node, Node> map) {
		if (node == null) { // BASE: a null pointer copies to null
			return null;
		}
		if (map.containsKey(node)) { // REUSE: this node already has its one copy
			return map.get(node);
		}
		Node copy = new Node(node.data);
		map.put(node, copy); // RECORD: before following pointers, so a cycle finds it
		copy.left = clone(node.left, map); // WALK: left, right and random alike
		copy.right = clone(node.right, map);
		copy.random = clone(node.random, map);
		return copy;
	}

	public static Node copy(Node root) {
		if (root == null) {
			return null;
		}
		Map<Node, Node> map = new HashMap<>(); // MAP: original node -> its copy
		Node copy = copy(root, map);
		copyRandom(root, copy, map);
		return copy;
	}

	private static Node copy(Node root, Map<Node, Node> map) {
		if (root == null) {
			return null;
		}
		Node copy = new Node(root.data);
		map.put(root, copy); // RECORD: so pass 2 can find the copy of any node
		copy.left = copy(root.left, map); // WALK: copy the children
		copy.right = copy(root.right, map);
		return copy;
	}

	private static void copyRandom(Node node, Node copy, Map<Node, Node> map) {
		if (copy == null) {
			return;
		}
		copy.random = map.get(node.random); // LINK: the copy of the target; null stays null
		copyRandom(node.left, copy.left, map); // WALK: both trees together
		copyRandom(node.right, copy.right, map);
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

		printInOrder(root); // [4 1], [2 NULL], [5 3], [1 5], [3 NULL],
		System.out.println();

		Node clone = clone(root);
		printInOrder(clone); // [4 1], [2 NULL], [5 3], [1 5], [3 NULL],
		System.out.println();

		Node copy = copy(root);
		printInOrder(copy); // [4 1], [2 NULL], [5 3], [1 5], [3 NULL],
	}

	// prints each node as [value random-value] in left, node, right order
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
