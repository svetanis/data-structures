package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// given the head of Singly LinkedList,
// reverse the SLL. return the new head
// of the reversed SLL

public final class ReverseIterative {

  public static Node reverse(Node head) {
    // Time Complexity: O(n)

    Node curr = head;
    Node prev = null;
    Node next = null;
    while (curr != null) {
      next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(85, 15, 4, 20));
    print(head);
    print(reverse(head));
  }
}
