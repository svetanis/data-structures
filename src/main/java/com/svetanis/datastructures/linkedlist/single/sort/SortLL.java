package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2046. Sort Linked List Already Sorted Using Absolute Values
//
// Input: head -- the first node of a singly linked list that is sorted by absolute value
// (0 2 -5 5 10 -10). On LeetCode: 1 to 10^5 nodes, each value -5000 to 5000. Returns the head of
// the same nodes rearranged into ordinary increasing order.
//
// The one idea: sorted by distance from zero, the values that are zero or above are already in
// increasing order. The negative values come in order of growing distance from zero, which is
// decreasing order. So walk the list once and move every negative node to the front: each one
// moved goes in front of the ones moved before it, which reverses the negatives into increasing
// order, all ahead of the rest. The first node never needs to move: if it is negative, it is the
// negative closest to zero, which is the last negative in the answer. prev is the node before
// curr, so that curr can be cut out of the list.
//
// Time: O(n) -- one pass, each move to the front is a few arrow changes
// Space: O(1) -- the nodes are reused
//
// Siblings: none in this package; MergeSort and InsertionSortList sort any list, in O(n log n)
// and O(n^2).

public final class SortLL {

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) { // BASE: zero or one node is already sorted
			return head;
		}
		ListNode prev = head; // the node just before curr
		ListNode curr = head.next; // the first node is never moved
		while (curr != null) { // WALK the list once
			if (curr.val < 0) { // MOVE a negative node to the front
				// store next node
				ListNode next = curr.next;
				// remove current node
				prev.next = next;
				// move current node to front
				curr.next = head;
				head = curr;
				// move to the next node
				curr = next;
			} else { // SKIP a node that is zero or above: it is already in place
				prev = curr;
				curr = curr.next;
			}
		}
		return head; // RETURN: the last node moved to the front, or the original head
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
