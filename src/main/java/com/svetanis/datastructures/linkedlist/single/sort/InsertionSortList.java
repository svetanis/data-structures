package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 147. Insertion Sort List
//
// Input: head -- the first node of a singly linked list. On LeetCode: 1 to 5000 nodes, each value
// -5000 to 5000. Returns the head of the same nodes rearranged into increasing order.
//
// The one idea: the answer is built as a second list that is always sorted, behind a fake node.
// Take the input's nodes one at a time. curr is the node taken and prev is the node it will go
// right after: starting from the fake node, prev walks forward until the node after it is larger
// than curr. Splice curr in there. Because the sorted list is always in order, putting curr at
// its spot keeps it in order. The fake node means that putting a node in front of the sorted
// list's first node needs no special case. Before curr's arrow is changed, the rest of the input
// is saved in next, because changing the arrow loses the only way forward.
//
// Time: O(n^2) -- each of the n nodes may walk the whole sorted list to find its spot
// Space: O(1) -- the nodes are reused; only a few pointers are kept
//
// Siblings: MergeSort (LC 148) sorts the same kind of list in O(n log n) by halving and merging.

public final class InsertionSortList {

	public static ListNode sort(ListNode head) {
		if (head == null || head.next == null) { // BASE: zero or one node is already sorted
			return head;
		}
		ListNode curr = head; // the next input node to place
		ListNode dummy = new ListNode(0); // fake node in front of the sorted list
		while (curr != null) { // TAKE the input nodes one at a time
			ListNode prev = dummy; // curr goes right after prev
			// <= walks past equal values, so equal values keep their input order
			while (prev.next != null && prev.next.val <= curr.val) { // FIND the spot
				prev = prev.next;
			}
			ListNode next = curr.next; // SAVE the rest of the input before curr's arrow changes
			curr.next = prev.next; // SPLICE curr in after prev
			prev.next = curr;
			curr = next; // MOVE on to the next input node
		}
		return dummy.next; // RETURN: the first node of the sorted list
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
