package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 21. Merge Two Sorted Lists
//
// Merge two sorted singly linked lists into one sorted list, made of their own nodes, and
// return its head.
//
// The smaller front node is the head of the answer, and its next is the merge of
// everything that is left. Each call takes one node, so the calls go n + m deep.

public final class MergeTwoSortedRecursive {
	// Time Complexity: O(n + m), each call places one node
	// Space Complexity: O(n + m), one stack frame per node taken

	public static ListNode merge(ListNode node1, ListNode node2) {
		if (node1 == null) {
			return node2;
		}
		if (node2 == null) {
			return node1;
		}

		if (node1.val < node2.val) {
			node1.next = merge(node1.next, node2);
			return node1;
		} else {
			node2.next = merge(node2.next, node1);
			return node2;
		}
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, 3, 5, 5, 6 };
		int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
		test(a1, a2); // 1 2 3 4 5 5 5 6 6 7 8 9 10 11

		int[] a3 = { 1, 2, 4 };
		int[] a4 = { 1, 3, 4 };
		test(a3, a4); // 1 1 2 3 4 4
	}

	private static void test(int[] a1, int[] a2) {
		ListNode head1 = fromArray(a1);
		ListNode head2 = fromArray(a2);
		ListNode merged = merge(head1, head2);
		print(merged);
	}
}
