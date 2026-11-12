package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.search.KthNode.kthNode;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Rotate a linked list to the left by k nodes
//
// Input: the head of a singly linked list and k. Rotate the list counter-clockwise by k nodes:
// move the first k nodes, in their order, to the end of the list and return the new head:
// 10 20 30 40 50 60 with k = 4 becomes 50 60 10 20 30 40. k is not reduced by the length: when
// k is 0 or less, or k is the length or more, the list comes back unchanged.
//
// The one idea: only three links change. Find the kth node (the last node that moves) and the
// last node of the list. The last node points at the old head, which closes the list into a
// ring; the node after the kth becomes the new head; and the kth node's next becomes null, which
// opens the ring there.
//
// Time: O(n) -- one walk to the kth node, then on to the last node.
// Space: O(1) -- a few pointers.
//
// Siblings: RotateToTheRight (LC 61) turns the other way and reduces k by the length first.

public final class RotateToTheLeft {

  public static ListNode rotate(ListNode head, int k) {

    if (k <= 0 || head == null || head.next == null) {
      return head; // BASE: nothing to rotate
    }

    ListNode curr = kthNode(head, k - 1); // counts from 0, so index k - 1 is the kth node

    // if current is null, k is greater
    // than the count of nodes
    if (curr == null) {
      return head; // k past the end: unchanged
    }

    // current points to (k - 1)-th node
    // store it in a variable
    ListNode kthNode = curr; // KEEP the last node that moves

    // current will point to
    // last node after this loop
    while (curr.next != null) { // WALK to the last node
      curr = curr.next;
    }
    // change next of last node
    // to previous head
    curr.next = head; // CLOSE the ring: the last node points at the old head

    // change head to k-th node
    // when k equals the length, kthNode is the last node and its next is now the old head, so
    // the list comes back unchanged
    head = kthNode.next; // the new head: the node after the kth

    // change next of kth node to null
    kthNode.next = null; // OPEN the ring after the kth node

    return head;
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(10, 20, 30, 40, 50, 60));
    print(head); // 10 20 30 40 50 60
    print(rotate(head, 4)); // 50 60 10 20 30 40
  }
}
