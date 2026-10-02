package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Reverse Alternate k Nodes
//
// Given the head of a linked list and a number k, reverse the first k nodes, leave the next k
// as they are, reverse the k after that, and so on. A short group at the end is reversed if it
// falls on a reversing turn. Return the new head.
//
// One call handles two groups. It reverses the first k nodes with the usual four lines (save
// the next node, turn the arrow, move prev, move curr). head was the group's first node, so
// after the reversal it is the group's last node, and it is pointed at the first node of the
// group to leave alone. curr then walks to the last node of that group, and that node is
// pointed at whatever the recursive call returns for the rest of the list.

public final class ReverseAlternateKNodes {
  // Time Complexity: O(n), each node is either reversed or walked over, once
  // Space Complexity: O(n / k) recursion stack, one call for every two groups

  public static ListNode reverse(ListNode head, int k) {
    
    int count = 0;
    ListNode prev = null;
    ListNode curr = head;

    // 1. reverse first k nodes of the linked list
    while (curr != null && count < k) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
      count++;
    }

    // 2. now head points to the kth node.
    // so change next of head to (k + 1)-th node
    if (head != null) {
      head.next = curr;
    }
    
    // 3. we don't want to reverse next k nodes.
    // so move the pointer to skip next k nodes
    count = 0;
    while (count < k - 1 && curr != null) {
      curr = curr.next;
      count++;
    }

    // 4. recursively call for the list starting from current.next.
    // and make rest of the list as next of first node
    if (curr != null) {
      curr.next = reverse(curr.next, k);
    }
    // 5. prev is new head of the input list
    return prev;
  }

  public static void main(String[] args) {
    int k = 3;
    ListNode head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head); // 1 2 3 ... 20
    head = reverse(head, k);
    print(head); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19

    ListNode head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2); // 1 2 3 ... 20
    head2 = reverse(head2, k);
    print(head2); // 3 2 1 4 5 6 9 8 7 10 11 12 15 14 13 16 17 18 20 19
  }
}
