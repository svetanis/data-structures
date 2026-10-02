package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 21. Merge Two Sorted Lists
//
// Merge two sorted singly linked lists into one sorted list, made of their own nodes, and
// return its head.
//
// The same merge as MergeTwoSortedDummy without the fake node: the smaller of the two front
// nodes is chosen as the head before the loop, which is the special case the fake node
// exists to remove.

public final class MergeTwoSortedIterative {
	// Time Complexity: O(n + m), each node is attached once
	// Space Complexity: O(1), the nodes are reused

	public static ListNode merge(ListNode head1, ListNode head2) {
		if (head1 == null) {
			return head2;
		}
		if (head2 == null) {
			return head1;
		}
		ListNode head = (head1.val < head2.val) ? head1 : head2; // the answer's first node
		if (head1.val < head2.val) {
			head1 = head1.next;
		} else {
			head2 = head2.next;
		}

		ListNode curr = head; // the last node of the answer so far
		while (head1 != null || head2 != null) {
			if (head1 == null) {
				curr.next = head2; // what is left is already sorted
				return head;
			}
			if (head2 == null) {
				curr.next = head1;
				return head;
			}
			if (head1.val < head2.val) { // a tie takes the second list; either order is sorted
				curr.next = head1;
				curr = curr.next;
				head1 = head1.next;
			} else {
				curr.next = head2;
				curr = curr.next;
				head2 = head2.next;
			}
		}
		return head;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		ListNode head1 = fromArray(a1);
		ListNode head2 = fromArray(a2);
		ListNode merged = merge(head1, head2);
		print(merged); // 1 2 3 4 5 5 5 6 6 7 8 9 10 11
	}
}
