package com.svetanis.datastructures.linkedlist.single.flatten;

// 430. Flatten a Multilevel Doubly Linked List

public final class FlattenMultiLevelDoublyList {
	// Time Complexity: O(n)

	public static Node flatten(Node head) {
		if (head == null) {
			return null;
		}
		Node dummy = new Node();
		dummy.next = head;
		flatten(dummy, head);
		dummy.next.prev = null;
		return dummy.next;
	}

	private static Node flatten(Node prev, Node curr) {
		if (curr == null) {
			return prev;
		}
		// connect the curr node to prev
		curr.prev = prev;
		prev.next = curr;

		Node temp = curr.next;
		Node tail = flatten(curr, curr.child);
		curr.child = null;
		return flatten(tail, temp);
	}

	public static void main(String[] args) {}

	private static class Node {
		private int val;
		private Node prev;
		private Node next;
		private Node child;

	}

}
