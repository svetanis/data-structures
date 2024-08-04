package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// given the head of a LinkedList with
// a cycle, find the length of the cycle

// once the fast and slow pointers meet,
// save the slow pointer and iterate the
// whole cycle with another pointer until
// we see the slow pointer again to find 
// the length of the cycle

public final class CycleLength {

  public static int cycleLength(Node head) {
	// Time Complexity: O(n)
	  
    if (head == null) {
      return 0;
    }
    Node slow = head;
    Node fast = head;
    // find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        return length(slow);
      }
    }
    return 0;
  }

  private static int length(Node node) {
	int count = 0;
    Node current = node;
	do {
		current = current.next;
		count++;
	} while(current != node);
	return count;
  }
  
  public static void main(String[] args) {
    Node head = fromList(asList(50, 20, 15, 4, 10));
    print(head);
    System.out.println(cycleLength(head));
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    System.out.println(cycleLength(head));
  }
}
