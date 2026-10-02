package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import java.util.Optional;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 142. Linked List Cycle II
//
// Given the head of a singly linked list, return the node where a cycle begins, or nothing
// if the list has no cycle.
//
// Phase 1: slow moves one node per turn, fast two; if there is a cycle they meet inside it,
// and if fast runs off the end there is none. Phase 2: one pointer goes back to the head and
// both move one node per turn. The distance from the head to the cycle's start equals the
// distance from the meeting point to the start, going round, so they meet exactly there.
//
// CycleStartPointSubmit is the same method returning null for "no cycle", as LeetCode asks.
// This one returns an empty Optional, so the caller cannot forget that case.

public final class CycleStartPoint {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static Optional<ListNode> cycleStart(ListNode head) {
    if (head == null) {
      return Optional.empty();
    }

    ListNode slow = head;
    ListNode fast = head;

    // phase 1: find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        // phase 2 runs here, where a meeting has happened, so the list has a cycle
        slow = head;
        while (slow != fast) {
          slow = slow.next;
          fast = fast.next;
        }
        return Optional.of(fast);
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
