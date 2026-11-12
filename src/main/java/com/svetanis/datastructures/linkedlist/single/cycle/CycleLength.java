package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Length of the Cycle in a Linked List
//
// Input: the head of a singly linked list that may have a cycle. Returns the number of nodes
// that make up the cycle, or 0 if the list has no cycle. The list is not changed.
//
// The one idea: slow moves one node per turn and fast two, as in Linked List Cycle (LC 141).
// If there is a cycle they meet on a node inside it. Standing on a node of a cycle and
// following next until the walker is back on that same node takes exactly as many steps as the
// cycle has nodes, so count those steps.
//
// Time: O(n) -- finding the meeting point is O(n), then one walk round the cycle is at most n.
// Space: O(1) -- a few pointers and a counter.

public final class CycleLength {

  public static int cycleLength(ListNode head) {

    if (head == null) {
      return 0; // BASE: an empty list has no cycle
    }
    ListNode slow = head;
    ListNode fast = head;
    // find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next; // STEP: one node
      fast = fast.next.next; // STEP: two nodes
      if (slow == fast) { // MEET
        return length(slow); // slow is on a node inside the cycle
      }
    }
    return 0; // fast reached the end: no cycle
  }

  private static int length(ListNode node) {
    int count = 1; // COUNT the first step, taken before the test
    ListNode current = node.next;
    while (current != node) { // WALK round the cycle until back on the start
      current = current.next;
      count++;
    }
    return count;
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10));
    print(head); // 50 20 15 4 10
    System.out.println(cycleLength(head)); // 0
    // create a loop for testing
    head.next.next.next.next.next = head.next.next; // 10 points back to 15
    System.out.println(cycleLength(head)); // 3
  }
}
