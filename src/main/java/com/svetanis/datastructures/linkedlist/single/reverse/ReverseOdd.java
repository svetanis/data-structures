package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Print Alternate Nodes, Forward and Back
//
// print alternate nodes of the given Linked List,
// first from head to end, and then from end to head.
// If LL has even number of nodes, then skips last node.
// For Linked List 1->2->3->4->5, print 1 3 5 5 3 1.
// For Linked List 1->2->3->4->5->6, print 1 3 5 5 3 1.
//
// One call prints its node's value, then calls itself on the node two places ahead, skipping
// one, and after that call returns prints its own value a second time. The values printed on
// the way in come out first to last; the ones printed on the way back come out last to first.

public final class ReverseOdd {
  // Time Complexity: O(n), one call for every second node
  // Space Complexity: O(n) recursion stack, one waiting call for every second node

  public static void alternate(ListNode head) {

    // base case
    if (head == null) {
      return;
    }

    System.out.print(head.val + " ");

    if (head.next != null) { // head.next.next can be read only when head.next exists
      alternate(head.next.next);
    }

    System.out.print(head.val + " ");
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(newArrayList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    alternate(head); // 4 2 2 4
  }
}
