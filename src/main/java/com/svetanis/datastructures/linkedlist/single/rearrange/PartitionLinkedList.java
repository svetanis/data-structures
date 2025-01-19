package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 86. Partition List

public final class PartitionLinkedList {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static Node partition(Node head, int x) {
    Node smaller = new Node(0);
    Node greater = new Node(0);
    Node smallerHead = smaller;
    Node greaterHead = greater;
    Node curr = head;
    while (curr != null) {
      if (curr.data < x) {
        smaller.next = curr;
        smaller = curr;
      } else {
        greater.next = curr;
        greater = curr;
      }
      curr = curr.next;
    }
    smaller.next = greaterHead.next;
    greater.next = null;
    return smallerHead.next;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(1, 3, 7, 5, 2, 9, 4));
    print(partition(head, 5)); // 1 3 2 4 7 5 9

    Node head2 = fromList(asList(1, 4, 3, 2, 5, 2));
    print(partition(head2, 3)); // 1 2 2 4 3 5

    Node head3 = fromList(asList(2, 1));
    print(partition(head3, 2)); // 1 2
  }
}
