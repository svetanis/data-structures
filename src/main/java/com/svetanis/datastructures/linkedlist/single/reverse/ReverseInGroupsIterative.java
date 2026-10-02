package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Nodes in Groups of k, the Short Last Group Too
//
// Given the head of a LinkedList and a number k,
// reverse every k sized sub-list starting from the head.
// if in the end, left with a sub-list with less than k
// nodes, reverse it too
//
// Iterative, in place. firstTail is the last node of the group before (null for the first
// group), and subListTail is this group's first node, which becomes its last once reversed.
// The inner loop reverses up to k nodes and stops early when the list runs out, which is what
// reverses a short last group too. Then the reversed group is attached after firstTail (or
// becomes the head), subListTail is pointed at the node after the group, and subListTail is
// the node before the next group.

public final class ReverseInGroupsIterative {
	// Time Complexity: O(n), each node is reversed once
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}
		ListNode curr = head;
		ListNode prev = null;
		while (curr != null) {
			ListNode firstTail = prev;
			ListNode subListTail = curr;
			ListNode next = null;
			// reverse k nodes
			for (int i = 0; curr != null && i < k; i++) {
				next = curr.next;
				curr.next = prev;
				prev = curr;
				curr = next;
			}
			// connect with the previous part
			if (firstTail != null) {
				firstTail.next = prev;
			} else {
				head = prev;
			}
			// connect with the next part
			subListTail.next = curr;
			// prepare for next sublist
			prev = subListTail;
		}
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head); // 1 2 3 4 5 6 7 8
		print(reverse(head, 3)); // 3 2 1 6 5 4 8 7
	}
}
