package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.Nodes.size;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class IntersectionNode {

  public static Node intersection(Node head1, Node head2) {
    // Time Complexity: O(n + m)
    // Auxiliary Space: O(1)

    int n = size(head1);
    int m = size(head2);

    if (n > m) {
      int dif = n - m;
      return intersection(head1, head2, dif);
    } else {
      int dif = m - n;
      return intersection(head2, head1, dif);
    }
  }

  private static Node intersection(Node head1, Node head2, int dif) {

    Node curr1 = head1;
    Node curr2 = head2;

    for (int i = 0; i < dif; ++i) {
      if (curr1 == null) {
        return null;
      }
      curr1 = curr1.next;
    }

    while (curr1 != null && curr2 != null) {
      if (curr1 == curr2) {
        return curr1;
      }
      curr1 = curr1.next;
      curr2 = curr2.next;
    }
    return null;
  }

  public static void main(String[] args) {
    Node head1 = new Node(10);
    Node head2 = new Node(3);
    head2.next = new Node(6);
    head2.next.next = new Node(9);
    Node node3 = new Node(15);
    head1.next = node3;
    head2.next.next.next = node3;
    head1.next.next = new Node(30);
    print(head1);
    print(head2);
    System.out.println(intersection(head1, head2));
  }
}
