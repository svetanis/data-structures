package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Median of a Sorted Linked List
//
// Input: head -- the first node of a singly linked list whose values are in increasing order
// (any int values), or null. Returns the median: the middle value when the count is odd, the
// average of the two middle values when it is even; the empty list returns -1.0. Not a LeetCode
// problem, so there are no constraints.
//
// The one idea: the median sits in the middle, and the middle can be found without counting.
// slow moves one node per turn and fast two, so fast has always gone twice as far as slow; when
// fast stops, slow is on the middle node, the second of the two middles when the count is even.
// prev is the node slow was on one turn earlier, which for an even count is the first middle.
// Where fast stopped tells the two cases apart: on the last node for an odd count, on null for
// an even count. The two middle values are added as longs, because two ints near the int limit
// add up to more than an int can hold.
//
// Time: O(n) -- fast walks the list once and slow follows at half the speed
// Space: O(1) -- three pointers
//
// Siblings: none for a sorted list. MiddleNode finds the middle node with the same two pointers
// but returns the node, not a value.

public final class MedianSortedLL {

  public static double median(ListNode head) {
    if (head == null) { // BASE: no values, no median
      return -1.0;
    }

    ListNode prev = head; // the node slow stood on one turn ago
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) { // STOP when fast cannot take two steps
      prev = slow; // BEFORE: save slow before it moves, so prev stays one node behind
      slow = slow.next; // slow takes one step
      fast = fast.next.next; // fast takes two
    }

    if (fast != null) { // ODD count: fast stopped on the last node
      return slow.val;
    } else { // EVEN count: fast stopped on null
      return ((long) prev.val + slow.val) / 2.0; // long: the sum may pass the int limit
    }
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(1, 2, 3, 4, 5, 6));
    print(head); // 1 2 3 4 5 6
    System.out.println(median(head)); // 3.5
    ListNode odd = fromList(Arrays.asList(1, 2, 9));
    System.out.println(median(odd)); // 2.0
    ListNode big = fromList(Arrays.asList(Integer.MAX_VALUE, Integer.MAX_VALUE));
    System.out.println(median(big)); // 2.147483647E9
  }
}
