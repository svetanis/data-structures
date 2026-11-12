package com.svetanis.datastructures.linkedlist.single.flatten;

import java.util.ArrayDeque;
import java.util.Deque;

// 430. Flatten a Multilevel Doubly Linked List
//
// Input: the head of a doubly linked list whose nodes have next, prev, and child; a child may
// point to the head of another doubly linked list, which may have children of its own. On
// LeetCode: at most 1000 nodes, 1 <= val <= 10^5. Returns the head of one doubly linked list
// with every node: each child list comes right after its parent node, before the parent's next.
// Every child pointer ends up null.
//
// The one idea: a node with a child must be followed by that whole child list, and only then by
// the rest of its own row. curr walks the list. When curr has a child, the rest of curr's row
// (curr.next) is put aside on a stack and the child list takes its place right after curr. When
// curr has no next and the stack is not empty, the part put aside most recently is joined on.
// Most recent first is what places a deeper list before the rest of the row above it.
//
// Time: O(n) -- curr passes each node once.
// Space: O(d) -- d is how deep the child lists nest: one put-aside node per level.
//
// Siblings: FlattenMultiLevelDoublyList does the same with recursion and a fake head node.

public final class FlattenMultiLevelDoublyListStack {

	public static Node flatten(Node head) {
		if (head == null) {
			return null;
		}
		Deque<Node> dq = new ArrayDeque<>(); // STACK: the rows put aside, latest on top
		Node curr = head;
		while (curr != null) { // WALK
			if (curr.child != null) {
				if (curr.next != null) {
					dq.push(curr.next); // PUT ASIDE the rest of this level
				}
				curr.next = curr.child; // SPLICE the child list in right after curr
				curr.child.prev = curr; // both directions, as a doubly linked list needs
				curr.child = null; // CLEAR: LC 430 wants every child pointer null
			}

			if (curr.next == null && !dq.isEmpty()) { // end of a child list: join the latest part put aside
				curr.next = dq.pop(); // JOIN the row put aside latest
				curr.next.prev = curr;
			}
			curr = curr.next;
		}
		return head;
	}

	public static void main(String[] args) {
		// LeetCode example 1: 1 2 3 4 5 6, with 3 owning the row 7 8 9 10, and 8 owning 11 12
		Node head = row(1, 2, 3, 4, 5, 6);
		head.next.next.child = row(7, 8, 9, 10);
		head.next.next.child.next.child = row(11, 12);
		print(flatten(head)); // 1 2 3 7 8 11 12 9 10 4 5 6

		// LeetCode example 2: 1 2, with 1 owning the row 3
		Node head2 = row(1, 2);
		head2.child = row(3);
		print(flatten(head2)); // 1 3 2

		print(flatten(null)); // (empty line)
	}

	private static final String VALUE = "%d ";

	// a row of nodes joined by next and prev
	private static Node row(int... values) {
		Node head = null;
		Node tail = null;
		for (int value : values) {
			Node node = new Node();
			node.val = value;
			if (head == null) {
				head = node;
			} else {
				tail.next = node;
				node.prev = tail;
			}
			tail = node;
		}
		return head;
	}

	// the values along next
	private static void print(Node head) {
		for (Node curr = head; curr != null; curr = curr.next) {
			System.out.print(VALUE.formatted(curr.val));
		}
		System.out.println();
	}

	private static class Node {
		private int val;
		private Node prev;
		private Node next;
		private Node child;

	}

}
