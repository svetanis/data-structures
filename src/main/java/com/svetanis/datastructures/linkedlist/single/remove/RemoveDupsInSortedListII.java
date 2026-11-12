package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 82. Remove Duplicates from Sorted List II
//
// Input: the head of a sorted singly linked list. LeetCode: 0 <= n <= 300 nodes, values
// -100..100, sorted ascending. Returns: the head after every node whose value appears more
// than once is removed, so only the values that appear exactly once are kept.
//
// The one idea: equal values sit next to each other, in a run. prev is the last node known to
// stay; it starts on a fake node placed before the head, so the head can be removed like any
// other node. For each run, curr walks to the last node of the run. If the run is one node
// long, prev.next is still curr, so curr stays and prev moves onto it. Otherwise the whole run
// is cut out by pointing prev.next past it, and prev does not move, because the node after the
// run has not been checked yet.
//
// Time: O(n) -- each node is looked at once, by the walk inside a run or by the outer step.
// Space: O(1) -- nodes are unlinked in place.
//
// Siblings: RemoveDupsInSortedList (LC 83) keeps one copy of a repeated value instead of none.

public final class RemoveDupsInSortedListII {

  public static ListNode remove(ListNode head) {
    ListNode dummy = new ListNode(0, head); // FAKE: gives the head a node before it
    ListNode prev = dummy; // KEEP: the last node known to stay
    ListNode curr = head;
    while (curr != null) {
      // skip all nodes that have the same value
      while (curr.next != null && curr.next.val == curr.val) { // WALK to the last node of the run
        curr = curr.next;
      }
      // if no dups, prev should point to current
      if (prev.next == curr) { // ALONE: the run is one node, so it stays
        prev = curr;
      } else {
        // bypass all duplicates
        prev.next = curr.next; // CUT: prev stays, the next run is not checked yet
      }
      // move to the next node in the list
      curr = curr.next; // MOVE: to the first node of the next run
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
