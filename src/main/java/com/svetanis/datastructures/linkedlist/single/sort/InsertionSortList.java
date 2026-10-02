package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 147. Insertion Sort List
//
// Sort a singly linked list into increasing order, reusing its own nodes, and return the
// new head.
//
// The answer is built as a second list that is always sorted, behind a fake node. Take the
// input's nodes one at a time. For each one, walk the sorted list from the fake node until
// the next node is larger, and splice the taken node in at that spot. The fake node means
// that putting a node in front of the sorted list's first node needs no special case.

public final class InsertionSortList {
	// Time Complexity: O(n^2), each node may walk the whole sorted list to find its spot
	// Space Complexity: O(1), the nodes are reused

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode curr = head; // the next input node to place
		ListNode dummy = new ListNode(0); // fake node in front of the sorted list
		while (curr != null) {
			ListNode prev = dummy; // curr goes right after prev
			// <= walks past equal values, so equal values keep their input order
			while (prev.next != null && prev.next.val <= curr.val) {
				prev = prev.next;
			}
			ListNode next = curr.next; // save the rest of the input before curr's arrow changes
			curr.next = prev.next;
			prev.next = curr;
			curr = next;
		}
		return dummy.next;
	}

	public static void main(String[] args) {
		int[] a = { 4, 2, 1, 3 };
		ListNode head = fromArray(a);
		print(sort(head)); // 1 2 3 4

		int[] a1 = { -1, 5, 3, 4, 0 };
		ListNode head1 = fromArray(a1);
		print(sort(head1)); // -1 0 3 4 5
	}
}
