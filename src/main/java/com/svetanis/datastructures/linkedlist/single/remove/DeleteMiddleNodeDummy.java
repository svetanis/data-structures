package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2095. Delete the Middle Node of a Linked List
//
// Remove the middle node and return the head. Counting from 0, the middle of n nodes is the
// node at index n / 2, so with an even count it is the second of the two central nodes. A
// one-node list becomes the empty list.
//
// A fake node is placed before the head. slow starts on the fake node and fast on the head;
// each turn slow moves one node and fast moves two. Starting slow one node back makes it stop
// one node back: on the node just before the middle, which is the node whose next must change.
// A one-node list needs no special case: slow stays on the fake node and removes the head.

public final class DeleteMiddleNodeDummy {
  // Time Complexity: O(n), one pass
  // Space Complexity: O(1)

  public static ListNode delete(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode dummy = new ListNode(0, head); // FAKE: gives the head a node before it
    ListNode fast = head;
    ListNode slow = dummy; // one node behind the start of fast
    while (fast != null && fast.next != null) { // STOP when fast cannot take two steps
      slow = slow.next;
      fast = fast.next.next;
    }
    // skip middle node
    slow.next = slow.next.next; // SKIP: slow is on the node before the middle
    return dummy.next; // RETURN: dummy.next, since the head itself may have been removed
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10, 60));
    print(delete(head)); // 50 20 15 10 60

    ListNode head1 = fromList(asList(1, 3, 4, 7, 1, 2, 6));
    print(delete(head1)); // 1 3 4 1 2 6

    ListNode head2 = fromList(asList(1, 2, 3, 4));
    print(delete(head2)); // 1 2 4

    ListNode head3 = fromList(asList(2, 1));
    print(delete(head3)); // 2
  }
}
