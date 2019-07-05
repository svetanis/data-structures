package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class LoopDetect {

  public static boolean hasLoop(Node head) {
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
    System.out.println(hasLoop(head));
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    System.out.println(hasLoop(head));
  }
}
