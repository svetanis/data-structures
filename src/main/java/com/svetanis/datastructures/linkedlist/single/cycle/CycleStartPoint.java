package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.google.common.base.Optional;
import com.svetanis.datastructures.linkedlist.single.Node;

public final class CycleStartPoint {

  public static Optional<Node> cycleStart(Node head) {

    if (head == null) {
      return null;
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
      return absent();
    }
    // move slow to head.
    // keep fast at meeting point.
    // each are k steps from the loop start.
    // if they move at the same pace,
    // they must meet at Loop Start
    slow = head;
    while (slow != fast) {
      slow = slow.next;
      fast = fast.next;
    }
    return of(fast);
  }

  public static void main(String[] args) {
    Node head = fromList(asList(50, 20, 15, 4, 10));
    print(head);
    System.out.println(cycleStart(head));
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    System.out.println(cycleStart(head));
  }
}
