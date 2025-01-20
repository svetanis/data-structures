package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 173. Binary Search Tree Iterator

// Implement an iterator over a binary search tree (BST). 
// Your iterator will be initialized with the root node of a BST. 
// Calling next() will return the next smallest number in the BST. 
// Note: next() and hasNext() should run in average O(1) time 
// and uses O(h) memory, where h is the height of the tree.

public final class BtIterator {

	private Deque<Node> dq;

	public BtIterator(Node root) {
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

	private void pushLeft(Node node) {
		if (isNotNull(node)) {
			dq.push(node);
			pushLeft(node.left);
		}
	}

	private void pushLeftIterative(Node node) {
		while (isNotNull(node)) {
			dq.push(node);
			node = node.left;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(10);
		root.left = newNode(1);
		root.right = newNode(11);
		root.left.right = newNode(6);
		root.right.right = newNode(12);
		BtIterator iter = new BtIterator(root);
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}

		Node root2 = newNode(7);
		root2.left = newNode(3);
		root2.right = newNode(15);
		root2.right.left = newNode(9);
		root2.right.right = newNode(20);
		BtIterator iter2 = new BtIterator(root2);
		System.out.println(iter2.next()); // 3
		System.out.println(iter2.next()); // 7
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 9
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 15
		System.out.println(iter2.hasNext()); // true
		System.out.println(iter2.next()); // 20
		System.out.println(iter2.hasNext()); // false

	}
}
