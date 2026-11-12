package com.svetanis.datastructures.linkedlist.single.remove;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 2095. Delete the Middle Node of a Linked List
//
// Input: the head of a singly linked list. LeetCode: 1 <= n <= 10^5 nodes, node values
// 1..10^5. Returns: the head after the middle node is removed. Counting from 0, the middle of
// n nodes is the node at index n / 2, so with an even count it is the second of the two central
// nodes. A one-node list becomes the empty list (null).
//
// The one idea: a fake node is placed before the head. A marker is one node of the list. slow
// starts on the fake node and fast on the head; each turn slow moves one node and fast moves
// two. Starting slow one node back makes it stop one node back: on the node just before the
// middle, which is the node whose next must change. A one-node list needs no special case: slow
// stays on the fake node and removes the head.
//
// Time: O(n) -- fast walks the list once, in steps of two; slow walks half as far.
// Space: O(1) -- the fake node and two markers.
//
// Siblings: DeleteMiddleNode has no fake node, so it keeps a third marker prev one node behind
// midd and needs a separate branch for the one-node list.

public final class DeleteMiddleNodeDummy {

  public static ListNode delete(ListNode head) {
    if (head == null) { // BASE: empty list
      return null;
    }
    ListNode dummy = new ListNode(0, head); // FAKE: gives the head a node before it
    ListNode fast = head; // FAST marker: two nodes per turn
    ListNode slow = dummy; // SLOW marker: one node behind the start of fast
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

    ListNode head4 = fromList(asList(7));
    print(delete(head4)); // (empty line)
  }
}
