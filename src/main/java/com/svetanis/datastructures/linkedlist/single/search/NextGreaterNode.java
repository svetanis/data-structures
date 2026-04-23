package com.svetanis.datastructures.linkedlist.single.search;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.java.base.utils.Print;

// 1019. Next Greater Node In Linked List

public final class NextGreaterNode {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static int[] nextLargerNodes(Node head) {
    List<Integer> list = convertToList(head);
    Deque<Integer> dq = new ArrayDeque<>();
    int n = list.size();
    int[] res = new int[n];
    for (int i = n - 1; i >= 0; i--) {
      int current = list.get(i);
      while (!dq.isEmpty() && dq.peek() <= current) {
        dq.pop();
      }
      if (!dq.isEmpty()) {
        res[i] = dq.peek();
      }
      dq.push(current);
    }
    return res;
  }

  private static List<Integer> convertToList(Node head) {
    List<Integer> list = new ArrayList<>();
    while (head != null) {
      list.add(head.data);
      head = head.next;
    }
    return list;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(2, 1, 5));
    Print.print(nextLargerNodes(head)); // [5, 5, 0]
    Node head2 = fromList(newArrayList(2, 7, 4, 3, 5));
    Print.print(nextLargerNodes(head2)); // [7, 0, 5, 5, 0]
    Node head3 = fromList(newArrayList(1, 7, 5, 1, 9, 2, 5, 1));
    Print.print(nextLargerNodes(head3)); // [7, 9, 9, 9, 0, 5, 0, 0]
  }
}
