package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseOdd {

  public static void alternate(Node head) {

    // base case
    if (head == null) {
      return;
    }

    System.out.print(head.data + " ");

    if (head.next != null) {
      alternate(head.next.next);
    }

    System.out.print(head.data + " ");
  }

  public static void main(String[] agrs) {
    Node head = fromList(newArrayList(4, 3, 2, 1));
    print(head);
    alternate(head);
  }
}
