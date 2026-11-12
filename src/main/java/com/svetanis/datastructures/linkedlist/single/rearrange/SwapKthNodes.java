package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 1721. Swapping Nodes in a Linked List
//
// Input: the head of a list and k. On LeetCode: 1 <= k <= number of nodes <= 10^5,
// 0 <= val <= 100. Swap the kth node from the beginning with the kth node from the end, and
// return the head. k counts from 1 at either end. LeetCode lets you swap the two values; this
// version moves the nodes themselves, by changing links only. If k is less than 1 or larger than
// the length, the list comes back unchanged.
//
// The one idea: to move a node you must change the link that points at it, and that link lives
// in the node before it. So walk to the kth node from the head, keeping the node before it. For
// the kth node from the end, start one pointer on the kth node and one on the head, and move
// both until the first is on the last node: the second has then moved the same distance and sits
// k from the end. The swap has two extra cases: either node may be the head, which has no node
// before it, and the two may be neighbours.
//
// Time: O(n) -- one walk to the kth node, then one walk to the end.
// Space: O(1) -- a few pointers.

public final class SwapKthNodes {

  public static ListNode swapKth(ListNode head, int k) {

    if (head == null || k < 1) {
      return head; // BASE: nothing to swap
    }

    // 1. walk to the kth node from the head, keeping the node before it.
    // k = 1 must leave currX ON the head, so the loop runs k - 1 times
    ListNode prevX = null;
    ListNode currX = head;
    for (int i = 1; i < k && currX != null; i++) { // WALK k - 1 steps
      prevX = currX;
      currX = currX.next;
    }
    if (currX == null) {
      // fewer than k nodes, so there is no kth node from either end
      return head;
    }

    // 2. the kth node from the tail. a pointer starting at currX and one
    // starting at the head reach the end together, so when the first runs
    // out the second has travelled the same distance and sits k from the end
    ListNode prevY = null;
    ListNode currY = head;
    for (ListNode node = currX; node.next != null; node = node.next) { // WALK both to the end
      prevY = currY;
      currY = currY.next;
    }
    if (currX == currY) {
      // the same node counted from both ends: nothing to swap
      return head;
    }
    return swap(head, prevX, currX, prevY, currY);
  }

  // relink two distinct nodes. either one may be the head, in which case
  // its predecessor is null and the head itself has to be reassigned; and
  // the two may be neighbours, in which case the earlier one IS the
  // other's predecessor and writing through it would overwrite a pointer
  // that has not been read yet
  private static ListNode swap(ListNode head, ListNode prevX, ListNode x, ListNode prevY, ListNode y) {
    if (prevX == y) {
      // y comes first: swap the roles so only one adjacency case is left
      return swap(head, prevY, y, prevX, x);
    }
    ListNode afterY = y.next; // SAVE before y.next is overwritten
    if (prevX != null) {
      prevX.next = y; // the node before x now leads to y
    } else {
      head = y; // x was the head: y is the new head
    }
    if (prevY == x) {
      // x sits immediately before y
      y.next = x;
    } else {
      if (prevY != null) {
        prevY.next = x; // the node before y now leads to x
      } else {
        head = x; // y was the head: x is the new head
      }
      y.next = x.next;
    }
    x.next = afterY; // x takes y's old place in the chain
    return head;
  }

  public static void main(String[] args) {
    // 1->2->3->4->5->6->7->8

    for (int k = 1; k <= 8; k++) {
      print(swapKth(build(), k));
    }
    // 8 2 3 4 5 6 7 1
    // 1 7 3 4 5 6 2 8
    // 1 2 6 4 5 3 7 8
    // 1 2 3 5 4 6 7 8
    // 1 2 3 5 4 6 7 8
    // 1 2 6 4 5 3 7 8
    // 1 7 3 4 5 6 2 8
    // 8 2 3 4 5 6 7 1

    // k = 4 and k = 5 pick out neighbours, which is the case a plain
    // four-assignment swap turns into a node pointing at itself
    print(swapKth(build(), 4)); // 1 2 3 5 4 6 7 8

    // k past the end, and a list too short to have a kth node
    print(swapKth(build(), 9)); // 1 2 3 4 5 6 7 8
    print(swapKth(fromRange(1), 1)); // 1
    print(swapKth(fromRange(2), 1)); // 2 1
  }

  private static ListNode build() {
    return fromRange(8);
  }

  private static ListNode fromRange(int n) {
    ListNode head = null;
    for (int i = n; i >= 1; --i) {
      head = insertAtHead(head, i);
    }
    return head;
  }
}
