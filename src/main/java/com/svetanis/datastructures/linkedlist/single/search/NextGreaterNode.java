package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.linkedlist.single.ListNode;
import com.svetanis.java.base.utils.Print;

// 1019. Next Greater Node In Linked List
//
// For each node, find the value of the first node after it whose value is strictly larger, or
// 0 if there is none. Return the answers as an array, one per node, in list order.
//
// The values are copied into a list so they can be read from right to left. Walking from the
// last value to the first, a stack holds the values to the right that could still be the
// answer for some node, the nearest one on top. Before answering for the current value, every
// stack value that is not larger than it is popped: for every node further left, the current
// value is nearer and at least as large, so a popped value can never be the answer again. The
// value left on top, if any, is the answer.

public final class NextGreaterNode {
  // Time Complexity: O(n), each value is pushed once and popped at most once
  // Space Complexity: O(n), the copied list, the stack and the answer array

  public static int[] nextLargerNodes(ListNode head) {
    List<Integer> list = convertToList(head);
    Deque<Integer> dq = new ArrayDeque<>();
    int n = list.size();
    int[] res = new int[n]; // every slot starts at 0, the answer when nothing larger follows
    for (int i = n - 1; i >= 0; i--) {
      int current = list.get(i);
      while (!dq.isEmpty() && dq.peek() <= current) { // POP: not larger, and hidden behind current
        dq.pop();
      }
      if (!dq.isEmpty()) {
        res[i] = dq.peek(); // ANSWER: the nearest larger value to the right
      }
      dq.push(current); // current may be the answer for a node further left
    }
    return res;
  }

  private static List<Integer> convertToList(ListNode head) {
    List<Integer> list = new ArrayList<>();
    while (head != null) {
      list.add(head.val);
      head = head.next;
    }
    return list;
  }

  public static void main(String[] args) {
    ListNode head = fromList(newArrayList(2, 1, 5));
    Print.print(nextLargerNodes(head)); // [5, 5, 0]
    ListNode head2 = fromList(newArrayList(2, 7, 4, 3, 5));
    Print.print(nextLargerNodes(head2)); // [7, 0, 5, 5, 0]
    ListNode head3 = fromList(newArrayList(1, 7, 5, 1, 9, 2, 5, 1));
    Print.print(nextLargerNodes(head3)); // [7, 9, 9, 9, 0, 5, 0, 0]
  }
}
