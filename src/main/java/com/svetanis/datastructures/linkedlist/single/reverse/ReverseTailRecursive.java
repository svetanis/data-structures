package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Given the head of a singly linked list, reverse it and return the new head.
//
// ReverseIterative's loop with prev and curr carried as arguments: each call turns one
// arrow and passes the next pair on. The recursive call is the last thing a call does, but
// Java does not reuse the frame, so every node still costs one frame on the stack.

public final class ReverseTailRecursive {
	// Time Complexity: O(n), one call per node
	// Space Complexity: O(n), one stack frame per node

	public static ListNode reverse(ListNode head) {
		if (head == null) {
			return null;
		}
		return reverse(head, null);
	}

	private static ListNode reverse(ListNode curr, ListNode prev) {
		if (curr.next == null) { // the last node: it becomes the head
			curr.next = prev;
			return curr;
		}
		ListNode next = curr.next; // save the way forward first
		curr.next = prev; // turn the arrow
		return reverse(next, curr);
	}

	public static void main(String[] args) {
		ListNode head = fromList(newArrayList(8, 7, 6, 5, 4, 2, 2, 1));
		print(head); // 8 7 6 5 4 2 2 1
		print(reverse(head)); // 1 2 2 4 5 6 7 8
	}
}
