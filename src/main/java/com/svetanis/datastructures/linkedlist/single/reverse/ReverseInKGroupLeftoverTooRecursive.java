package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Nodes in Groups of k, the Short Last Group Too
//
// Input: the head of a singly linked list and a number k >= 1. Not a LeetCode problem (LC 25
// leaves a short last group as it is; this one reverses it), so there are no official limits;
// the lists here are a few dozen nodes. Returns: the head after every group of k nodes,
// starting from the head, is reversed. A last group of fewer than k nodes is reversed too.
//
// The one idea: a group is k nodes in a row; one node holds a value and an arrow to the next
// node. One call reverses the first k nodes, or fewer when the list runs out, with the usual
// four lines (save the next node, turn the arrow, move prev, move curr). head was the group's
// first node, so after the reversal it is the group's last node, and it is pointed at whatever
// the recursive call returns for the rest of the list. prev, the last node reversed, is the
// group's new first node, and it is returned.
//
// Time: O(n) -- each node is reversed once.
// Space: O(n / k) -- recursion stack, one waiting call per group.
//
// Siblings: ReverseInKGroupLeftoverToo gives the same answer with a loop and O(1) space.
// ReverseInKGroupRecursive (LC 25) leaves a short last group as it is.

public final class ReverseInKGroupLeftoverTooRecursive {

	public static ListNode reverseInGroups(ListNode head, int k) {
		int count = 0;
		ListNode curr = head;
		ListNode next = null;
		ListNode prev = null;

		// reverse first k nodes of the linked list
		while (curr != null && count < k) { // REVERSE: up to k nodes, fewer at the end
			next = curr.next; // SAVE
			curr.next = prev; // FLIP
			prev = curr; // KEEP
			curr = next; // MOVE
			count++;
		}

		// next is now a pointer to (k + 1)-th node
		// recursively call for the list starting from current
		// and make rest of the list as next of first node
		if (next != null) {
			head.next = reverseInGroups(next, k); // SEW: head is the group's last node now
		}
		// prev is new head of the input list
		return prev; // RETURN: the group's new first node
	}

	public static void main(String[] args) {
		int k = 3;
		ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head); // 1 2 3 4 5 6 7 8
		head = reverseInGroups(head, k);
		print(head); // 3 2 1 6 5 4 8 7

		k = 5;
		ListNode head2 = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(head2); // 1 2 3 4 5 6 7 8
		head2 = reverseInGroups(head2, k);
		print(head2); // 5 4 3 2 1 8 7 6
	}
}
