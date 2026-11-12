package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 83. Remove Duplicates from Sorted List
//
// Input: the head of a sorted singly linked list. LeetCode: 0 <= n <= 300 nodes, values
// -100..100, sorted ascending. Returns: the head after every repeated value is cut down to one
// node, so each value appears once.
//
// The one idea: the list is sorted, so equal values sit next to each other, in a run. curr is a
// node that stays in the list. While the node after it has the same value, unlink that next
// node and keep curr where it is, because the new next node may hold the same value too. Only
// when the next value is different does curr move on to it. A run of any length shrinks to its
// first node, and the first node of the list is never removed, so no fake node is needed.
//
// Time: O(n) -- each node is looked at once; every step either unlinks a node or moves curr.
// Space: O(1) -- nodes are unlinked in place.
//
// Siblings: RemoveDupsInSortedListII (LC 82) removes a repeated value completely instead of
// keeping one copy, so the head can go and it needs a fake node.

public final class RemoveDupsInSortedList {

	public static ListNode remove(ListNode head) {
		ListNode curr = head; // KEEP: curr is a node that stays
		while (curr != null && curr.next != null) { // STOP when there is no next node to compare
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next; // SKIP the repeat; curr stays, the new next may repeat too
			} else {
				curr = curr.next; // MOVE: the next value is different, so it stays
			}
		}
		return head; // RETURN: the first node of a run is kept, so head never changes
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
