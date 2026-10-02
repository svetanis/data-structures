package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.java.base.utils.IntWrapper.newIntWrapper;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.java.base.utils.IntWrapper;

// Kth Node from the End, recursive
//
// Return the node k places from the end of the list (k = 1 is the last node). Returns null
// when k is 0 or less, or larger than the number of nodes. The caller passes a new counter i
// that starts at 0.
//
// The calls first go all the way down to the end of the list, then count nodes on the way
// back. i is one counter shared by every call; a plain int would be copied into each call, so
// it is wrapped in an object. Each call adds 1 to it after the deeper calls have returned, so
// at that moment i holds this node's place from the end. The call where i equals k returns its
// own node; every other call passes on whatever the deeper call returned.

public final class KthToLastRecursive {
  // Time Complexity: O(n), every node is visited once, whatever k is
  // Space Complexity: O(n) recursion stack, one call per node

  // Recursive with wrapper class to pass i by reference
  public static ListNode kthToLast(ListNode node, int k, IntWrapper i) {
    if (node == null || k == 0) { // BASE: past the last node, or k = 0, which has no answer
      return null;
    }

    ListNode current = kthToLast(node.next, k, i); // DOWN first: count only on the way back
    i.value = i.value + 1; // COUNT: this node's place from the end

    if (i.value == k) {
      return node;
    }
    return current; // PASS ON the answer found deeper, or null
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthToLast(head, 3, newIntWrapper())); // 4
  }
}
