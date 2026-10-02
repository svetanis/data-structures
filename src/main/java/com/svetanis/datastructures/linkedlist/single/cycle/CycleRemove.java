package com.svetanis.datastructures.linkedlist.single.cycle;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Remove the Cycle from a Linked List
//
// given a Singly LinkedList
// find whether the SLL contains a loop
// and if loop is present, remove it
// The list is changed in place and nothing is returned: afterwards it holds the same nodes
// in the same order, and the last one points to null instead of back into the list.
//
// Find where the cycle starts, as in Linked List Cycle II (LC 142): slow and fast meet on a
// node inside the cycle; then one pointer from the head and one from the meeting point,
// one node per turn each, meet on the cycle's first node. The node whose next is that first
// node is the last node of the list, and its next is the one link to set to null.

public final class CycleRemove {
  // Time Complexity: O(n), find the meeting point, the start, then one walk round the cycle
  // Space Complexity: O(1), a few pointers

  public static void cycleRemove(ListNode head) {
	  
    if (head == null || head.next == null) {
      return;
    }

    ListNode slow = head;
    ListNode fast = head;

    // find the meeting point
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        cut(head, slow);
        return;
      }
    }
    // the loop ran out: no meeting point, so no cycle
  }

  // the pointers met, so a cycle exists. move one back to the head and
  // walk both one step at a time: the distance from the head to the
  // cycle's start equals the distance from the meeting point to it, so
  // they arrive there together. then walk the cycle once more to reach
  // the node pointing AT the start, which is the link to break.
  private static void cut(ListNode head, ListNode meeting) {
    ListNode start = head;
    while (start != meeting) {
      start = start.next;
      meeting = meeting.next;
    }
    ListNode tail = start;
    while (tail.next != start) { // stop on the node that points back AT the start
      tail = tail.next;
    }
    tail.next = null; // the only link that closed the cycle
  }

  public static void main(String[] args) {
    ListNode head = fromList(asList(50, 20, 15, 4, 10));
    print(head);
    // create a loop for testing
    head.next.next.next.next.next = head.next.next;
    cycleRemove(head);
    print(head); // 50 20 15 4 10

    // an acyclic list of EVEN length. the fast pointer steps past the
    // end to null rather than landing on the last node, so any test of
    // fast.next after the loop throws here and nowhere else
    ListNode head1 = fromList(asList(1, 2, 3, 4));
    cycleRemove(head1);
    print(head1); // 1 2 3 4

    // the cycle starts AT the head, so the head is the node to keep and
    // the tail is the link to break. comparing slow.next against
    // fast.next stops one node early and cuts after the head instead
    ListNode head2 = fromList(asList(1, 2, 3, 4));
    head2.next.next.next.next = head2;
    cycleRemove(head2);
    print(head2); // 1 2 3 4
  }
}
