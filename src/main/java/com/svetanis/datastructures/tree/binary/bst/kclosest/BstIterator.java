package com.svetanis.datastructures.tree.binary.bst.kclosest;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// implement an iterator over a BST
// iterator initialized with the root node
// next() returns the next smallest num in BST

public final class BstIterator {
	// Time Complexity: O(1)
	// Space Complexity: O(h)

	private Deque<Node> dq;

	public BstIterator(Node root) {
		this.dq = new ArrayDeque<>();
		pushLeft(root);
	}

	public boolean hasNext() {
		return !dq.isEmpty();
	}

	public int next() {
		Node node = dq.pop();
		pushLeft(node.right);
		return node.data;
	}

	private void pushLeft(Node root) {
		if (isNotNull(root)) {
			dq.push(root);
			pushLeft(root.left);
		}
	}

	private void pushLeftIterative(Node root) {
		while (isNotNull(root)) {
			dq.push(root);
			root = root.left;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(10);
		root.left = newNode(1);
		root.right = newNode(11);
		root.left.right = newNode(6);
		root.right.right = newNode(12);
		BstIterator iter = new BstIterator(root);
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println();
	}
}