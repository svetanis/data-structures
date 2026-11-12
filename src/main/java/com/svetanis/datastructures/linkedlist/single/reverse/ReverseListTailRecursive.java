package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Input: the head of a singly linked list. LeetCode: 0 <= n <= 5000 nodes, values
// -5000..5000. Returns: the head of the reversed list (null for an empty list).
//
// The one idea: one node holds a value and an arrow to the next node. This is ReverseList's
// loop with prev and curr carried as arguments: each call turns one arrow and passes the next
// pair on. The recursive call is the last thing a call does, but Java does not reuse the
// frame, so every node still costs one frame on the stack.
//
// Time: O(n) -- one call per node.
// Space: O(n) -- one stack frame per node. Assumption: the largest legal list, n = 5000, runs
// on a default JVM stack; the stack overflows
// somewhere between 20,000 and 25,000 nodes.
//
// Siblings: ReverseList is the same four lines as a loop with O(1) space. ReverseListRecursive
// turns the arrows on the way back; ReverseListMoveToFront and ReverseListStack are the other two
// ways.

public final class ReverseListTailRecursive {

	public static ListNode reverse(ListNode head) {
		if (head == null) { // BASE: empty list
			return null;
		}
		return reverse(head, null); // START: prev is null, the new last node points at nothing
	}

	private static ListNode reverse(ListNode curr, ListNode prev) {
		if (curr.next == null) { // the last node: it becomes the head
			curr.next = prev; // FLIP: the last arrow
			return curr; // RETURN: the old last node, handed up unchanged
		}
		ListNode next = curr.next; // SAVE: the way forward first
		curr.next = prev; // FLIP: turn the arrow
		return reverse(next, curr); // MOVE: next becomes curr, curr becomes prev
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(8, 7, 6, 5, 4, 2, 2, 1));
		print(head); // 8 7 6 5 4 2 2 1
		print(reverse(head)); // 1 2 2 4 5 6 7 8
	}
}
