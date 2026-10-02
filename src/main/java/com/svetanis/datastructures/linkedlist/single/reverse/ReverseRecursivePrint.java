package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Print a Linked List in Reverse
//
// Given the head of a linked list, print its values from last to first without changing the
// list.
//
// The call for one node first prints everything after it, and only then prints its own value,
// so the last node is printed first. The calls still waiting to finish hold the nodes, in place
// of a stack.

public final class ReverseRecursivePrint {
  // Time Complexity: O(n), one call per node
  // Space Complexity: O(n) recursion stack, one waiting call per node

  public static void reverse(ListNode head) {

    // base case
    if (head == null) {
      return;
    }
    
    // print the list after head node
    reverse(head.next);

    // after everything else is printed, print head
    System.out.print(head.val + " ");
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(newArrayList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    reverse(head); // 1 2 3 4
  }
}
