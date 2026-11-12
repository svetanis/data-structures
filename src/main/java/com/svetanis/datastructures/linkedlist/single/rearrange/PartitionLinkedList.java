package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 86. Partition List
//
// Input: the head of a list and a value x. On LeetCode: 0 <= number of nodes <= 200,
// -100 <= val <= 100, -200 <= x <= 200. Put every node whose value is less than x before every
// node whose value is x or more, keeping the original order inside each group. Returns the new
// head.
//
// The one idea: a group that keeps its order is a list that is only ever added to at its end.
// Walk the list once and build two separate lists from its nodes: one for the values less than
// x, one for the rest. Each starts with a fake node, so the first node added to either needs no
// special case. smaller and greater always sit on the last node of their list. At the end,
// join the end of the first list to the start of the second, and end the second.
//
// Time: O(n) -- one pass.
// Space: O(1) -- two fake nodes; the existing nodes are relinked.

public final class PartitionLinkedList {

  public static ListNode partition(ListNode head, int x) {
    ListNode smaller = new ListNode(0);
    ListNode greater = new ListNode(0);
    ListNode smallerHead = smaller;
    ListNode greaterHead = greater;
    ListNode curr = head;
    while (curr != null) { // WALK every node once
      if (curr.val < x) {
        smaller.next = curr; // attach to the end of the smaller list
        smaller = curr;
      } else {
        greater.next = curr; // attach to the end of the greater list
        greater = curr;
      }
      curr = curr.next;
    }
    smaller.next = greaterHead.next; // JOIN: the last smaller node points at the first greater one
    // the last greater node may still point at a smaller node that came after it: cut, or it loops
    greater.next = null; // CUT
    return smallerHead.next; // RETURN: the first real node of the smaller list
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 3, 7, 5, 2, 9, 4));
    print(partition(head, 5)); // 1 3 2 4 7 5 9

    ListNode head2 = fromList(asList(1, 4, 3, 2, 5, 2));
    print(partition(head2, 3)); // 1 2 2 4 3 5

    ListNode head3 = fromList(asList(2, 1));
    print(partition(head3, 2)); // 1 2
  }
}
