package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Queue;
import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a perfect binary tree
// print the level order of nodes
// in following specific manner
// print nodes in level order but
// nodes should be from left and 
// right side alternatively

public final class LOTPerfectBtBottomUp {

	public static ImmutableList<Node> traverse(Node root) {
		// Time Complexity: O(n)

		Node node = root;
		if (isNull(node)) {
			return newList();
		}
		Stack<Node> stack = new Stack<Node>();
		stack.push(node);

		// Since it is Perfect BT, right not checked
		if (isNotNull(node.left)) {
			stack.push(node.right);
			stack.push(node.left);
		}

		// if there are no nodes at next level, return
		if (isNotNull(node.left.left)) {
			return newList();
		}

		// create a queue and enqueue left and right
		Queue<Node> queue = newLinkedList();
		queue.offer(node.left);
		queue.offer(node.right);

		// process two nodes at a time,
		// store two front items of queue
		while (!queue.isEmpty()) {
			Node first = queue.poll();
			Node second = queue.poll();

			// push first and second node's
			// children in reverse order
			stack.push(second.left);
			stack.push(first.right);
			stack.push(second.right);
			stack.push(first.left);

			// if first and second have grandchildren,
			// enqueue them in specific order
			if (first.left.left != null) {
				queue.offer(first.right);
				queue.offer(second.left);
				queue.offer(first.left);
				queue.offer(second.right);
			}
		}
		return newList(stack);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.left = new Node(6);
		root.right.right = new Node(7);
		root.left.left.left = new Node(8);
		root.left.left.right = new Node(9);
		root.left.right.left = new Node(10);
		root.left.right.right = new Node(11);
		root.right.left.left = new Node(12);
		root.right.left.right = new Node(13);
		root.right.right.left = new Node(14);
		root.right.right.right = new Node(15);
		root.left.left.left.left = new Node(16);
		root.left.left.left.right = new Node(17);
		root.left.left.right.left = new Node(18);
		root.left.left.right.right = new Node(19);
		root.left.right.left.left = new Node(20);
		root.left.right.left.right = new Node(21);
		root.left.right.right.left = new Node(22);
		root.left.right.right.right = new Node(23);
		root.right.left.left.left = new Node(24);
		root.right.left.left.right = new Node(25);
		root.right.left.right.left = new Node(26);
		root.right.left.right.right = new Node(27);
		root.right.right.left.left = new Node(28);
		root.right.right.left.right = new Node(29);
		root.right.right.right.left = new Node(30);
		root.right.right.right.right = new Node(31);

		print(traverse(root));
	}
}