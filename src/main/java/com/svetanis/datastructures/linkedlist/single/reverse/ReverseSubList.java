package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 92. Reverse Linked List II
//
// Given the head of a singly linked list and positions left <= right, counted from 1,
// reverse the nodes from position left to position right and return the head.
//
// Walk to the node before the stretch, reverse exactly right - left + 1 nodes, then connect
// both ends. Without a fake node in front, left = 1 has no node before the stretch, and the
// reconnection needs a second branch that moves the head. ReverseSubListDummy removes it.

public final class ReverseSubList {
	// Time Complexity: O(n), one pass, which stops at position right
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head, int left, int right) {
		if (head.next == null || left == right) {
			return head;
		}

		// 1. walk to the stretch
		ListNode prev = null;
		ListNode curr = head;
		for (int i = 0; curr != null && i < left - 1; i++) {
			prev = curr;
			curr = curr.next;
		}
		ListNode before = prev; // the node before the stretch, null when left = 1
		ListNode subListTail = curr; // the stretch's first node, its last after the reversal

		// 2. reverse the stretch between left and right
		for (int i = 0; curr != null && i < right - left + 1; i++) {
			ListNode next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}

		// 3. reconnect: prev is the stretch's new first node, curr the node after it
		ListNode subListHead = prev;
		if (before != null) {
			before.next = subListHead;
		} else {
			head = subListHead; // left = 1: the stretch starts at the head
		}
		subListTail.next = curr;
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head, 2, 4)); // 1 4 3 2 5

		ListNode head2 = fromList(asList(5));
		print(reverse(head2, 1, 1)); // 5
	}
}
