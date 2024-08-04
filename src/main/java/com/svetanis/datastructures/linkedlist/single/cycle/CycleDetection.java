package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// given the head of a Singly LinkedList,
// determine if the SLL has a cycle in it or not

// traverse SLL using two pointers. 
// move one pointer by one and the other pointer by two.
// if these pointers meet at some node then there is a loop.
// if pointers don't meet then SLL doesn't have a loop.

public final class CycleDetection {

  public static boolean hasCycle(Node head) {
	// Time Complexity: O(n)
	  
    if (head == null) {
      return false;
    }
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(50, 20, 15, 4, 10));
    print(head);
    System.out.println(hasCycle(head));
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    System.out.println(hasCycle(head));
  }
}
