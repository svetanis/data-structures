package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseTailRecursive {

  public static Node reverse(Node head) {
    if (head == null) {
      return null;
    }
    return reverse(head, null, head);
  }

  private static Node reverse(Node curr, Node prev, Node head) {
    if (curr.next == null) {
      head = curr;
      // update next to prev
      curr.next = prev;
      return head;
    }

    // save current.next node for recursive call
    Node next = curr.next;

    // and update next
    curr.next = prev;

    return reverse(next, curr, head);
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(8, 7, 6, 5, 4, 2, 2, 1));
    print(head);
    print(reverse(head));
  }
}
