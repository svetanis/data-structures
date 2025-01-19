package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 25. Reverse Nodes in k-Group

// Given the head of a LinkedList and a number k, 
// reverse every k sized sub-list starting from the head.
// if the number of nodes is not a multiple of k then
// left-out nodes, in the end, should remain as is

public final class ReverseInKGroupDummy {
  // Time Complexity: O(n)

  public static Node reverse(Node head, int k) {
    if (k <= 1 || head == null) {
      return head;
    }

    Node dummy = new Node(0, head);
    Node prev = dummy;
    Node curr = dummy;
    while (curr != null) {
      for (int i = 0; i < k && curr != null; i++) {
        curr = curr.next;
      }
      if (curr == null) {
        return dummy.next;
      }
      // temporarily store the next segment
      Node temp = curr.next;
      // detach the k nodes from the rest of the list
      curr.next = null;
      // start will be the new tail after reversal
      Node start = prev.next;
      // reverse k nodes
      prev.next = reverse(start);
      // connect the new tail with the temp segment
      start.next = temp;
      // move prev and curr pointers k nodes ahead
      prev = start;
      curr = prev;
    }
    return dummy.next;
  }

  private static Node reverse(Node head) {
    Node prev = null;
    Node curr = head;
    while (curr != null) {
      Node next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
    print(reverse(head, 3));

    Node head1 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head1, 2)); // 2 1 4 3 5

    Node head2 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head2, 3)); // 3 2 1 4 5
  }
}
