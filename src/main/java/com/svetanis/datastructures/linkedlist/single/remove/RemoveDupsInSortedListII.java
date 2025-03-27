package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 82. Remove Duplicates from Sorted List II

public final class RemoveDupsInSortedListII {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static Node remove(Node head) {
    Node dummy = new Node(0, head);
    Node prev = dummy;
    Node curr = head;
    while (curr != null) {
      // skip all nodes that have the same value
      while (curr.next != null && curr.next.data == curr.data) {
        curr = curr.next;
      }
      // if no dups, prev should point to current
      if (prev.next == curr) {
        prev = curr;
      } else {
        // bypass all duplicates
        prev.next = curr.next;
      }
      // move to the next node in the list
      curr = curr.next;
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(1, 2, 3, 3, 4, 4, 5));
    print(remove(head)); // 1 2 5

    Node head1 = fromList(asList(1, 1, 1, 2, 3));
    print(remove(head1)); // 2 3
  }
}
