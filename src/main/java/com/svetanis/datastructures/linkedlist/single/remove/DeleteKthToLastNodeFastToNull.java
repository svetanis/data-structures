package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import java.util.NoSuchElementException;

// 19. Remove Nth Node From End of List
//
// Input: the head of a singly linked list and a number k. LeetCode: the list has sz nodes,
// 1 <= sz <= 30, node values 0..100, and 1 <= k <= sz. Returns: the head of the list after the
// node that is k places from the end is removed (k = 1 is the last node).
//
// The one idea: two markers hold a fixed gap. A marker is one node of the list. Number the
// nodes 1..L and put a fake node at position 0. The node to remove is at position L - k + 1, so
// slow must stop on the node before it, at L - k. fast runs until it falls off the end, onto
// null, which counts as position L + 1. The gap is end minus start: (L + 1) - (L - k) = k + 1.
// So fast starts k + 1 steps ahead, and when it reaches null, slow is where it must be.
//
// Time: O(n) -- fast walks the list once; slow walks the same distance less k + 1.
// Space: O(1) -- the fake node and two markers.
//
// Siblings: DeleteKthToLastNodeDummy stops fast on the last node instead, one position
// earlier, so its gap is k; it returns the list unchanged on a k larger than the list, where
// this file throws NoSuchElementException.

public final class DeleteKthToLastNodeFastToNull {

  public static ListNode remove(ListNode head, int k) {
    ListNode dummy = new ListNode(0, head); // FAKE: removing the first node needs a node before it
    ListNode fast = dummy;
    ListNode slow = dummy;
    for (int i = 0; i < k + 1; i++) { // GAP: fast goes k + 1 steps ahead
      if (fast == null) { // CHECK: a step is still owed and there is nowhere to step from
        throw new NoSuchElementException("k is larger than the list");
      }
      fast = fast.next;
    }
    while (fast != null) { // WALK together until fast falls off the end
      fast = fast.next;
      slow = slow.next;
    }
    slow.next = slow.next.next; // SKIP: slow is on the node before the one to remove
    return dummy.next; // RETURN: never head, because the head itself may have been removed
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(remove(head, 3)); // 50 20 15 10 60

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(remove(head1, 2)); // 1 2 3 5

    ListNode head2 = fromList(asList(1));
    print(remove(head2, 1)); // (empty line)

    ListNode head3 = fromList(asList(1, 2));
    print(remove(head3, 1)); // 1
  }
}
