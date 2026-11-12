package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Alternating split of a linked list
//
// Input: the head of a singly linked list. Split it into two lists, counting positions from 0:
// the nodes at positions 0, 2, 4, ... go to the first list and the nodes at positions 1, 3, 5, ...
// to the second, each keeping its order. Returns both heads; a list of 0 or 1 nodes gives an
// empty (null) second list, and the empty list gives two empty lists.
//
// The one idea: every second node belongs to the same list, so each list is built by skipping
// the node the other list takes. Two chains grow side by side: even sits on the last node given
// to the first list, odd on the last node given to the second. curr is the next node not yet
// given out; it goes to even, and the node after it to odd. At the end both chains are ended
// with null, because the last node of each may still point into the other.
//
// Time: O(n) -- one pass.
// Space: O(1) -- the existing nodes are relinked.
//
// Siblings: RearrangeOddEven (LC 328) builds the same two chains and then joins them; this keeps
// them apart.

public final class SplitAlternate {

  // the two heads; either may be null when the list is too short to fill it
  public record Halves(ListNode first, ListNode second) {}

  public static Halves split(ListNode head) {
    if (head == null || head.next == null) {
      return new Halves(head, null); // BASE: 0 or 1 node: nothing goes to the second list
    }
    ListNode even = head;
    ListNode odd = head.next;
    ListNode curr = head.next.next; // the first node not yet given to either list
    ListNode h1 = even;
    ListNode h2 = odd;

    while (curr != null) {
      even.next = curr; // GIVE curr to the first list
      even = even.next;
      curr = curr.next;

      if (curr != null) {
        odd.next = curr; // GIVE the next node to the second list
        odd = odd.next;
        curr = curr.next;
      }
    }
    even.next = null; // END both lists: the last node of each may still point into the other
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
    print(one.second()); // (empty line)
  }
}
