package com.svetanis.datastructures.linkedlist.single.flatten;

import java.util.ArrayDeque;
import java.util.Deque;

// 430. Flatten a Multilevel Doubly Linked List

public final class FlattenMultiLevelDoublyListStack {
	// Time Complexity: O(n)

	public static Node flatten(Node head) {
		if (head == null) {
			return null;
		}
		Deque<Node> dq = new ArrayDeque<>();
		Node curr = head;
		while (curr != null) {
			if (curr.child != null) {
				if (curr.next != null) {
					dq.push(curr.next);
				}
				curr.next = curr.child;
				curr.child.prev = curr;
				curr.child = null;
			}

			if (curr.next == null && !dq.isEmpty()) {
				curr.next = dq.pop();
				curr.next.prev = curr;
			}
			curr = curr.next;
		}
		return head;
	}

	public static void main(String[] args) {}

	private static class Node {
		private int val;
		private Node prev;
		private Node next;
		private Node child;

	}

}
