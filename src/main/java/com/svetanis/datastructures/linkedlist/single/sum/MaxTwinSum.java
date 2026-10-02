package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2130. Maximum Twin Sum of a Linked List
//
// The list has an even number of nodes n, each value at least 1. Node i and node n - 1 - i
// are twins (first and last, second and second to last, ...). Return the largest sum of a
// pair of twins.
//
// Twins are one node from the front half and its mirror from the back half, but the back
// half can only be walked forward. So find the last node of the front half with two
// pointers (fast moves two nodes for each one that slow moves), cut the list there, and
// reverse the back half: now walking both halves together lines up every pair of twins.
// The list is left cut in two, so the caller's head then reads as the front half only.

public final class MaxTwinSum {
  // Time Complexity: O(n), three passes over half the list or less each
  // Space Complexity: O(1), the nodes are rewired, nothing is copied

  public static int maxTwinSum(ListNode head) {
    // 1. initialize two pointers
    ListNode slow = head;
    ListNode fast = head.next; // one ahead, so slow stops on the last node of the front half
    // 2. find the middle of the list
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    // 3. split the list into two halves
    ListNode first = head;
    ListNode second = slow.next;
    slow.next = null;
    // 4. reverse the second half of the list
    ListNode reversed = reverse(second);
    // 5. traverse the two halves together
    // and update the max twin sum
    int max = 0; // safe start: every value is at least 1, so every twin sum is larger
    while (first != null && reversed != null) {
      int sum = first.val + reversed.val;
      max = Math.max(max, sum);
      first = first.next;
      reversed = reversed.next;
    }
    return max;
  }

  private static ListNode reverse(ListNode head) {
    ListNode dummy = new ListNode();
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
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
