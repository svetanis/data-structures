package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// print alternate nodes of the given Linked List, 
// first from head to end, and then from end to head. 
// If LL has even number of nodes, then skips last node. 
// For Linked List 1->2->3->4->5, print 1 3 5 5 3 1. 
// For Linked List 1->2->3->4->5->6, print 1 3 5 5 3 1.

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
