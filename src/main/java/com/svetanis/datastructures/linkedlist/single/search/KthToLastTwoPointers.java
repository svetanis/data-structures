package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class KthToLastTwoPointers {

  public static Node kthToLast(Node head, int k) {
    // Time complexity: O(n)
    // Space complexity: O(1)

    if (k <= 0 || head == null) {
      return null;
    }

    Node fast = head;
    Node slow = head;

    // advance fast by K - 1 nodes
    for (int i = 0; i < k - 1; ++i) {
      if (fast == null) {
        return null;
      }
      fast = fast.next;
    }

    // another error check
    if (fast == null) {
      return null;
    }
    // now, move fast and slow at same speed
    // when fast hits the end,
    // slow will be at the right element
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    return slow;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head);
    System.out.println(kthToLast(head, 3));
    System.out.println(kthToLast(head, 5));
  }
}
