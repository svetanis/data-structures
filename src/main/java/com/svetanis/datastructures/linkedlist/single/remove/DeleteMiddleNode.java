package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 2095. Delete the Middle Node of a Linked List

public final class DeleteMiddleNode {
  // Time Complexity: O(n)

  public static Node delete(Node head) {
    if (head == null) {
      return null;
    }

    Node curr = head;
    Node midd = head;
    Node prev = midd;
    while (curr != null && curr.next != null) {
      curr = curr.next.next;
      prev = midd;
      midd = midd.next;
    }
    // delete middle node
    prev.next = midd.next;
    midd = null;
    return head;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(delete(head)); // 50 20 15 10 60

    Node head1 = fromList(asList(1, 3, 4, 7, 1, 2, 6));
    print(delete(head1)); // 1 3 4 1 2 6

    Node head2 = fromList(asList(1, 2, 3, 4));
    print(delete(head2)); // 1 2 4

    Node head3 = fromList(asList(2, 1));
    print(delete(head3)); // 2
  }
}
