package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.svetanis.datastructures.linkedlist.single.Nodes.insertAtHead;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.reverse.ReverseIterative.reverse;

import com.svetanis.datastructures.linkedlist.single.Node;

// Given a singly linked list L0 -> L1 -> … -> Ln-1 -> Ln. 
// Rearrange the nodes in the list so that the new formed list is : 
// L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 …

public final class Zipping {

  public static Node zip(Node head) {
    
    if(head == null) {
      return null;
    }

    // find the middle point of LL
    Node slow = head;
    Node fast = head.next;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    Node list1 = head;
    Node list2 = slow.next;
    slow.next = null;// split the list into two lists
    Node reversed = reverse(list2);
    
    while (list1 != null && reversed != null) {
      // connect current.next to reversed,
      // and advance current
      Node temp = list1.next;
      list1.next = reversed;
      list1 = temp;

      // connect reversed.next to current,
      // and advance reversed
      if (list1 != null) {
        Node tmp = reversed.next;
        reversed.next = list1;
        reversed = tmp;
      }
    }
    return head;
  }

  public static void main(String[] args) {
    // 1->2->3->4->5->6->7->8

    Node head = null;
    for (int i = 8; i >= 1; --i) {
      head = insertAtHead(head, i);
    }
    print(head);
    Node zipped = zip(head);
    print(zipped);
  }
}
