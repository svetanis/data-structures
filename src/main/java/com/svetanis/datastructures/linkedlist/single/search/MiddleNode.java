package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNotNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.isNull;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 876. Middle of the Linked List
//
// given a head of a Singly LinkedList,
// find the middle node of the SLL
//
// SLL is short for singly linked list. With an even count there are two middle nodes. middle
// returns the second one, at index n / 2 counting from 0, which is what LC 876 asks for.
// middleNode returns the first one, at index (n - 1) / 2. With an odd count both are the same.
//
// slow moves one node per turn and fast two, so fast has always gone twice as far as slow.
// When fast cannot go further, slow is halfway. middle starts both on the head. middleNode
// starts fast one node ahead, so on an even count it runs out one turn sooner, and slow stops
// on the first middle instead of the second.

public final class MiddleNode {
	// Time Complexity: O(n), one pass
	// Space Complexity: O(1)

	public static ListNode middle(ListNode head) {
		if (isNull(head)) {
			return null;
		}
		ListNode slow = head;
		ListNode fast = head;
		while (isNotNull(fast) && isNotNull(fast.next)) { // STOP when fast cannot take two steps
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	public static ListNode middleNode(ListNode head) {
		if (isNull(head)) {
			return head;
		}
		ListNode slow = head;
		ListNode fast = head.next; // one node ahead: an even count stops on the first middle
		while (isNotNull(fast)) {
			fast = fast.next;
			if (isNotNull(fast)) { // slow moves only when fast completed both steps
				slow = slow.next;
				fast = fast.next;
			}
		}
		return slow;
	}

	public static void main(String[] args) {
		ListNode head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
		print(head); // 50 20 15 4 10 60
		System.out.println(middle(head)); // 4
	}
}
