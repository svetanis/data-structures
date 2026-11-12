package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Input: the head of a singly linked list. LeetCode: 0 <= n <= 5000 nodes, values
// -5000..5000. Returns: the head of the reversed list (null for an empty list).
//
// The one idea: one node holds a value and an arrow to the next node. Walk the list once and
// turn each arrow around, so it points at the node behind it. Before an arrow is turned, the
// node it pointed to is saved in next, because turning it loses the only way forward. Every
// node gets the same four lines, in this order: SAVE, FLIP, KEEP, MOVE.
//
// Time: O(n) -- each node is visited once.
// Space: O(1) -- three markers.
//
// Siblings: ReverseListMoveToFront inserts each node at the front of a new list behind a fake node.
// ReverseListRecursive turns the arrows on the way back from the recursion,
// ReverseListTailRecursive turns them on the way forward with prev and curr as arguments, and
// ReverseListStack pushes the nodes and pops them back.

public final class ReverseList {

	public static ListNode reverse(ListNode head) {
		ListNode prev = null; // END: the reversed part so far; the old head's flip points it at null
		ListNode curr = head;
		while (curr != null) { // STOP: curr, not curr.next, so the last node gets its turn
			ListNode next = curr.next; // SAVE: the next line destroys the only way forward
			curr.next = prev; // FLIP: this node points at the one behind it
			prev = curr; // KEEP: a name on the node just finished
			curr = next; // MOVE: by the saved name, never by curr.next
		}
		return prev; // RETURN: curr is null, prev is the old last node
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(85, 15, 4, 20));
		print(reverse(head)); // 20 4 15 85

		ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1)); // 5 4 3 2 1

		ListNode head2 = fromList(asList(1, 2));
		print(reverse(head2)); // 2 1
	}
}
