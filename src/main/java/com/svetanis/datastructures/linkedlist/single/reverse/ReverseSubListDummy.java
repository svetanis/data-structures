package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 92. Reverse Linked List II
//
// Given the head of a singly linked list and positions left <= right, counted from 1,
// reverse the nodes from position left to position right and return the head.
//
// Walk to the node before the stretch, reverse exactly right - left + 1 nodes, then connect
// both ends. The fake node in front means there is always a node before the stretch, even
// when left = 1, so the reconnection is one line with no branch.
//
// The stretch's first node is saved as subListTail before the loop, so the two
// reconnections can run in either order. Written as before.next.next = curr instead, the
// tail must be sewn first, while before.next still leads to that node.

public final class ReverseSubListDummy {
	// Time Complexity: O(n), one pass, which stops at position right
	// Space Complexity: O(1)

	public static ListNode reverse(ListNode head, int left, int right) {
		if (head.next == null || left == right) {
			return head;
		}
		// 1. walk to the node before the stretch
		ListNode dummy = new ListNode(0, head); // FAKE: left = 1 still has a node before the stretch
		ListNode before = dummy; // WALK from position 0 ...
		for (int i = 0; i < left - 1; i++) { // ... left - 1 steps: end (left - 1) minus start (0)
			before = before.next;
		}
		ListNode subListTail = before.next; // the stretch's first node, its last after the reversal

		// 2. reverse the stretch between left and right, ReverseIterative's four lines
		ListNode prev = null; // SEW TAIL overwrites what the first flip writes, so any start works
		ListNode curr = subListTail;
		for (int i = 0; i < right - left + 1; i++) { // COUNT: one turn per node in the stretch
			ListNode next = curr.next; // SAVE
			curr.next = prev; // FLIP
			prev = curr; // KEEP
			curr = next; // MOVE
		}

		// 3. reconnect: prev is the stretch's new first node, curr the node after it
		before.next = prev; // SEW FRONT
		subListTail.next = curr; // SEW TAIL
		return dummy.next; // RETURN: never head, because left = 1 changes the first node
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(1, 2, 3, 4, 5));
		print(reverse(head, 2, 4)); // 1 4 3 2 5

		ListNode head2 = fromList(asList(5));
		print(reverse(head2, 1, 1)); // 5
	}
}
