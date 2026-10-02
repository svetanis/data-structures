package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2046. Sort Linked List Already Sorted Using Absolute Values
//
// The list is sorted by distance from zero (0 2 -5 5 10 -10). Rearrange its nodes so the
// values are in ordinary increasing order, and return the new head.
//
// Sorted by distance from zero, the values that are zero or above are already in increasing
// order. The negative values come in order of growing distance from zero, which is
// decreasing order. So walk the list once and move every negative node to the front: each
// one moved goes in front of the ones moved before it, which reverses the negatives into
// increasing order, all ahead of the rest. The first node never needs to move: if it is
// negative, it is the negative closest to zero, which is the last negative in the answer.

public final class SortLL {
	// Time Complexity: O(n), one pass, each move to the front is a few arrow changes
	// Space Complexity: O(1), the nodes are reused

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode prev = head;
		ListNode curr = head.next;
		while (curr != null) {
			if (curr.val < 0) {
				// store next node
				ListNode next = curr.next;
				// remove current node
				prev.next = next;
				// move current node to front
				curr.next = head;
				head = curr;
				// move to the next node
				curr = next;
			} else {
				prev = curr;
				curr = curr.next;
			}
		}
		return head;
	}

	public static void main(String[] args) {
		int[] a = { 0, 2, -5, 5, 10, -10 };
		ListNode head = fromArray(a);
		print(sort(head)); // -10 -5 0 2 5 10

		int[] a1 = { 0, 1, 2 };
		ListNode head1 = fromArray(a1);
		print(sort(head1)); // 0 1 2

		int[] a2 = { 1 };
		ListNode head2 = fromArray(a2);
		print(sort(head2)); // 1
	}
}
