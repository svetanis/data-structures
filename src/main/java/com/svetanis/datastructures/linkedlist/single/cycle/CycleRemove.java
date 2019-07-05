package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class CycleRemove {

  public static void cycleRemove(Node head) {

    if (head == null || head.next == null) {
      return;
    }

    Node slow = head;
    Node fast = head;

    // find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        break;
      }
    }

    // no meeting point? - no loop
    if (fast.next == null) {
      return;
    }
    // move slow to head.
    // keep fast at meeting point.
    // each are k steps from the loop start.
    // if they move at the same pace,
    // they must meet at Loop Start
    slow = head;
    while (slow.next != fast.next) {
      slow = slow.next;
      fast = fast.next;
    }
    fast.next = null; // remove loop
  }

  public static void main(String[] args) {
    Node head = fromList(asList(50, 20, 15, 4, 10));
    print(head);
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    cycleRemove(head);
    print(head);
  }
}
