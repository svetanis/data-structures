package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 25. Reverse Nodes in k-Group
//
// Given the head of a LinkedList and a number k,
// reverse every k sized sub-list starting from the head.
// if the number of nodes is not a multiple of k then
// left-out nodes, in the end, should remain as is
//
// Recursive. First count k nodes from head; if the list runs out first, the group is short and
// is returned as it is. Otherwise curr is the first node after the group, and the recursive
// call finishes everything from curr on. Then the group's k nodes are taken off the front one
// at a time, and each is put in front of curr, the finished part, so they end up in front of
// it in reverse order. The last node put in front is the new head.

public final class ReverseInKGroupRecursive {
	// Time Complexity: O(n), each node is counted once and moved once
	// Space Complexity: O(n / k) recursion stack, one call per group

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}
		int count = 0;
		ListNode curr = head;
		while (curr != null && count != k) {
			curr = curr.next;
			count++;
		}
		if (count == k) { // a full group; a short one falls through and is returned as it is
			curr = reverse(curr, k); // curr: the rest of the list, already finished
			while (count-- > 0) { // move each of the k nodes, one at a time
				ListNode next = head.next; // save the group's next node before head.next changes
				head.next = curr; // put head in front of the finished part
				curr = head; // the finished part now starts at head
				head = next;
			}
			head = curr; // the group's old last node, now the first
		}
		return head;
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
		print(reverse(head, 3)); // 3 2 1 6 5 4 7 8

		ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head1, 2)); // 2 1 4 3 5

		ListNode head2 = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head2, 3)); // 3 2 1 4 5
	}
}
