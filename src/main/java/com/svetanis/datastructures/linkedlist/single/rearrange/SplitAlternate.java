package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.java.base.Pair;

public final class SplitAlternate {

  public static Pair<Node, Node> split(Node head) {
    Node even = head;
    Node odd = head.next;
    Node curr = head.next.next;
    Node h1 = even;
    Node h2 = odd;

    while (curr != null) {
      even.next = curr;
      even = even.next;
      curr = curr.next;

      if (curr != null) {
        odd.next = curr;
        odd = odd.next;
        curr = curr.next;
      }
    }
    even.next = null;
    odd.next = null;
    return Pair.build(h1, h2);
  }

  public static void main(String[] args) {
    Node head = null;
    head = insertAtHead(head, 61);
    head = insertAtHead(head, 10);
    head = insertAtHead(head, 4);
    head = insertAtHead(head, 15);
    head = insertAtHead(head, 25);
    head = insertAtHead(head, 55);

    print(head);

    Pair<Node, Node> pair = split(head);
    Node head1 = pair.getLeft();
    Node head2 = pair.getRight();

    print(head1);
    print(head2);
  }
}
