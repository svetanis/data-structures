package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 148. Sort List
//
// Sort a singly linked list into increasing order, reusing its own nodes, and return the
// new head.
//
// Merge sort. Find the last node of the first half with two pointers (fast moves two nodes
// for each one that slow moves), cut the arrow after it so the list becomes two lists, sort
// each half the same way, then merge the two sorted halves behind a fake node by taking the
// smaller front node each turn. A list of zero or one node is already sorted. For two nodes
// the first half is exactly one node, so every cut makes both halves smaller.

public final class MergeSort {
	// Time Complexity: O(n log n), the halving goes log n levels deep and each level touches
	// every node once
	// Space Complexity: O(log n), one stack frame per level of halving; merging uses a loop

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode middle = middleNode(head);
		ListNode middleNext = middle.next;
		middle.next = null;

		// recursively sort and merge the sublists
		ListNode left = sort(head);
		ListNode right = sort(middleNext);
		return mergeDummy(left, right);
	}

	private static ListNode middleNode(ListNode head) {
		if (head == null) {
			return head;
		}
		ListNode slow = head;
		ListNode fast = head.next;
		while (fast != null) {
			fast = fast.next;
			if (fast != null) {
				slow = slow.next;
				fast = fast.next;
			}
		}
		return slow;
	}

	private static ListNode mergeDummy(ListNode left, ListNode right) {
		ListNode dummy = new ListNode();
		ListNode curr = dummy;
		while (left != null && right != null) {
			if (left.val <= right.val) {
				curr.next = left;
				left = left.next;
			} else {
				curr.next = right;
				right = right.next;
			}
			curr = curr.next;
		}
		curr.next = left == null ? right : left;
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
