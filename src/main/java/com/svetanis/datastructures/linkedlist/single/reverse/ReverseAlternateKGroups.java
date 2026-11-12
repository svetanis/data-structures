package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternating k Nodes
//
// Input: the head of a singly linked list and a number k. Not a LeetCode problem, so there are
// no official limits; the lists here are a few dozen nodes. Returns: the head after every
// alternating k-node sub-list, starting from the head, is reversed. If the end of the list
// leaves a sub-list of fewer than k nodes, it is reversed too when it falls on a reversing
// turn. k <= 1 returns the list unchanged.
//
// The one idea: a sub-list is k nodes in a row; one node holds a value and an arrow to the next
// node. Each turn of the outer loop handles two sub-lists, in place. firstTail is the last node
// before the sub-list (null for the first one), and subListTail is the sub-list's first node,
// which becomes its last once reversed. Reverse up to k nodes, attach the reversed sub-list
// after firstTail (or make it the head), point subListTail at the node after the sub-list, then
// walk curr over the next k nodes, so that prev ends on the last node of the sub-list left
// alone.
//
// Time: O(n) -- each node is either reversed or walked over, once.
// Space: O(1) -- a few markers.
//
// Siblings: ReverseAlternateKGroupsRecursive and ReverseAlternateKGroupsFlag give the same answer
// by recursion, using O(n / k) stack.

public final class ReverseAlternateKGroups {

  public static ListNode reverse(ListNode head, int k) {
    if (k <= 1 || head == null) { // k = 1: every group is one node, so nothing moves
      return head;
    }

    ListNode curr = head;
    ListNode prev = null;
    while (curr != null) { // ONE TURN: a reversed sub-list, then a skipped one
      ListNode firstTail = prev; // the last node before the sub-list
      ListNode subListTail = curr; // the sub-list's first node, its last after the reversal
      ListNode next = null;
      // reverse k nodes
      for (int i = 0; curr != null && i < k; i++) { // REVERSE: up to k nodes
        next = curr.next; // SAVE
        curr.next = prev; // FLIP
        prev = curr; // KEEP
        curr = next; // MOVE
      }

      // connect with previous part
      if (firstTail != null) {
        firstTail.next = prev; // SEW FRONT: the reversed sub-list follows firstTail
      } else {
        head = prev; // the first sub-list: its new first node is the head
      }
      // connect with next part
      subListTail.next = curr; // SEW TAIL: the old first node leads to the sub-list left alone
      // skip k nodes
      for (int i = 0; curr != null && i < k; i++) { // SKIP: walk over the sub-list left alone
        prev = curr; // KEEP: ends on the last node walked over
        curr = curr.next;
      }
    }
    return head; // RETURN: head moved only on the first turn
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
    print(head); // 1 2 3 4 5 6 7 8
    print(reverse(head, 2)); // 2 1 3 4 6 5 7 8
  }
}
