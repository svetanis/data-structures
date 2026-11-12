package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 21. Merge Two Sorted Lists
//
// Input: node1 and node2 -- the first nodes of two singly linked lists, each sorted in increasing
// order (either may be empty). On LeetCode: 0 to 50 nodes in each, each value -100 to 100.
// Returns the head of one sorted list made of their own nodes.
//
// The one idea: both lists are sorted, so the first node of the answer is the smaller of the two
// front nodes, and everything after it is the merge of what is left -- the same problem on
// shorter lists. So the smaller front node becomes the head, its next is set to the result of
// merging the rest, and that smaller node is returned. When one list is empty, the other is the
// answer. Each call places one node, so the calls go n + m deep. A call swaps the order of its
// two lists when the second list's front is taken, so on a tie the front node taken alternates
// between the two lists; any such order is sorted.
//
// Time: O(n + m) -- each call places one node
// Space: O(n + m) -- one stack frame per node placed
//
// Siblings: MergeTwoSortedDummy and MergeTwoSortedIterative do the same merge with a loop and
// O(1) space; the first uses a fake node, the second picks the head before the loop.

public final class MergeTwoSortedRecursive {

	public static ListNode merge(ListNode node1, ListNode node2) {
		if (node1 == null) { // BASE: one list is used up, the other is the rest
			return node2;
		}
		if (node2 == null) {
			return node1;
		}

		if (node1.val < node2.val) { // TAKE the smaller front node; the rest is merged by a call
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
