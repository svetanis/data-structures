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
// Two pointers start on the head: midd moves one node per turn, curr moves two. When curr
// cannot take two more steps, midd has gone half as far, so it is on the middle node. prev is
// the node midd was on one turn earlier, the node just before the middle, and pointing
// prev.next past the middle removes it.

public final class DeleteMiddleNode {
  // Time Complexity: O(n), one pass
  // Space Complexity: O(1)

  public static ListNode delete(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      // one node: it IS the middle, and nothing in a singly linked list
      // points at the head, so there is no predecessor to unlink it
      // from. the dummy-headed sibling needs no such branch, because
      // the dummy is that predecessor
      return null;
    }

    ListNode curr = head;
    ListNode midd = head;
    ListNode prev = midd;
    while (curr != null && curr.next != null) { // STOP when curr cannot take two steps
      curr = curr.next.next;
      prev = midd; // BEFORE: save midd before it moves, so prev stays one node behind
      midd = midd.next;
    }
    // delete middle node
    prev.next = midd.next; // SKIP: prev now points past the middle
    midd = null;
    return head;
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

    // one node: the answer is the empty list. print() on null prints a
    // blank line, which is what an empty list looks like here
    ListNode head4 = fromList(asList(7));
    print(delete(head4)); // []
  }
}
