package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Given the head of a singly linked list, reverse it and return the new head.
//
// Take the nodes off the old list one at a time and insert each at the front of a new list
// that starts behind a fake node. The last node taken ends up first. dummy.next plays the
// part that prev plays in ReverseIterative.

public final class ReverseDummy {
	// Time Complexity: O(n), one pass
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head) {
		ListNode curr = head;
		ListNode dummy = new ListNode(); // a fake node in front of the reversed list
		while (curr != null) {
			ListNode next = curr.next; // save the way forward first
			curr.next = dummy.next; // insert curr at the front of the reversed list
			dummy.next = curr;
			curr = next;
		}
		return dummy.next;
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
