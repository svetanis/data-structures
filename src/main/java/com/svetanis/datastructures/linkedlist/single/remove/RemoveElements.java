package com.svetanis.datastructures.linkedlist.single.remove;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.datastructures.linkedlist.single.Nodes;

// 203. Remove Linked List Elements
//
// Remove every node whose value equals val and return the head.
//
// prev is the last node known to stay. It starts on a fake node placed before the head, so a
// head holding val is removed the same way as any other node. Each turn looks at prev.next: if
// it holds val, unlink it and keep prev where it is, because the new prev.next must be checked
// too; otherwise prev.next stays, and prev moves onto it.

public final class RemoveElements {
	// Time Complexity: O(n), each node is looked at once
	// Space Complexity: O(1), nodes are unlinked in place

	public static ListNode removeElements(ListNode head, int val) {
		ListNode dummy = new ListNode(-1); // FAKE: gives the head a node before it
		dummy.next = head;
		ListNode prev = dummy;
		while (prev.next != null) { // STOP when no node is left to check
			if (prev.next.val == val) {
				prev.next = prev.next.next; // SKIP: prev stays, the new prev.next is checked next
			} else {
				prev = prev.next; // MOVE: this node stays
			}
		}
		return dummy.next; // RETURN: dummy.next, since the head itself may have been removed
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 6, 3, 4, 5, 6 };
		ListNode head = Nodes.fromArray(a);
		Nodes.print(removeElements(head, 6)); // 1,2,3,4,5

		int[] a1 = { 7, 7, 7, 7 };
		ListNode head1 = Nodes.fromArray(a1);
		Nodes.print(removeElements(head1, 7)); // []
	}
}
