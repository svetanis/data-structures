package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 83. Remove Duplicates from Sorted List
//
// The list is sorted, so equal values sit next to each other. Keep one node of every value
// and return the head.
//
// curr is a node that stays in the list. While the node after it has the same value, unlink
// that next node and keep curr where it is, because the new next node may hold the same value
// too. Only when the next value is different does curr move on to it.

public final class RemoveDupsInSortedList {
	// Time Complexity: O(n), each node is looked at once
	// Space Complexity: O(1), nodes are unlinked in place

	public static ListNode remove(ListNode head) {
		ListNode curr = head;
		while (curr != null && curr.next != null) { // STOP when there is no next node to compare
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next; // SKIP the repeat; curr stays, the new next may repeat too
			} else {
				curr = curr.next; // MOVE: the next value is different, so it stays
			}
		}
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 3, 4, 4, 5));
		print(remove(head)); // 1 2 3 4 5

		ListNode head1 = fromList(asList(1, 1, 1, 2, 3));
		print(remove(head1)); // 1 2 3

		// 83 keeps one copy of every value; 82 keeps only the values that
		// were never repeated. these two inputs are where they differ, and
		// RemoveDupsInSortedListII prints 1 2 5 and 2 3 for them
		ListNode head2 = fromList(asList(1, 1, 1));
		print(remove(head2)); // 1
	}
}
