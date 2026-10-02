package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 141. Linked List Cycle
//
// given the head of a Singly LinkedList,
// determine if the SLL has a cycle in it or not
// Return true if following next from the head comes back to a node already passed, and
// false if it reaches null.
//
// traverse SLL using two pointers.
// move one pointer by one and the other pointer by two.
// if these pointers meet at some node then there is a loop.
// if pointers don't meet then SLL doesn't have a loop.
// Each pointer holds one node of the list. Without a cycle, fast reaches null and the loop
// ends. With a cycle, both end up going round it, and each turn fast gains one node on slow,
// so the gap between them closes to zero and they stand on the same node.

public final class CycleDetection {
  // Time Complexity: O(n), fast catches slow before slow has gone once round the cycle
  // Space Complexity: O(1), two pointers

  public static boolean hasCycle(ListNode head) {
	  
    if (head == null) {
      return false;
    }
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) { // fast can take two steps only if both exist
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) { // tested after the steps: before them both are on the head
        return true;
      }
    }
    return false; // fast reached the end of the list
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(50, 20, 15, 4, 10));
    print(head); // 50 20 15 4 10
    System.out.println(hasCycle(head)); // false
    // create a loop for testing
    head.next.next.next.next.next = head.next.next; // 10 points back to 15
    System.out.println(hasCycle(head)); // true
  }
}
