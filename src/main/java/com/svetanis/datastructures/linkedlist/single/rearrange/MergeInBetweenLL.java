package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 1669. Merge In Between Linked Lists
//
// Given list1, list2 and two positions a <= b counted from 0, remove list1's nodes at positions
// a through b and put the whole of list2 in their place. Returns the head of list1. LC 1669
// promises 1 <= a, so the head of list1 is never removed.
//
// Only two nodes of list1 matter: the one at position a - 1, just before the removed run, where
// list2 is attached; and the one at position b, the last removed node, whose next is where
// list1 carries on. Find both, attach list2 after the first, walk to the last node of list2,
// and point it at the node after b.

public final class MergeInBetweenLL {
	// Time Complexity: O(n), n = the length of list1 plus list2; list1 is walked up to b, list2 to its end
	// Space Complexity: O(1)

	public static ListNode mergeInBetween(ListNode root1, int a, int b, ListNode root2) {
		ListNode head = root1;
		for (int i = 0; i < a - 1; i++) { // stop on position a - 1, the node before the removed run
			head = head.next;
		}
		ListNode tail = root1;
		for (int i = 0; i < b; i++) { // stop on position b, the last node removed
			tail = tail.next;
		}
		head.next = root2; // attach list2 after position a - 1
		while (head.next != null) { // walk to the last node of list2
			head = head.next;
		}
		head.next = tail.next; // the last node of list2 points at what came after position b
		tail.next = null; // detach the removed run from the rest of list1
		return root1;
	}

	public static void main(String[] args) {
		ListNode head1 = fromList(asList(10, 1, 13, 6, 9, 5));
		ListNode head2 = fromList(asList(1000000, 1000001, 1000002));
		print(mergeInBetween(head1, 3, 4, head2)); // 10,1,13,1000000,1000001,1000002,5
		ListNode head3 = fromList(asList(0, 1, 2, 3, 4, 5, 6));
		ListNode head4 = fromList(asList(1000000, 1000001, 1000002, 1000003, 1000004));
		print(mergeInBetween(head3, 2, 5, head4)); // 0,1,1000000,1000001,1000002,1000003,1000004,6
	}
}
