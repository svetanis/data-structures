package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Print a Linked List in Reverse
//
// Input: the head of a singly linked list. Not a LeetCode problem (LC 1265 asks for this on the
// judge's own read-only interface, see PrintImmutableInReverse); the lists here are a few nodes.
// Returns: nothing; it prints the values from last to first without changing the list.
//
// The one idea: one call is one node. It first prints everything after its node, and only then
// prints its own value, so the last node is printed first. The calls still waiting to finish
// hold the nodes, in place of a stack.
//
// Time: O(n) -- one call per node.
// Space: O(n) -- recursion stack, one waiting call per node.
//
// Siblings: PrintImmutableInReverse is the same three lines on the judge's read-only node type (LC
// 1265).

public final class PrintInReverse {

  public static void reverse(ListNode head) {

    // base case
    if (head == null) { // BASE: past the end
      return;
    }

    // print the list after head node
    reverse(head.next); // RECURSE: everything after head is printed first

    // after everything else is printed, print head
    System.out.print(head.val + " "); // PRINT: on the way back
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(asList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    reverse(head); // 1 2 3 4
  }
}
