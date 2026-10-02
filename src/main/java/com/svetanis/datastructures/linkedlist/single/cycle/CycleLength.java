package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Length of the Cycle in a Linked List
//
// given the head of a LinkedList with
// a cycle, find the length of the cycle
// Return the number of nodes that make up the cycle, or 0 if the list has no cycle.
//
// once the fast and slow pointers meet,
// save the slow pointer and iterate the
// whole cycle with another pointer until
// we see the slow pointer again to find
// the length of the cycle
// slow moves one node per turn and fast two, as in Linked List Cycle (LC 141). If there is
// a cycle they meet on a node inside it, and walking next from a node inside the cycle
// comes back to that node after exactly as many steps as the cycle has nodes.

public final class CycleLength {
  // Time Complexity: O(n), finding the meeting point, then one walk round the cycle
  // Space Complexity: O(1), a few pointers and a counter

  public static int cycleLength(ListNode head) {
	  
    if (head == null) {
      return 0;
    }
    ListNode slow = head;
    ListNode fast = head;
    // find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        return length(slow); // slow is on a node inside the cycle
      }
    }
    return 0; // fast reached the end: no cycle
  }

  private static int length(ListNode node) {
	int count = 0;
    ListNode current = node;
	do {
		current = current.next;
		count++;
	} while(current != node);
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
