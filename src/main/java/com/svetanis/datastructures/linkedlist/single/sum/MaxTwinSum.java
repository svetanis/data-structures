package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static java.util.Arrays.asList;

import com.svetanis.datastructures.linkedlist.single.Node;

// 2130. Maximum Twin Sum of a Linked List

public final class MaxTwinSum {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static int maxTwinSum(Node head) {
    // 1. initialize two pointers
    Node slow = head;
    Node fast = head.next;
    // 2. find the middle of the list
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    // 3. split the list into two halves
    Node first = head;
    Node second = slow.next;
    slow.next = null;
    // 4. reverse the second half of the list
    Node reversed = reverse(second);
    // 5. traverse the two halves together
    // and update the max twin sum
    int max = 0;
    while (first != null && reversed != null) {
      int sum = first.data + reversed.data;
      max = Math.max(max, sum);
      first = first.next;
      reversed = reversed.next;
    }
    return max;
  }

  private static Node reverse(Node head) {
    Node dummy = new Node();
    Node curr = head;
    while (curr != null) {
      Node next = curr.next;
      curr.next = dummy.next;
      dummy.next = curr;
      curr = next;
    }
    return dummy.next;
  }

  public static void main(String[] args) {
    Node head1 = fromList(asList(5, 4, 2, 1));
    System.out.println(maxTwinSum(head1)); // 6

    Node head2 = fromList(asList(4, 2, 2, 3));
    System.out.println(maxTwinSum(head2)); // 7

    Node head3 = fromList(asList(1, 100000));
    System.out.println(maxTwinSum(head3)); // 100001
  }
}
