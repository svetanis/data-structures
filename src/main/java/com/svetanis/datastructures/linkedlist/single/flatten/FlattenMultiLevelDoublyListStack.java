package com.svetanis.datastructures.linkedlist.single.flatten;

import java.util.ArrayDeque;
import java.util.Deque;

// 430. Flatten a Multilevel Doubly Linked List
//
// Each node has next, prev, and child; child may point to the head of another doubly linked
// list, which may have children of its own. Return the head of one doubly linked list with
// every node: each child list comes right after its parent node, before the parent's next.
// Every child pointer ends up null.
//
// curr walks the list. When curr has a child, the rest of curr's level (curr.next) is put
// aside on a stack and the child list takes its place. When curr has no next and the stack
// is not empty, the part put aside most recently is joined on. Most recent first is what
// places a deeper list before the rest of the level above it.

public final class FlattenMultiLevelDoublyListStack {
	// Time Complexity: O(n), curr passes each node once
	// Space Complexity: O(d), d is how deep the child lists nest: one put-aside node per level

	public static Node flatten(Node head) {
		if (head == null) {
			return null;
		}
		Deque<Node> dq = new ArrayDeque<>();
		Node curr = head;
		while (curr != null) {
			if (curr.child != null) {
				if (curr.next != null) {
					dq.push(curr.next); // put aside the rest of this level
				}
				curr.next = curr.child;
				curr.child.prev = curr; // both directions, as a doubly linked list needs
				curr.child = null;
			}

			if (curr.next == null && !dq.isEmpty()) { // end of a child list: join the latest part put aside
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
