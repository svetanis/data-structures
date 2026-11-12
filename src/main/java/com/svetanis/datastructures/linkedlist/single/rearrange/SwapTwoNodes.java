package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Swap two nodes in a linked list without swapping data
//
// Input: the head of a list whose values are all different, and two values x and y. Swap the
// node holding x with the node holding y by changing links, never values, and return the head.
// If either value is missing, or x and y are the same value, the list comes back unchanged.
//
// The one idea: to move a node you must change the link that points at it, and that link lives
// in the node before it. So each search returns two nodes: the one holding the value, and the one
// before it (null when the value sits at the head, and then the head itself is what has to
// change). With both predecessors known, point each predecessor at the other node, then exchange
// the two nodes' next links.
//
// Time: O(n) -- two searches of at most n steps each.
// Space: O(1) -- a few pointers.

public final class SwapTwoNodes {

  // what one search finds: the node holding the value, and the node before it. Either may be
  // null: prev at the head, curr when the value is missing
  private record Found(ListNode prev, ListNode curr) {}

  public static ListNode swap(ListNode head, int x, int y) {

    if (x == y) {
      return head; // BASE: the same node, nothing to swap
    }

    Found pairX = search(head, x);
    ListNode prevX = pairX.prev();
    ListNode currX = pairX.curr();

    Found pairY = search(head, y);
    ListNode prevY = pairY.prev();
    ListNode currY = pairY.curr();

    // a value that is not in the list: nothing to swap
    if (currX == null || currY == null) {
      return head;
    }

    // if x is not head
    if (prevX != null) {
      prevX.next = currY; // the node before x now leads to y
    } else { // if x is head
      head = currY;
    }

    // if y is not head
    if (prevY != null) {
      prevY.next = currX; // the node before y now leads to x
    } else { // if y is head
      head = currX;
    }

    // swap
    // when the two are neighbours, the predecessor lines above leave the earlier node pointing
    // at itself. That is the very link the later node needs (a link to the earlier node), so
    // these three lines still come out right
    ListNode temp = currY.next; // SAVE before currY.next is overwritten
    currY.next = currX.next; // EXCHANGE the two next links
    currX.next = temp;
    return head;
  }

  private static Found search(ListNode head, int x) {
    ListNode curr = head;
    ListNode prev = null;
    // stop on the node holding x, or past the end when x is missing
    while (curr != null && curr.val != x) { // WALK, keeping the node behind
      prev = curr;
      curr = curr.next;
    }
    return new Found(prev, curr);
  }

  public static void main(String[] args) {
    // two non-adjacent nodes
    ListNode head1 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(head1); // 10 15 12 13 20 14
    swap(head1, 12, 20);
    print(head1); // 10 15 20 13 12 14
    System.out.println(); // (empty line)

    // y is tail
    ListNode head2 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(head2); // 10 15 12 13 20 14
    swap(head2, 12, 14);
    print(head2); // 10 15 14 13 20 12
    System.out.println(); // (empty line)

    // two adjacent nodes
    ListNode head3 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(head3); // 10 15 12 13 20 14
    swap(head3, 12, 13);
    print(head3); // 10 15 13 12 20 14
    System.out.println(); // (empty line)

    // x is the second node
    ListNode head4 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(head4); // 10 15 12 13 20 14
    ListNode swapped = swap(head4, 15, 20);
    print(swapped); // 10 20 12 13 15 14
    System.out.println(); // (empty line)

    // x is head: the head itself changes, so use the returned node
    ListNode head5 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(swap(head5, 10, 20)); // 20 15 12 13 10 14

    // y is not in the list: unchanged
    ListNode head6 = fromList(asList(10, 15, 12, 13, 20, 14));
    print(swap(head6, 10, 99)); // 10 15 12 13 20 14
  }
}
