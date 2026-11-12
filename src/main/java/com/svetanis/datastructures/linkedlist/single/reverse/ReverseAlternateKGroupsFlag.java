package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternate k Nodes
//
// Input: the head of a singly linked list and a number k >= 1. Not a LeetCode problem, so there
// are no official limits; the lists here are a few dozen nodes. Returns: the head after the
// first k nodes are reversed, the next k are left as they are, the k after that are reversed,
// and so on. A short group at the end is reversed if it falls on a reversing turn.
//
// The one idea: a group is k nodes in a row; one node holds a value and an arrow to the next
// node. One call handles one group, and the flag reverseNextK says whether this group is
// reversed or only walked over. The same loop does both: it always moves prev and curr
// forward, and turns the arrow only when the flag is true. A reversed group's old first node
// (head) is now its last node, so the rest of the list is attached after head. A walked
// group's last node is prev, so the rest is attached after prev. Each call passes the opposite
// flag to the next.
//
// Time: O(n) -- each node is either reversed or walked over, once.
// Space: O(n / k) -- recursion stack, one waiting call per group.
//
// Siblings: ReverseAlternateKGroupsRecursive handles two groups per call, with no flag; and
// ReverseAlternateKGroups does it with a loop and O(1) space.

public final class ReverseAlternateKGroupsFlag {

  public static ListNode reverse(ListNode head, int k) {
    return reverse(head, k, true); // START: the first group is reversed
  }

  public static ListNode reverse(ListNode head, int k, boolean reverseNextK) {

    if (head == null) { // BASE: no group left
      return null;
    }

    int count = 1;
    ListNode prev = null;
    ListNode curr = head;

    // the loop serves two purposes
    // 1. if reverseNextK is true, then it reverses the k nodes
    // 2. if reverseNextK is false, then it moves the current pointer
    while (curr != null && count <= k) { // GROUP: up to k nodes, fewer at the end of the list
      ListNode next = curr.next; // SAVE: the way forward

      // reverse the nodes only if reverseNextK is true
      if (reverseNextK) {
        curr.next = prev; // FLIP: only on a reversing turn
      }
      prev = curr; // KEEP: the node just handled
      curr = next; // MOVE: by the saved name
      count++;
    }

    // 3. if reverseNextK is true, then node is the kth node.
    // so attach rest of the list after node
    // 4. after attaching, return the new head
    if (reverseNextK) {
      head.next = reverse(curr, k, !reverseNextK); // SEW: head is the group's last node now
      return prev; // RETURN: the group's new first node
    } else {
      // attach rest of the list after prev
      prev.next = reverse(curr, k, !reverseNextK); // SEW: prev is the group's last node, unchanged
      return head; // RETURN: the group is in its old order, head is still first
    }
  }

  public static void main(String[] args) {
    int k = 3;
    ListNode head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head); // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    head = reverse(head, k);
    print(head); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19

    ListNode head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2); // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    head2 = reverse(head2, k);
    print(head2); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19
  }
}
