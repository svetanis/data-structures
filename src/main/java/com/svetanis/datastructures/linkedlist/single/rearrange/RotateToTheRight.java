package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 61. Rotate List
// given a Singly LinkedList,
// rotate the SLL clockwise 
// by k nodes

public final class RotateToTheRight {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static Node rotate(Node head, int k) {
    // 1. check for edge cases
    if (head == null || head.next == null) {
      return head;
    }
    // 2. calculate the length
    int n = size(head);

    // 3. adjust k
    k = k % n;

    // 4. early exit for k = 0
    if (k == 0) {
      return head;
    }
    // 5. initialize two pointers
    Node fast = head;
    Node slow = head;
    // 6. move fast pointer by k
    for (int i = 0; i < k; i++) {
      fast = fast.next;
    }
    // 7. move both pointers
    // until fast reaches the end
    while (fast.next != null) {
      fast = fast.next;
      slow = slow.next;
    }
    // 8. perform rotation
    Node newHead = slow.next;
    slow.next = null;
    fast.next = head;
    return newHead;
  }

  private static int size(Node node) {
    int count = 0;
    while (node != null) {
      count++;
      node = node.next;
    }
    return count;
  }

  public static void main(String[] args) {
    Node head = fromList(asList(10, 20, 30, 40, 50, 60));
    print(rotate(head, 4)); // 30 40 50 60 10 20

    Node head1 = fromList(asList(1, 2, 3, 4, 5));
    print(rotate(head1, 2)); // 4 5 1 2 3

    Node head2 = fromList(asList(0, 1, 2));
    print(rotate(head2, 4)); // 2 0 1
  }
}
