package com.svetanis.datastructures.tree.binary.bt.special;

import java.util.Deque;
import java.util.LinkedList;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 958. Check Completeness of a Binary Tree

public final class BinaryTreeCompletenessBfs {
	// Time complexity: O(n)

	public static boolean isComplete(Node root) {
		Deque<Node> dq = new LinkedList<>();
		dq.offer(root);
		while (dq.peek() != null) {
			Node node = dq.poll();
			dq.offer(node.left);
			dq.offer(node.right);
		}
		while(!dq.isEmpty() && dq.peek() == null) {
			dq.poll();
		}
		return dq.isEmpty();
	}

	public static boolean isCompleteLastLevel(Node root) {
		Deque<Node> dq = new LinkedList<>();
		dq.offer(root);
		boolean lastLevel = false;
		while (!dq.isEmpty()) {
			Node node = dq.poll();
			if(node == null) {
				lastLevel = true;
				continue;
			} else if(lastLevel) {
				return false;
			}
			dq.offer(node.left);
			dq.offer(node.right);
		}
		return true;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.left = new Node(6);
		System.out.println(isComplete(root)); // true

		Node root1 = new Node(1);
		root1.left = new Node(2);
		root1.right = new Node(3);
		root1.left.left = new Node(4);
		root1.left.right = new Node(5);
		root1.right.right = new Node(7);
		System.out.println(isComplete(root1)); // false
	}
}
