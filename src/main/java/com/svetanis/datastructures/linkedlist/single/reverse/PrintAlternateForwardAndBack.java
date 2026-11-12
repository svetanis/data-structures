package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Print Alternate Nodes, Forward and Back
//
// Input: the head of a singly linked list. Not a LeetCode problem, so there are no official
// limits; the lists here are a few nodes. Returns: nothing; it prints the values of every
// second node (the 1st, 3rd, 5th, ...) from head to end, and then the same values from end to
// head. If the list has an even number of nodes, the last node is skipped.
// For Linked List 1->2->3->4->5, print 1 3 5 5 3 1.
// For Linked List 1->2->3->4->5->6, print 1 3 5 5 3 1.
//
// The one idea: one call is one node. It prints its node's value, then calls itself on the
// node two places ahead, skipping one, and after that call returns prints its own value a
// second time. The values printed on the way in come out first to last; the ones printed on
// the way back come out last to first, because the call that started last finishes first.
//
// Time: O(n) -- one call for every second node.
// Space: O(n) -- recursion stack, one waiting call for every second node.

public final class PrintAlternateForwardAndBack {

  public static void alternate(ListNode head) {

    // base case
    if (head == null) { // BASE: past the end
      return;
    }

    System.out.print(head.val + " "); // PRINT on the way in

    if (head.next != null) { // head.next.next can be read only when head.next exists
      alternate(head.next.next); // SKIP one node, RECURSE on the node two places ahead
    }

    System.out.print(head.val + " "); // PRINT on the way back
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(asList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    alternate(head); // 4 2 2 4
  }
}
