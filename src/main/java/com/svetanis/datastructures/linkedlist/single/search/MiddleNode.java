package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class MiddleNode {

  public static Node middle(Node head) {
    if (head == null) {
      return null;
    }
    Node slow = head;
    Node fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  public static Node middleNode(Node head) {
    if (head == null) {
      return head;
    }
    Node slow = head;
    Node fast = head.next;
    while (fast != null) {
      fast = fast.next;
      if (fast != null) {
        slow = slow.next;
        fast = fast.next;
      }
    }
    return slow;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head);
    System.out.println(middle(head));
  }
}
