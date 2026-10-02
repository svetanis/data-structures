package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.search.KthNode.kthNode;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Rotate a linked list to the left by k nodes
//
// given a Singly LinkedList,
// rotate the SLL counter-clockwise
// by k nodes
//
// Move the first k nodes, in their order, to the end of the list and return the new head:
// 10 20 30 40 50 60 with k = 4 becomes 50 60 10 20 30 40. k is not reduced by the length:
// when k is 0 or less, or k is the length or more, the list comes back unchanged.
//
// Only three links change. Find the kth node (the last node that moves) and the last node of
// the list. The last node points at the old head, which closes the list into a ring; the node
// after the kth becomes the new head; and the kth node's next becomes null, which opens the
// ring there.

public final class RotateToTheLeft {
  // Time Complexity: O(n), one walk to the kth node, then on to the last node
  // Space Complexity: O(1)

  public static ListNode rotate(ListNode head, int k) {

    if (k <= 0 || head == null || head.next == null) {
      return head;
    }

    ListNode curr = kthNode(head, k - 1); // counts from 0, so index k - 1 is the kth node

    // if current is null, k is greater
    // than the count of nodes
    if (curr == null) {
      return head;
    }

    // current points to (k - 1)-th node
    // store it in a variable
    ListNode kthNode = curr;

    // current will point to
    // last node after this loop
    while (curr.next != null) {
      curr = curr.next;
    }
    // change next of last node
    // to previous head
    curr.next = head;

    // change head to k-th node
    // when k equals the length, kthNode is the last node and its next is now the old head, so
    // the list comes back unchanged
    head = kthNode.next;

    // change next of kth node to null
    kthNode.next = null;

    return head;
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(10, 20, 30, 40, 50, 60));
    print(head); // 10 20 30 40 50 60
    print(rotate(head, 4)); // 50 60 10 20 30 40
  }
}
