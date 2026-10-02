package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Median of a Sorted Linked List
//
// The list is sorted. Return its median: the middle value when the count is odd, the average
// of the two middle values when it is even. The empty list returns -1.0.
//
// slow moves one node per turn and fast two, so when fast stops, slow is on the middle node,
// the second of the two middles when the count is even. prev is the node slow was on one turn
// earlier, which for an even count is the first middle. Where fast stopped tells the two cases
// apart: on the last node for an odd count, on null for an even count.

public final class MedianSortedLL {
  // Time Complexity: O(n), one pass
  // Space Complexity: O(1)

  public static double median(ListNode head) {
    if (head == null) {
      return -1.0;
    }
    
    ListNode prev = head;
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) { // STOP when fast cannot take two steps
      prev = slow; // BEFORE: save slow before it moves, so prev stays one node behind
      slow = slow.next;
      fast = fast.next.next;
    }

    if (fast != null) { // ODD count: fast stopped on the last node
      return slow.val;
    } else { // EVEN count: fast stopped on null
      return (prev.val + slow.val) / 2.0;
    }
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(1, 2, 3, 4, 5, 6));
    print(head); // 1 2 3 4 5 6
    System.out.println(median(head)); // 3.5
  }
}
