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
// Recursive. One call reverses the first k nodes, or fewer when the list runs out, with the
// usual four lines (save the next node, turn the arrow, move prev, move curr). head was the
// group's first node, so after the reversal it is the group's last node, and it is pointed at
// whatever the recursive call returns for the rest of the list. prev, the last node reversed,
// is the group's new first node, and it is returned.

public final class ReverseInGroupsRecursive {
	// Time Complexity: O(n), each node is reversed once
	// Space Complexity: O(n / k) recursion stack, one call per group

	public static ListNode reverseInGroups(ListNode head, int k) {
		int count = 0;
		ListNode curr = head;
		ListNode next = null;
		ListNode prev = null;

		// reverse first k nodes of the linked list
		while (curr != null && count < k) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
			count++;
		}

		// next is now a pointer to (k + 1)-th node
		// recursively call for the list starting from current
		// and make rest of the list as next of first node
		if (next != null) {
			head.next = reverseInGroups(next, k);
		}
		// prev is new head of the input list
		return prev;
	}

	public static void main(String[] args) {
		int k = 3;
		ListNode head = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head); // 1 2 3 4 5 6 7 8
		head = reverseInGroups(head, k);
		print(head); // 3 2 1 6 5 4 8 7

		k = 5;
		ListNode head2 = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head2); // 1 2 3 4 5 6 7 8
		head2 = reverseInGroups(head2, k);
		print(head2); // 5 4 3 2 1 8 7 6
	}
}
