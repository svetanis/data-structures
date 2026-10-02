package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternating k Nodes
//
// Given the head of a LinkedList and a number k,
// reverse every alternating k sized sub-list
// starting from the head. if in the end,
// left with a sub-list with less than k nodes,
// reverse it too
//
// Iterative, in place. Each turn of the outer loop handles two groups. firstTail is the last
// node before the group (null for the first group), and subListTail is the group's first node,
// which becomes its last once reversed. Reverse up to k nodes, attach the reversed group after
// firstTail (or make it the head), point subListTail at the node after the group, then walk
// curr over the next k nodes, so that prev ends on the last node of the group left alone.

public final class ReverseAlternatingKNodes {
  // Time Complexity: O(n), each node is either reversed or walked over, once
  // Space Complexity: O(1)

  public static ListNode reverse(ListNode head, int k) {
    if (k <= 1 || head == null) { // k = 1: every group is one node, so nothing moves
      return head;
    }

    ListNode curr = head;
    ListNode prev = null;
    while(curr != null) {
      ListNode firstTail = prev; 
      ListNode subListTail = curr;
      ListNode next = null;
      // reverse k nodes
      for (int i = 0; curr != null && i < k; i++) {
        next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
      }
      
      // connect with previous part
      if (firstTail != null) {
        firstTail.next = prev;
      } else {
        head = prev;
      }
      // connect with next part
      subListTail.next = curr;
      // skip k nodes
      for(int i = 0; curr != null && i < k; i++) {
    	prev = curr;
    	curr = curr.next;
      }
    }
    return head;
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(1, 2, 3, 4, 5, 6, 7, 8));
    print(head); // 1 2 3 4 5 6 7 8
    print(reverse(head, 2)); // 2 1 3 4 6 5 7 8
  }
}
