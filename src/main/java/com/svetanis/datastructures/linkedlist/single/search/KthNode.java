package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class KthNode {

  public static Node kthNode(Node head, int k) {
    if (head == null || k < 0) {
      return null;
    }

    int count = 0;
    Node curr = head;
    while (curr != null && count < k) {
      curr = curr.next;
      count++;
    }
    return curr;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head);
    System.out.println(kthNode(head, 0));
    System.out.println(kthNode(head, 4));
    System.out.println(kthNode(head, 5));
  }
}
