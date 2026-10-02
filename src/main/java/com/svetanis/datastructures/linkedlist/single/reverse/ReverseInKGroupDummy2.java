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
// Count the nodes first, so the loop knows how many full groups are left. For each group, prev
// is the node before it and curr is the group's first node. curr itself is never moved:
// instead the node right after curr, called next, is unlinked and inserted just after prev,
// which makes it the group's first node. After k - 1 such moves the group is reversed, curr is
// its last node, and curr becomes the node before the next group. The loop stops when fewer
// than k nodes are left, so a short last group stays as it is.

public final class ReverseInKGroupDummy2 {
	// Time Complexity: O(n), one pass to count, then k - 1 moves per group
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}

		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode prev = dummy;
		ListNode curr = dummy;
		ListNode next = dummy;
		int count = 0;
		while (curr.next != null) {
			curr = curr.next;
			count++;
		}
		while (count >= k) { // stop when fewer than k nodes are left: they stay as they are
			curr = prev.next;
			next = curr.next;
			for (int i = 1; i < k; i++) {
				curr.next = next.next; // unlink next: curr now skips over it
				next.next = prev.next; // next points at the group's current first node
				prev.next = next; // next is the group's first node now
				next = curr.next; // the following node to move
			}
			prev = curr; // curr is the group's last node: the node before the next group
			count -= k;
		}
		return dummy.next;
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
