package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 142. Linked List Cycle II
//
// Given the head of a singly linked list, return the node where a cycle begins, or null if
// the list has no cycle.
//
// Phase 1: slow moves one node per turn, fast two; if there is a cycle they meet inside it,
// and if fast runs off the end there is none. Phase 2: start walks from the head while slow
// walks on from the meeting point, one node each per turn. The distance from the head to the
// cycle's start equals the distance from the meeting point to the start, going round, so
// they meet exactly there.

public final class CycleStartPointSubmit {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static ListNode cycleStart(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode slow = head;
		ListNode fast = head;
		// phase 1: find the meeting point
		while (fast != null && fast.next != null) { // fast lands on null or on the last node
			slow = slow.next; // STEP: slow one node
			fast = fast.next.next; // STEP: fast two; in the cycle the gap shrinks by one
			if (slow == fast) { // MEET: after a step; before one they are equal
				// phase 2: from the head and from the meeting point, one node each per turn
				ListNode start = head; // RESTART: head to entrance F = meeting point to entrance C - a
				while (start != slow) { // WALK both one node per turn
					start = start.next;
					slow = slow.next;
				}
				return start; // RETURN the cycle's start
			}
		}
		return null; // NO CYCLE: fast ran off the end
	}

	public static void main(String[] args) {
		ListNode head = fromList(asList(50, 20, 15, 4, 10));
		print(head); // 50 20 15 4 10
		System.out.println(cycleStart(head)); // null
		head.next.next.next.next.next = head.next.next; // 10 points back to 15
		System.out.println(cycleStart(head)); // 15
	}
}
