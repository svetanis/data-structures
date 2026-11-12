package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// 1019. Next Greater Node In Linked List
//
// Input: head -- the first node of a singly linked list. On LeetCode: 1 to 10^4 nodes, each value
// 1 to 10^9. Returns an array with one entry per node, in list order: the value of the first node
// after it whose value is strictly larger, or 0 if there is none.
//
// The one idea: a singly linked list can only be read left to right, but the answer for a node
// depends on the nodes to its right, so the values are first copied into a list that can be read
// from right to left. Walking from the last value to the first, a stack holds the values to the
// right that could still be the answer for some node, the nearest one on top. Before answering
// for the current value, every stack value that is not larger than it is popped: for every node
// further left, the current value is nearer and at least as large, so a popped value can never
// be the answer again. The value left on top, if any, is the answer. The current value is then
// pushed, because it may be the answer for a node further left.
//
// Time: O(n) -- each value is pushed once and popped at most once
// Space: O(n) -- the copied list, the stack and the answer array
//
// Siblings: none in this package.

public final class NextGreaterNode {

  public static int[] nextLargerNodes(ListNode head) {
    List<Integer> list = convertToList(head);
    Deque<Integer> dq = new ArrayDeque<>(); // STACK: candidates to the right, nearest on top
    int n = list.size();
    int[] res = new int[n]; // every slot starts at 0, the answer when nothing larger follows
    for (int i = n - 1; i >= 0; i--) { // WALK right to left
      int current = list.get(i);
      while (!dq.isEmpty() && dq.peek() <= current) { // POP: not larger, and hidden behind current
        dq.pop();
      }
      if (!dq.isEmpty()) {
        res[i] = dq.peek(); // ANSWER: the nearest larger value to the right
      }
      dq.push(current); // PUSH: current may be the answer for a node further left
    }
    return res;
  }

  private static List<Integer> convertToList(ListNode head) {
    List<Integer> list = new ArrayList<>();
    while (head != null) { // COPY every value, in list order
      list.add(head.val);
      head = head.next;
    }
    return list;
  }

  private static void print(int[] a) {
    for (int x : a) {
      System.out.print(x);
      System.out.print(' ');
    }
    System.out.println();
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(2, 1, 5));
    print(nextLargerNodes(head)); // 5 5 0
    ListNode head2 = fromList(Arrays.asList(2, 7, 4, 3, 5));
    print(nextLargerNodes(head2)); // 7 0 5 5 0
    ListNode head3 = fromList(Arrays.asList(1, 7, 5, 1, 9, 2, 5, 1));
    print(nextLargerNodes(head3)); // 7 9 9 9 0 5 0 0
  }
}
