package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Alternating split of a linked list
//
// Split one list into two, counting positions from 0: the nodes at positions 0, 2, 4, ... go
// to the first list and the nodes at positions 1, 3, 5, ... to the second, each keeping its
// order. Returns both heads; a list of 0 or 1 nodes gives an empty (null) second list, and the
// empty list gives two empty lists.
//
// Two chains grow side by side: even sits on the last node given to the first list, odd on the
// last node given to the second. curr is the next node not yet given out; it goes to even, and
// the node after it to odd. At the end both chains are ended with null, because the last node
// of each may still point into the other. LC 328 (Odd Even Linked List) builds the same two
// chains and then joins them; this keeps them apart.

public final class SplitAlternate {
  // Time Complexity: O(n), one pass
  // Space Complexity: O(1), the existing nodes are relinked

  // the two heads; either may be null when the list is too short to fill it
  public record Halves(ListNode first, ListNode second) {}

  public static Halves split(ListNode head) {
    if (head == null || head.next == null) {
      return new Halves(head, null); // 0 or 1 node: nothing goes to the second list
    }
    ListNode even = head;
    ListNode odd = head.next;
    ListNode curr = head.next.next; // the first node not yet given to either list
    ListNode h1 = even;
    ListNode h2 = odd;

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
    even.next = null; // end both lists: the last node of each may still point into the other
    odd.next = null;
    return new Halves(h1, h2);
  }

  public static void main(String[] args) {
    ListNode head = null;
    head = insertAtHead(head, 61);
    head = insertAtHead(head, 10);
    head = insertAtHead(head, 4);
    head = insertAtHead(head, 15);
    head = insertAtHead(head, 25);
    head = insertAtHead(head, 55);

    print(head); // 55 25 15 4 10 61

    Halves halves = split(head);
    print(halves.first()); // 55 15 10
    print(halves.second()); // 25 4 61

    Halves one = split(new ListNode(7));
    print(one.first()); // 7
    print(one.second()); // []
  }
}
