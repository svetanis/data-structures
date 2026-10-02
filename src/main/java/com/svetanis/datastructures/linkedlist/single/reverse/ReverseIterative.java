package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Given the head of a singly linked list, reverse it and return the new head.
//
// Walk the list once and turn each arrow around. Before an arrow is turned, the node it
// pointed to is saved in next, because turning it loses the only way forward. Every node
// gets the same four lines, in this order: SAVE, FLIP, KEEP, MOVE.

public final class ReverseIterative {
	// Time Complexity: O(n), one pass
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head) {
		ListNode prev = null; // END: the reversed part so far; the old head's flip points it at null
		ListNode curr = head;
		while (curr != null) { // STOP: curr, not curr.next, so the last node gets its turn
			ListNode next = curr.next; // SAVE: the next line destroys the only way forward
			curr.next = prev; // FLIP: this node points at the one behind it
			prev = curr; // KEEP: a name on the node just finished
			curr = next; // MOVE: by the saved name, never by curr.next
		}
		return prev; // RETURN: curr is null, prev is the old last node
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(85, 15, 4, 20));
		print(reverse(head)); // 20 4 15 85

		ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1)); // 5 4 3 2 1

		ListNode head2 = fromList(asList(1, 2));
		print(reverse(head2)); // 2 1
	}
}
