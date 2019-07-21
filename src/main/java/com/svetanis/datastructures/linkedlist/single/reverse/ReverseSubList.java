package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// Given the head of a LinkedList and two positions ‘p’ and ‘q’, 
// reverse the LinkedList from position ‘p’ to ‘q’.

public final class ReverseSubList {

  public static Node reverse(Node head, int p, int q) {
    // Time Complexity: O(n)

    if (p == q) {
      return head;
    }

    Node curr = head;
    Node prev = null;
    for (int i = 0; curr != null && i < p - 1; i++) {
      prev = curr;
      curr = curr.next;
    }
    Node firstTail = prev; // points to node @ p - 1
    Node subListTail = curr;

    // reverse the subList
    for (int i = 0; curr != null && i < q - p + 1; i++) {
      Node next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }

    Node subListHead = prev;

    if (firstTail != null) {
      firstTail.next = subListHead;
    } else {
      head = subListHead;
    }
    subListTail.next = curr;
    return head;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(1, 2, 3, 4, 5));
    print(head);
    print(reverse(head, 2, 4));
  }
}
