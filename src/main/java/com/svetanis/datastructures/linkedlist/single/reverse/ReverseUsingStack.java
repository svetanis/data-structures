package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Stack;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 206. Reverse Linked List
//
// Given the head of a singly linked list, reverse it and return the new head.
//
// Walk to the last node, pushing every node before it onto a stack. The last node is the new
// head. Popping hands the earlier nodes back last first, and each popped node is attached
// after the one popped before it. The last node popped is the old head, and its next is set to
// null to end the list.
//
// An empty list (head = null) throws NullPointerException: the first loop reads node.next
// before anything checks that node exists.

public final class ReverseUsingStack {
  // Time Complexity: O(n), one pass to push, one pass to pop
  // Space Complexity: O(n), the stack holds every node but the last

  public static ListNode reverse(ListNode head) {
    Stack<ListNode> stack = new Stack<>();
    ListNode node = head;
    while (node.next != null) {
      stack.push(node);
      node = node.next;
    }

    head = node;
    while (!stack.isEmpty()) {
      node.next = stack.pop();
      node = node.next;
    }
    node.next = null;
    return head;
  }

  public static void main(String[] agrs) {
    ListNode head = fromList(newArrayList(4, 3, 2, 1));
    print(head); // 4 3 2 1
    ListNode reversed = reverse(head);
    print(reversed); // 1 2 3 4
  }
}
