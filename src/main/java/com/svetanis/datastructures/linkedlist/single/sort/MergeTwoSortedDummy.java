package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

// 21. Merge Two Sorted Lists

public final class MergeTwoSortedDummy {
  // Time Complexity: O(n + m)
  // Space Complexity: O(1)

  public static Node merge(Node head1, Node head2) {
    if (head1 == null) {
      return head2;
    }
    if (head2 == null) {
      return head1;
    }

    Node dummy = new Node();
    Node current = dummy;
    while (head1 != null && head2 != null) {
      if (head1.data <= head2.data) {
        current.next = head1;
        head1 = head1.next;
      } else {
        current.next = head2;
        head2 = head2.next;
      }
      current = current.next;
    }
    current.next = head1 == null ? head2 : head1;
    return dummy.next;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 2, 3, 5, 5, 6 };
    int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
    test(a1, a2);

    int[] a3 = { 1, 2, 4 };
    int[] a4 = { 1, 3, 4 };
    test(a3, a4);
  }

  private static void test(int[] a1, int[] a2) {
    Node head1 = fromArray(a1);
    Node head2 = fromArray(a2);
    Node merged = merge(head1, head2);
    print(merged);
  }
}
