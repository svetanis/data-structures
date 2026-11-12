package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 142. Linked List Cycle II
//
// Input: the head of a singly linked list. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^5 <= val <= 10^5. Returns the node where a cycle begins, or null if the list has no
// cycle. The list is not changed.
//
// The one idea: phase 1 is Linked List Cycle (LC 141): slow moves one node per turn, fast two;
// if there is a cycle they meet inside it, and if fast runs off the end there is none.
// Phase 2: let a be the nodes before the cycle starts, and b the nodes from the cycle's start to
// the meeting point. When they meet, slow has walked a + b nodes and fast twice that, so fast is
// a + b nodes ahead, and being ahead and on the same node means a + b is a whole number of laps.
// Going on from the meeting point for a more nodes therefore finishes a lap and lands on the
// start. So start walks from the head while slow walks on from the meeting point, one node each
// per turn, and after a turns both are on the cycle's start: they meet exactly there.
//
// Time: O(n) -- phase 1 is at most a + c turns (c is the cycle's length), phase 2 is a turns.
// Space: O(1) -- three pointers.
//
// Siblings: CycleStartPoint is the same method returning an Optional instead of null.

public final class CycleStartPointSubmit {

	public static ListNode cycleStart(ListNode head) {
		if (head == null) {
			return null; // BASE: an empty list has no cycle
		}
		ListNode slow = head;
		ListNode fast = head;
		// phase 1: find the meeting point
		while (fast != null && fast.next != null) { // fast lands on null or on the last node
			slow = slow.next; // STEP: slow one node
			fast = fast.next.next; // STEP: fast two; in the cycle the gap shrinks by one
			if (slow == fast) { // MEET: after a step; before one they are equal
				// phase 2: from the head and from the meeting point, one node each per turn
				ListNode start = head; // RESTART: a nodes to the start from the head, and from the meeting point
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
