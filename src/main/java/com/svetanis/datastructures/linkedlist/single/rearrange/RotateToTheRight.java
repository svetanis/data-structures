package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 61. Rotate List
//
// given a Singly LinkedList,
// rotate the SLL clockwise
// by k nodes
//
// Move the last k nodes, in their order, to the front and return the new head: 1 2 3 4 5 with
// k = 2 becomes 4 5 1 2 3. k may be larger than the length.
//
// Rotating by the length n gives the same list back, so only k % n matters. Then two pointers
// held k nodes apart: when fast is on the last node, slow is on the node just before the last
// k. Cut after slow, and point the last node at the old head.

public final class RotateToTheRight {
  // Time Complexity: O(n), one pass to count, one to find the cut
  // Space Complexity: O(1)

  public static ListNode rotate(ListNode head, int k) {
    // 1. check for edge cases
    if (head == null || head.next == null) {
      return head;
    }
    // 2. calculate the length
    int n = size(head);

    // 3. adjust k
    k = k % n;

    // 4. early exit for k = 0
    if (k == 0) {
      return head;
    }
    // 5. initialize two pointers
    ListNode fast = head;
    ListNode slow = head;
    // 6. move fast pointer by k
    for (int i = 0; i < k; i++) {
      fast = fast.next;
    }
    // 7. move both pointers
    // until fast reaches the end
    while (fast.next != null) {
      fast = fast.next;
      slow = slow.next;
    }
    // 8. perform rotation
    ListNode newHead = slow.next; // save before the cut: the first of the last k nodes
    slow.next = null; // cut: slow is the new last node
    fast.next = head; // the old last node points at the old head
    return newHead;
  }

  private static int size(ListNode node) {
    int count = 0;
    while (node != null) {
      count++;
      node = node.next;
    }
    return count;
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(10, 20, 30, 40, 50, 60));
    print(rotate(head, 4)); // 30 40 50 60 10 20

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(rotate(head1, 2)); // 4 5 1 2 3

    ListNode head2 = fromList(asList(0, 1, 2));
    print(rotate(head2, 4)); // 2 0 1
  }
}
