package com.svetanis.datastructures.linkedlist.single.sum;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class AddTwoNums {

  public static Node addTwoNumbers(Node first, Node second) {
    
    int carry = 0;
    Node dummyHead = new Node(0);
    Node curr = dummyHead;
    while (first != null || second != null) {
      int x = first != null ? first.data : 0;
      int y = second != null ? second.data : 0;
      int sum = x + y + carry;
      carry = sum / 10;

      curr.next = new Node(sum % 10);
      curr = curr.next;

      if (first != null) {
        first = first.next;
      }
      if (second != null) {
        second = second.next;
      }
    }

    if (carry > 0) {
      curr.next = new Node(carry);
    }
    return dummyHead.next;
  }

  public static void main(String[] args) {
    Node head1 = null;
    head1 = insertAtHead(head1, 6);
    head1 = insertAtHead(head1, 4);
    head1 = insertAtHead(head1, 9);
    head1 = insertAtHead(head1, 5);
    head1 = insertAtHead(head1, 7);

    Node head2 = null;
    head2 = insertAtHead(head2, 4);
    head2 = insertAtHead(head2, 8);

    System.out.println("Original lists:");
    print(head1);
    print(head2);
    System.out.println("sum:");
    Node sum = addTwoNumbers(head1, head2);
    print(sum);
  }
}
