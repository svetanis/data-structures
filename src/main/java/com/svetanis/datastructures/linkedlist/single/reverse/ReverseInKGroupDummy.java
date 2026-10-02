package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 25. Reverse Nodes in k-Group
//
// Given the head of a LinkedList and a number k,
// reverse every k sized sub-list starting from the head.
// if the number of nodes is not a multiple of k then
// left-out nodes, in the end, should remain as is
//
// A fake node in front of the list means every group has a node before it, and prev is that
// node. curr walks k nodes ahead of prev; if it falls off the end, the last group is short and
// the list is returned as it is. Otherwise the group is cut off after curr, reversed on its own
// as a separate list, and attached back: prev is pointed at the reversed group's new first
// node, and start, the group's old first node and now its last, is pointed at the rest of the
// list. start is then the node before the next group.

public final class ReverseInKGroupDummy {
  // Time Complexity: O(n), each node is counted once and reversed once
  // Space Complexity: O(1)

  public static ListNode reverse(ListNode head, int k) {
    if (k <= 1 || head == null) {
      return head;
    }

    ListNode dummy = new ListNode(0, head);
    ListNode prev = dummy;
    ListNode curr = dummy;
    while (curr != null) {
      for (int i = 0; i < k && curr != null; i++) {
        curr = curr.next;
      }
      if (curr == null) {
        return dummy.next;
      }
      // temporarily store the next segment
      ListNode temp = curr.next;
      // detach the k nodes from the rest of the list
      curr.next = null;
      // start will be the new tail after reversal
      ListNode start = prev.next;
      // reverse k nodes
      prev.next = reverse(start);
      // connect the new tail with the temp segment
      start.next = temp;
      // move prev and curr pointers k nodes ahead
      prev = start;
      curr = prev;
    }
    return dummy.next;
  }

  private static ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(1, 2, 3, 4, 5, 6, 7, 8));
    print(reverse(head, 3)); // 3 2 1 6 5 4 7 8

    ListNode head1 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head1, 2)); // 2 1 4 3 5

    ListNode head2 = fromList(asList(1, 2, 3, 4, 5));
    print(reverse(head2, 3)); // 3 2 1 4 5
  }
}
