package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import java.util.Optional;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 142. Linked List Cycle II
//
// Input: the head of a singly linked list. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^5 <= val <= 10^5. Returns the node where a cycle begins, or nothing if the list has no
// cycle. The list is not changed.
//
// The one idea: phase 1 is Linked List Cycle (LC 141): slow moves one node per turn, fast two;
// if there is a cycle they meet inside it, and if fast runs off the end there is none.
// Phase 2: let a be the nodes before the cycle starts, and b the nodes from the cycle's start to
// the meeting point. When they meet, slow has walked a + b nodes and fast twice that, so fast is
// a + b nodes ahead, and being ahead and on the same node means a + b is a whole number of laps.
// Going on from the meeting point for a more nodes therefore finishes a lap and lands on the
// start. So one pointer goes back to the head, both move one node per turn, and after a turns
// each is on the cycle's start: they meet exactly there.
//
// Time: O(n) -- phase 1 is at most a + c turns (c is the cycle's length), phase 2 is a turns.
// Space: O(1) -- two pointers.
//
// Siblings: CycleStartPointSubmit is the same method returning null for "no cycle", as
// LeetCode asks. This one returns an empty Optional, so the caller cannot forget that case.

public final class CycleStartPoint {

  public static Optional<ListNode> cycleStart(ListNode head) {
    if (head == null) {
      return Optional.empty(); // BASE: an empty list has no cycle
    }

    ListNode slow = head;
    ListNode fast = head;

    // phase 1: find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next; // STEP: one node
      fast = fast.next.next; // STEP: two nodes
      if (slow == fast) { // MEET
        // phase 2 runs here, where a meeting has happened, so the list has a cycle
        slow = head; // RESTART one pointer from the head
        while (slow != fast) { // WALK both one node per turn until they meet on the start
          slow = slow.next;
          fast = fast.next;
        }
        return Optional.of(fast); // RETURN the cycle's start
      }
    }
    // fast ran off the end: no cycle
    return Optional.empty();
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10));
    print(head); // 50 20 15 4 10
    System.out.println(cycleStart(head)); // Optional.empty
    head.next.next.next.next.next = head.next.next; // 10 points back to 15
    System.out.println(cycleStart(head)); // Optional[15]
  }
}
