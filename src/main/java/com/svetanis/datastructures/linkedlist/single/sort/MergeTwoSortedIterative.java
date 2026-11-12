package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 21. Merge Two Sorted Lists
//
// Input: head1 and head2 -- the first nodes of two singly linked lists, each sorted in increasing
// order (either may be empty). On LeetCode: 0 to 50 nodes in each, each value -100 to 100.
// Returns the head of one sorted list made of their own nodes.
//
// The one idea: both lists are sorted, so the smallest node not yet placed is always one of the
// two front nodes. Take the smaller front node each turn and hang it on the end of the answer,
// and when one list runs out, attach the other whole, because it is already sorted. Without a
// fake node the answer has no first node to hang things on, so the smaller of the two front nodes
// is chosen as the head before the loop -- the special case the fake node exists to remove.
//
// Time: O(n + m) -- each node is attached once
// Space: O(1) -- the nodes are reused
//
// Siblings: MergeTwoSortedDummy is the same merge with a fake node, which removes the head
// special case; on a tie it takes the first list, where this one takes the second.
// MergeTwoSortedRecursive merges the rest by a call, one stack frame per node.

public final class MergeTwoSortedIterative {

	public static ListNode merge(ListNode head1, ListNode head2) {
		if (head1 == null) { // BASE: an empty list leaves the other as the answer
			return head2;
		}
		if (head2 == null) {
			return head1;
		}
		ListNode head = (head1.val < head2.val) ? head1 : head2; // HEAD: the answer's first node
		if (head1.val < head2.val) {
			head1 = head1.next; // ADVANCE past the node chosen as head
		} else {
			head2 = head2.next;
		}

		ListNode curr = head; // the last node of the answer so far
		while (head1 != null || head2 != null) {
			if (head1 == null) {
				curr.next = head2; // LEFTOVER: what is left is already sorted
				return head;
			}
			if (head2 == null) {
				curr.next = head1;
				return head;
			}
			if (head1.val < head2.val) { // TAKE the smaller front; a tie takes the second list
				curr.next = head1;
				curr = curr.next;
				head1 = head1.next;
			} else {
				curr.next = head2;
				curr = curr.next;
				head2 = head2.next;
			}
		}
		return head; // RETURN: the first node chosen
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
