package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 141. Linked List Cycle
//
// Input: the head of a singly linked list. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^5 <= val <= 10^5. Returns true if following next from the head comes back to a node
// already passed, and false if it reaches null.
//
// The one idea: two walkers start on the head. slow takes one node per turn, fast takes two.
// Without a cycle, fast reaches null and the loop ends. With a cycle, both end up going round
// it, and each turn fast gains exactly one node on slow, so the gap between them shrinks by one
// per turn until it is zero and they stand on the same node. A gap that shrinks by one can never
// jump over zero, which is why two steps against one is enough.
//
// Time: O(n) -- fast catches slow before slow has gone once round the cycle.
// Space: O(1) -- two pointers.

public final class CycleDetection {

  public static boolean hasCycle(ListNode head) {

    if (head == null) {
      return false; // BASE: an empty list has no cycle
    }
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) { // fast can take two steps only if both exist
      slow = slow.next; // STEP: one node
      fast = fast.next.next; // STEP: two nodes
      if (slow == fast) { // MEET: tested after the steps: before them both are on the head
        return true;
      }
    }
    return false; // fast reached the end of the list
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10));
    print(head); // 50 20 15 4 10
    System.out.println(hasCycle(head)); // false
    // create a loop for testing
    head.next.next.next.next.next = head.next.next; // 10 points back to 15
    System.out.println(hasCycle(head)); // true
  }
}
