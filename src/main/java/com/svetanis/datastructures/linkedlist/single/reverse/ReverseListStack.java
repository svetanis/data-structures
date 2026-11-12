package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import java.util.ArrayDeque;
import java.util.Deque;

// 206. Reverse Linked List
//
// Input: the head of a singly linked list. LeetCode: 0 <= n <= 5000 nodes, values
// -5000..5000. Returns: the head of the reversed list (null for an empty list).
//
// The one idea: one node holds a value and an arrow to the next node; a stack hands things back
// last in, first out. Walk to the last node, pushing every node before it onto the stack. The
// last node is the new head. Popping hands the earlier nodes back last first, and each popped
// node is attached after the one popped before it. The last node popped is the old head, and
// its next is set to null to end the list.
//
// Left as found, on purpose: an empty list (head = null) throws NullPointerException, because
// the first loop reads node.next before anything checks that node exists. LC 206 allows an
// empty list; the three other LC 206 files return it. The pages use this as a trap.
//
// Time: O(n) -- one pass to push, one pass to pop.
// Space: O(n) -- the stack holds every node but the last.
//
// Siblings: ReverseList, ReverseListMoveToFront, ReverseListRecursive and ReverseListTailRecursive
// solve the same problem without the stack; the first two need only O(1) space.

public final class ReverseListStack {

  public static ListNode reverse(ListNode head) {
    Deque<ListNode> stack = new ArrayDeque<>(); // STACK: push and pop at the same end
    ListNode node = head;
    while (node.next != null) { // PUSH every node but the last
      stack.push(node);
      node = node.next;
    }

    head = node; // the last node is the new head
    while (!stack.isEmpty()) { // POP: nodes come back last first
      node.next = stack.pop(); // ATTACH: after the node popped before it
      node = node.next;
    }
    node.next = null; // END: the old head is the last node now
    return head; // RETURN: the old last node
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(asList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    ListNode reversed = reverse(head);
    print(reversed); // 1 2 3 4
  }
}
