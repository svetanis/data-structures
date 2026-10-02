package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Kth Node from the End, two pointers
//
// Return the node k places from the end of the list (k = 1 is the last node). Returns null
// when k is 0 or less, or larger than the number of nodes.
//
// Both pointers start on the head. fast first moves k - 1 nodes ahead, so slow and fast are
// the first and last nodes of a stretch of k nodes. Then both move one node at a time until
// fast is on the last node. The stretch now ends at the last node, so slow, at its start, is
// k places from the end.

public final class KthToLastTwoPointers {
  // Time Complexity: O(n), one pass: fast walks to the end once and slow follows
  // Space Complexity: O(1)

  public static ListNode kthToLast(ListNode head, int k) {
    if (k <= 0 || head == null) {
      return null;
    }

    ListNode fast = head;
    ListNode slow = head;

    // advance fast by K - 1 nodes
    for (int i = 0; i < k - 1; ++i) {
      if (fast == null) {
        return null;
      }
      fast = fast.next;
    }

    // another error check
    if (fast == null) { // k is larger than the list: the last step left the list
      return null;
    }
    // now, move fast and slow at same speed
    // when fast hits the end,
    // slow will be at the right element
    while (fast.next != null) { // STOP with fast on the last node, not past it
      slow = slow.next;
      fast = fast.next;
    }
    return slow;
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthToLast(head, 3)); // 4
    System.out.println(kthToLast(head, 5)); // 20
  }
}
