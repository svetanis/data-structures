package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Kth Node from the End, recursive
//
// Input: node -- the first node of a singly linked list, or null; k -- the place from the end,
// where k = 1 is the last node; i -- a counter the caller creates with new IntWrapper(), starting
// at 0. Returns the node k places from the end of the list, or null when k is 0 or less or larger
// than the number of nodes. Not a LeetCode problem, so there are no constraints.
//
// The one idea: a node knows only the node after it, so a node cannot tell how far it is from the
// end. A recursive call can: the calls first go all the way down to the end of the list, and
// each call returns only after the calls below it have. So on the way back, the last node is the
// first to finish, then the one before it, and so on. i is one counter shared by every call;
// a plain int would be copied into each call, so it is wrapped in an object. Each call adds 1
// to it after the deeper calls have returned, so at that moment i holds this node's place from
// the end. The call where i equals k returns its own node; every other call passes on whatever
// the deeper call returned.
//
// Time: O(n) -- every node is visited once, whatever k is
// Space: O(n) -- one call waits on the stack per node
//
// Siblings: KthToLastTwoPointers finds the same node in one forward pass with O(1) space, by
// keeping two pointers k - 1 nodes apart; KthNode counts from the head instead.

public final class KthToLastRecursive {

  // a counter many calls can share; a plain int would be copied into each call
  public static final class IntWrapper {
    public int value;
  }

  // Recursive with wrapper class to pass i by reference
  public static ListNode kthToLast(ListNode node, int k, IntWrapper i) {
    if (node == null || k == 0) { // BASE: past the last node, or k = 0, which has no answer
      return null;
    }

    ListNode current = kthToLast(node.next, k, i); // DOWN first: count only on the way back
    i.value = i.value + 1; // COUNT: this node's place from the end

    if (i.value == k) { // TAKE: this node is k places from the end
      return node;
    }
    return current; // PASS ON the answer found deeper, or null
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthToLast(head, 3, new IntWrapper())); // 4
    System.out.println(kthToLast(head, 6, new IntWrapper())); // 50
    System.out.println(kthToLast(head, 7, new IntWrapper())); // null
  }
}
