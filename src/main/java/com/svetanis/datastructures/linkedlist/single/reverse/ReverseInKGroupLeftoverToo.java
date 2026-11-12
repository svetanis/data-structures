package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Nodes in Groups of k, the Short Last Group Too
//
// Input: the head of a singly linked list and a number k. Not a LeetCode problem (LC 25 leaves
// a short last group as it is; this one reverses it), so there are no official limits; the
// lists here are a few dozen nodes. Returns: the head after every group of k nodes, starting
// from the head, is reversed. A last group of fewer than k nodes is reversed too. k <= 1
// returns the list unchanged.
//
// The one idea: a group is k nodes in a row; one node holds a value and an arrow to the next
// node. firstTail is the last node of the group before (null for the first group), and
// subListTail is this group's first node, which becomes its last once reversed. The inner loop
// reverses up to k nodes and stops early when the list runs out, which is what reverses a
// short last group too. Then the reversed group is attached after firstTail (or becomes the
// head), subListTail is pointed at the node after the group, and subListTail is the node before
// the next group.
//
// Time: O(n) -- each node is reversed once.
// Space: O(1) -- a few markers.
//
// Siblings: ReverseInKGroupLeftoverTooRecursive gives the same answer by recursion, using O(n / k)
// stack. ReverseInKGroup (the version to carry), ReverseInKGroupCutAndReverse,
// ReverseInKGroupMoveToFront and ReverseInKGroupRecursive (LC 25) leave the short last group as it
// is.

public final class ReverseInKGroupLeftoverToo {

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) { // BASE: k = 1 moves nothing
			return head;
		}
		ListNode curr = head;
		ListNode prev = null;
		while (curr != null) { // ONE TURN: one group
			ListNode firstTail = prev; // the last node of the group before
			ListNode subListTail = curr; // this group's first node, its last after the reversal
			ListNode next = null;
			// reverse k nodes
			for (int i = 0; curr != null && i < k; i++) { // REVERSE: up to k nodes, fewer at the end
				next = curr.next; // SAVE
				curr.next = prev; // FLIP
				prev = curr; // KEEP
				curr = next; // MOVE
			}
			// connect with the previous part
			if (firstTail != null) {
				firstTail.next = prev; // SEW FRONT: the reversed group follows firstTail
			} else {
				head = prev; // the first group: its new first node is the head
			}
			// connect with the next part
			subListTail.next = curr; // SEW TAIL: the old first node leads to the next group
			// prepare for next sublist
			prev = subListTail; // KEEP: the node before the next group
		}
		return head; // RETURN: head moved only on the first turn
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head); // 1 2 3 4 5 6 7 8
		print(reverse(head, 3)); // 3 2 1 6 5 4 8 7
	}
}
