package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Kth Node from the Head
//
// Return the node k steps from the head, counting from 0, so k = 0 is the head itself.
// Returns null when k is negative or the list has k nodes or fewer.
//
// curr starts on the head, and count holds how many steps curr has taken. curr steps forward
// until count reaches k or curr runs off the end of the list; curr is then the answer, or null.

public final class KthNode {
  // Time Complexity: O(min(k, n)), one step per count, and never past the end
  // Space Complexity: O(1)

  public static ListNode kthNode(ListNode head, int k) {
    if (head == null || k < 0) {
      return null;
    }

    int count = 0;
    ListNode curr = head;
    while (curr != null && count < k) { // STOP after k steps, or when the list runs out
      curr = curr.next;
      count++;
    }
    return curr; // null when the list ran out first
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthNode(head, 0)); // 50
    System.out.println(kthNode(head, 4)); // 10
    System.out.println(kthNode(head, 5)); // 60
  }
}
