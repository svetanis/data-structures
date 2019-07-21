package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseAlternateKNodes {

  public static Node reverse(Node head, int k) {
    
    int count = 0;
    Node prev = null;
    Node curr = head;

    // 1. reverse first k nodes of the linked list
    while (curr != null && count < k) {
      Node next = curr.next;
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
    Node head = null;
    for (int i = 20; i > 0; i--) {
      head = insertAtHead(head, i);
    }
    print(head);
    head = reverse(head, k);
    print(head);

    Node head2 = null;
    for (int i = 20; i > 0; i--) {
      head2 = insertAtHead(head2, i);
    }
    print(head2);
    head2 = reverse(head2, k);
    print(head2);
  }
}
