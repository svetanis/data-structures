package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 82. Remove Duplicates from Sorted List II
//
// The list is sorted. Remove every node whose value appears more than once, keep only the
// values that appear exactly once, and return the head.
//
// Equal values sit next to each other, in a run. prev is the last node known to stay; it
// starts on a fake node placed before the head, so the head can be removed like any other
// node. For each run, curr walks to the last node of the run. If the run is one node long,
// prev.next is still curr, so curr stays and prev moves onto it. Otherwise the whole run is
// cut out by pointing prev.next past it, and prev does not move.

public final class RemoveDupsInSortedListII {
  // Time Complexity: O(n), each node is looked at once
  // Space Complexity: O(1)

  public static ListNode remove(ListNode head) {
    ListNode dummy = new ListNode(0, head); // FAKE: gives the head a node before it
    ListNode prev = dummy;
    ListNode curr = head;
    while (curr != null) {
      // skip all nodes that have the same value
      while (curr.next != null && curr.next.val == curr.val) {
        curr = curr.next;
      }
      // if no dups, prev should point to current
      if (prev.next == curr) {
        prev = curr;
      } else {
        // bypass all duplicates
        prev.next = curr.next; // CUT: prev stays, the next run is not checked yet
      }
      // move to the next node in the list
      curr = curr.next;
    }
    return dummy.next; // RETURN: dummy.next, since the head itself may have been removed
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 2, 3, 3, 4, 4, 5));
    print(remove(head)); // 1 2 5

    ListNode head1 = fromList(asList(1, 1, 1, 2, 3));
    print(remove(head1)); // 2 3
  }
}
