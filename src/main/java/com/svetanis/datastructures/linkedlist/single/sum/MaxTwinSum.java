package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2130. Maximum Twin Sum of a Linked List
//
// Input: head -- the first node of a singly linked list with an even number of nodes n. On
// LeetCode: n from 2 to 10^5, each value 1 to 10^5. Node i and node n - 1 - i are twins (first
// and last, second and second to last, ...). Returns the largest sum of a pair of twins.
//
// The one idea: a twin pair is one node from the front half and its mirror from the back half,
// but the back half can only be walked forward, so the mirror order is unreachable as it
// stands. Reverse the back half, and walking both halves together from their first nodes lines
// up every pair of twins. To split the list, find the last node of the front half with two
// pointers (fast moves two nodes for each one that slow moves) and cut the list there. The
// list is left cut in two, so the caller's head then reads as the front half only.
//
// Time: O(n) -- finding the middle, reversing the back half and walking the pairs are each at
// most one pass over half the list or the list
// Space: O(1) -- the nodes are rewired, nothing is copied
//
// Siblings: none in this package.

public final class MaxTwinSum {

  public static int maxTwinSum(ListNode head) {
    // 1. initialize two pointers
    ListNode slow = head;
    ListNode fast = head.next; // one ahead, so slow stops on the last node of the front half
    // 2. find the middle of the list
    while (fast != null && fast.next != null) { // WALK: fast takes two steps for each of slow's one
      slow = slow.next;
      fast = fast.next.next;
    }
    // 3. split the list into two halves
    ListNode first = head;
    ListNode second = slow.next;
    slow.next = null; // CUT: the list is now two lists
    // 4. reverse the second half of the list
    ListNode reversed = reverse(second);
    // 5. traverse the two halves together
    // and update the max twin sum
    int max = 0; // safe start: every value is at least 1, so every twin sum is larger
    while (first != null && reversed != null) { // PAIR the twins
      int sum = first.val + reversed.val;
      max = Math.max(max, sum); // RECORD the largest sum so far
      first = first.next;
      reversed = reversed.next;
    }
    return max;
  }

  private static ListNode reverse(ListNode head) {
    ListNode dummy = new ListNode(); // FAKE: the reversed list grows after it
    ListNode curr = head;
    while (curr != null) { // MOVE each node to the front of the reversed list
      ListNode next = curr.next; // SAVE the rest before the arrow changes
      curr.next = dummy.next;
      dummy.next = curr;
      curr = next;
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    ListNode head1 = fromList(asList(5, 4, 2, 1));
    System.out.println(maxTwinSum(head1)); // 6

    ListNode head2 = fromList(asList(4, 2, 2, 3));
    System.out.println(maxTwinSum(head2)); // 7

    ListNode head3 = fromList(asList(1, 100000));
    System.out.println(maxTwinSum(head3)); // 100001
  }
}
