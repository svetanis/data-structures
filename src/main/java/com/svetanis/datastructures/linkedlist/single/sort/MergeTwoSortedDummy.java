package com.svetanis.datastructures.linkedlist.single.sort;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromArray;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 21. Merge Two Sorted Lists
//
// Merge two sorted singly linked lists into one sorted list, made of their own nodes, and
// return its head.
//
// Build the answer behind a fake node: take the smaller front node each turn and hang it
// on the end. The fake node means the first node taken needs no special case, and neither
// does an empty list: the loop never runs, and the other list is attached whole.

public final class MergeTwoSortedDummy {
  // Time Complexity: O(n + m)
  // Space Complexity: O(1), the nodes are reused

  public static ListNode merge(ListNode head1, ListNode head2) {
    ListNode dummy = new ListNode(); // FAKE: the answer's first node needs something in front of it
    ListNode current = dummy; // TAIL: the last node of the answer so far
    while (head1 != null && head2 != null) { // STOP: as soon as either list runs out
      if (head1.val <= head2.val) { // COMPARE the fronts; a tie takes the first list
        current.next = head1; // ATTACH the smaller front to the end of the answer
        head1 = head1.next; // ADVANCE: that list's next node is its new front
      } else {
        current.next = head2; // ATTACH
        head2 = head2.next; // ADVANCE
      }
      current = current.next; // TAIL moves onto the node just attached
    }
    current.next = head1 == null ? head2 : head1; // LEFTOVER: already sorted, one arrow attaches it
    return dummy.next; // RETURN: the first ATTACH wrote dummy's arrow, so this is the first node
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 2, 3, 5, 5, 6 };
    int[] a2 = { 4, 5, 6, 7, 8, 9, 10, 11 };
    test(a1, a2); // 1 2 3 4 5 5 5 6 6 7 8 9 10 11

    int[] a3 = { 1, 2, 4 };
    int[] a4 = { 1, 3, 4 };
    test(a3, a4); // 1 1 2 3 4 4
  }

  private static void test(int[] a1, int[] a2) {
    ListNode head1 = fromArray(a1);
    ListNode head2 = fromArray(a2);
    ListNode merged = merge(head1, head2);
    print(merged);
  }
}
