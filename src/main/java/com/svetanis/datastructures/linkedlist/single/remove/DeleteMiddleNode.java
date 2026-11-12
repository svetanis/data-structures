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
// The one idea: two markers walk at different speeds. A marker is one node of the list. curr
// moves two nodes per turn and midd moves one, so when curr cannot take two more steps, midd
// has gone half as far: it is on the middle node. prev is the node midd was on one turn
// earlier, the node just before the middle, and pointing prev.next past the middle removes it.
//
// Time: O(n) -- curr walks the list once, in steps of two; midd walks half as far.
// Space: O(1) -- three markers.
//
// Siblings: DeleteMiddleNodeDummy does the same with a fake node in front of the head, so it
// needs neither the one-node branch nor the third marker prev.

public final class DeleteMiddleNode {

  public static ListNode delete(ListNode head) {
    if (head == null) { // BASE: empty list
      return null;
    }
    if (head.next == null) { // BASE: one node
      // one node: it IS the middle, and nothing in a singly linked list
      // points at the head, so there is no predecessor to unlink it
      // from. the dummy-headed sibling needs no such branch, because
      // the dummy is that predecessor
      return null; // RETURN: the empty list
    }

    ListNode curr = head; // FAST marker: two nodes per turn
    ListNode midd = head; // SLOW marker: one node per turn
    ListNode prev = midd;
    while (curr != null && curr.next != null) { // STOP when curr cannot take two steps
      curr = curr.next.next;
      prev = midd; // BEFORE: save midd before it moves, so prev stays one node behind
      midd = midd.next;
    }
    // delete middle node
    prev.next = midd.next; // SKIP: prev now points past the middle
    midd = null; // DROP: the removed node's name, no effect on the list
    return head; // RETURN: head is unchanged, the list has at least two nodes
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
    print(delete(head4)); // (empty line)
  }
}
