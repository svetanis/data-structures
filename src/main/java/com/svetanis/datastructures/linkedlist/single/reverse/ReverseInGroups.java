package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseInGroups {

  public static Node reverseInGroups(Node head, int k) {

    int count = 0;

    Node curr = head;
    Node next = null;
    Node prev = null;

    // reverse first k nodes of the linked list
    while (curr != null && count < k) {
      next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
      count++;
    }

    // next is now a pointer to (k + 1)-th node
    // recursively call for the list starting from current
    // and make rest of the list as next of first node
    if (next != null) {
      head.next = reverseInGroups(next, k);
    }
    // prev is new head of the input list
    return prev;
  }

  public static void main(String[] args) {
    int k = 3;
    Node head = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
    print(head);
    head = reverseInGroups(head, k);
    print(head);

    k = 5;
    Node head2 = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
    print(head2);
    head2 = reverseInGroups(head2, k);
    print(head2);
  }
}
