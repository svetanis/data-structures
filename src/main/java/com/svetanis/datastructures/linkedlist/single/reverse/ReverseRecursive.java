package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Given the head of a singly linked list, reverse it and return the new head.
//
// Reverse everything after head, then put head on the end. The call is handed the list that
// starts at head.next and returns it reversed. It never touches head, so head.next still
// points at the node that was second, which is now the last node of the reversed part, and
// head is attached after it. The arrows are turned on the way back, last pair first, where
// ReverseIterative and ReverseTailRecursive turn them on the way forward.

public final class ReverseRecursive {
	// Time Complexity: O(n), one call per node
	// Space Complexity: O(n), one stack frame per node

	public static ListNode reverse(ListNode head) {
		if (head == null || head.next == null) { // STOP: empty or one node is already reversed
			return head;
		}
		ListNode node = head.next; // SAVE: the second node, the last one once the rest is reversed
		ListNode newHead = reverse(node); // MOVE: the call reverses node onward, returns its new head
		// KEEP: no line, head stays named in this call while the deeper call runs
		node.next = head; // FLIP: the node after head points back at it
		head.next = null; // END: head is the last node now, which also breaks the two-node loop
		return newHead; // RETURN: handed up unchanged from the deepest call
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
